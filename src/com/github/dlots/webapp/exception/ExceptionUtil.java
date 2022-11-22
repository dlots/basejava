package com.github.dlots.webapp.exception;

import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionUtil {
    private static final String ERRCODE_UNIQUE_VIOLATION = "23505";

    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            if (e.getSQLState().equals(ERRCODE_UNIQUE_VIOLATION)) {
                return new ExistsStorageException(null);
            }
        }
        return new StorageException(e);
    }
}
