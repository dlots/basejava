package com.github.dlots.webapp.model.section;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationsSection extends Section {
    private final List<Organization> organizations;

    OrganizationsSection() {
        organizations = new ArrayList<>();
    }

    public OrganizationsSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationsSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationsSection that = (OrganizationsSection) o;
        boolean os = organizations.equals(that.organizations);
        return os;

    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}
