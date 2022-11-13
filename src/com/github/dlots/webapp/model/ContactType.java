package com.github.dlots.webapp.model;

public enum ContactType {
    PHONE("Phone number"),
    CELLPHONE("Cell phone number"),
    HOME_PHONE("Home phone number"),
    SKYPE("Skype"),
    EMAIL("E-mail"),
    LINKEDIN("LinkedIn profile"),
    GITHUB("GitHub profile"),
    STACKOVERFLOW("Stack Overflow profile"),
    PERSONAL_PAGE("Personal page");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
