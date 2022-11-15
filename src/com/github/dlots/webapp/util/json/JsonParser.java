package com.github.dlots.webapp.util.json;

import com.github.dlots.webapp.model.ContactType;
import com.github.dlots.webapp.model.section.Section;
import com.github.dlots.webapp.model.section.SectionType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.Writer;
import java.util.EnumMap;

public class JsonParser {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter<>())
            .registerTypeAdapter(
                    new TypeToken<EnumMap<SectionType, Section>>(){}.getType(),
                    new EnumMapInstanceCreator<SectionType, Section>(SectionType.class))
            .registerTypeAdapter(
                    new TypeToken<EnumMap<ContactType, String>>(){}.getType(),
                    new EnumMapInstanceCreator<ContactType, String>(ContactType.class))
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

}
