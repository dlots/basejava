package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.exception.NotExistsStorageException;
import com.github.dlots.webapp.exception.StorageException;
import com.github.dlots.webapp.model.ContactType;
import com.github.dlots.webapp.model.Resume;
import com.github.dlots.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @SuppressWarnings("SqlWithoutWhere")
    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume r) {
        sqlHelper.<Void>transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.setString(2, r.getFullName());
                preparedStatement.execute();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (type, value, resume_uuid) VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> contact : r.getContacts().entrySet()) {
                    preparedStatement.setString(1, contact.getKey().name());
                    preparedStatement.setString(2, contact.getValue());
                    preparedStatement.setString(3, r.getUuid());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.<Void>transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name =? WHERE uuid =?")) {
                preparedStatement.setString(1, r.getFullName());
                preparedStatement.setString(2, r.getUuid());
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistsStorageException(r.getUuid());
                }
            }


            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r " +
                        "   LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "WHERE uuid =?",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistsStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, resultSet.getString("full_name"));
                    do {
                        String value = resultSet.getString("value");
                        if (value == null) {
                            continue;
                        }
                        ContactType type = ContactType.valueOf(resultSet.getString("type"));
                        r.addContact(type, value);
                    } while (resultSet.next());
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("DELETE FROM resume WHERE uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistsStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r " +
                        "   LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid ",
                preparedStatement -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    ArrayList<Resume> resumes = new ArrayList<>();
                    String currentResumeUuid = null;
                    while (resultSet.next()) {
                        String currentTupleUuid = resultSet.getString("uuid").trim();
                        if (!currentTupleUuid.equals(currentResumeUuid)) {
                            currentResumeUuid = currentTupleUuid;
                            resumes.add(new Resume(currentTupleUuid, resultSet.getString("full_name")));
                        }
                        String value = resultSet.getString("value");
                        if (value == null) {
                            continue;
                        }
                        ContactType type = ContactType.valueOf(resultSet.getString("type"));
                        resumes.get(resumes.size() - 1).addContact(type, value);
                    }
                    Collections.sort(resumes);
                    return resumes;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new StorageException("Could not get resumes count from database.");
            }
            return resultSet.getInt("count");
        });
    }
}
