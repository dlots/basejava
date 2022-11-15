package com.github.dlots.webapp.storage.serializer;

import com.github.dlots.webapp.model.Link;
import com.github.dlots.webapp.model.Resume;
import com.github.dlots.webapp.model.section.ListSection;
import com.github.dlots.webapp.model.section.Organization;
import com.github.dlots.webapp.model.section.OrganizationsSection;
import com.github.dlots.webapp.model.section.TextSection;
import com.github.dlots.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
    private final XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(
                Resume.class, Link.class, Organization.class, Organization.Position.class, OrganizationsSection.class,
                TextSection.class, ListSection.class
        );

    }

    @Override
    public void write(Resume r, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, writer);
        }
    }

    @Override
    public Resume read(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
