package com.github.dlots.webapp.model;

public enum ContactType {
    PHONE("Phone number"),
    CELLPHONE("Cell phone number"),
    HOME_PHONE("Home phone number"),
    SKYPE("Skype") {
        @Override
        protected String toHtmlInternal(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("E-mail") {
        @Override
        protected String toHtmlInternal(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
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

    public String toHtml(String value) {
        return (value == null) ? "" : toHtmlInternal(value);
    }

    protected String toHtmlInternal(String value) {
        return title + ": " + value;

    }
}
