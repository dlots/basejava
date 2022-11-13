package com.github.dlots.webapp.model.section;

public enum SectionType {
    OBJECTIVE("Desired position"),
    PERSONAL("Personal qualities"),
    ACHIEVEMENTS("Achievements"),
    QUALIFICATIONS("Qualifications"),
    EXPERIENCE("Experience"),
    EDUCATION("Education");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
