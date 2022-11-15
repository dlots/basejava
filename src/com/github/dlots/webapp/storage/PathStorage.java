package com.github.dlots.webapp.storage;

import com.github.dlots.webapp.exception.StorageException;
import com.github.dlots.webapp.model.Resume;
import com.github.dlots.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamSerializer streamSerializer;

    protected PathStorage(String directoryPath, StreamSerializer streamSerializer) {
        directory = Paths.get(directoryPath);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directoryPath + " is not a directory or is not readable/writable");
        }
        this.streamSerializer = streamSerializer;
    }

    @Override
    public void clear() {
        try (Stream<Path> files = Files.list(directory)) {
            files.forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try (Stream<Path> files = Files.list(directory)) {
            return Math.toIntExact(files.count());
        } catch (IOException e) {
            throw new StorageException("Get files count error", null, e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid + ".resume");
    }

    @Override
    protected void doUpdate(Path file, Resume r) {
        try {
            streamSerializer.write(r, new BufferedOutputStream(Files.newOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExists(Path file) {
        return Files.exists(file);
    }

    @Override
    protected void doSave(Path file, Resume r) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file, getFileName(file), e);
        }
        doUpdate(file, r);
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return streamSerializer.read(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", getFileName(file), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("File delete error", getFileName(file), e);
        }
    }

    @Override
    protected List<Resume> getAllCopy() {
        try (Stream<Path> files = Files.list(directory)) {
            return files.map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Get resume stream for sorting error", null, e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
