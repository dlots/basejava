package com.github.dlots.webapp.model;

import com.github.dlots.webapp.model.section.Section;
import com.github.dlots.webapp.model.section.SectionType;

import java.util.EnumMap;
import java.util.Objects;
import java.util.UUID;

/**
 * com.github.dlots.webapp.model.Resume class
 */
public class Resume {
    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final EnumMap<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final EnumMap<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void addContact(ContactType type, String content) {
        contacts.put(type, content);
    }

    public Section getSection(SectionType type) {
        return sections.get(type);
    }

    public void addSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName) && contacts.equals(resume.contacts) && sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        return uuid;
    }
}
