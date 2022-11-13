package com.github.dlots.webapp;

import com.github.dlots.webapp.model.ContactType;
import com.github.dlots.webapp.model.Resume;
import com.github.dlots.webapp.model.section.*;
import org.jetbrains.annotations.NotNull;

import java.time.Month;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume r = getFilledResume("Григорий Кислин");

        System.out.println(r.getFullName() + '\n');
        for (ContactType type: ContactType.values()) {
            System.out.println(type.getTitle() + ": " + r.getContact(type));
        }
        System.out.println();

        for (SectionType type: SectionType.values()) {
            System.out.println(type.getTitle() + ": " + r.getSection(type));
        }
    }

    public static @NotNull Resume getFilledResume(String fullName) {
        return getFilledResume(null, fullName);
    }

    public static @NotNull Resume getFilledResume(String uuid, String fullName) {
        Resume r;
        if (uuid != null) {
            r = new Resume(uuid, fullName);
        } else {
            r = new Resume(fullName);
        }

        r.addContact(ContactType.PHONE, "+7(921) 855-0482");
        r.addContact(ContactType.SKYPE, "grigory.kislin");
        r.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        r.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        r.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        r.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        r.addContact(ContactType.PERSONAL_PAGE, "http://gkislin.ru/");
        r.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        r.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        r.addSection(SectionType.ACHIEVEMENTS, new ListSection(
                "Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."
        ));
        r.addSection(SectionType.QUALIFICATIONS, new ListSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB"
        ));
        r.addSection(SectionType.EXPERIENCE, new OrganizationsSection(
                new Organization("Java Online Projects", "http://javaops.ru/", new Organization.Position(2013, Month.of(10), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                new Organization("Wrike", "https://www.wrike.com/", new Organization.Position(2014, Month.of(10), 2016, Month.of(1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."))
        ));
        r.addSection(SectionType.EDUCATION, new OrganizationsSection(
                new Organization("Coursera", "https://www.coursera.org/course/progfun", new Organization.Position(2013, Month.of(3), 2013, Month.of(5), "'Functional Programming Principles in Scala' by Martin Odersky", null)),
                new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", new Organization.Position(2011, Month.of(3), 2011, Month.of(4), "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null))
        ));

        return r;
    }
}
