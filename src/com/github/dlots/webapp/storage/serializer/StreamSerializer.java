package com.github.dlots.webapp.storage.serializer;

import com.github.dlots.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {
    void write(Resume r, OutputStream outputStream) throws IOException;

    Resume read(InputStream inputStream) throws IOException;
}
