package com.github.dlots.webapp.exception;

public class NotExistsStorageException extends StorageException {

    public NotExistsStorageException(String uuid) {
        super ("Resume " + uuid + " does not exist", uuid);
    }
}
