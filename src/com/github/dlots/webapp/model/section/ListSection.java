package com.github.dlots.webapp.model.section;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection implements Section {
    private final List<String> items;

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return items.equals(((ListSection) o).items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
