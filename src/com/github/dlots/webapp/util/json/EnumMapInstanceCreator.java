package com.github.dlots.webapp.util.json;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;
import java.util.EnumMap;

public class EnumMapInstanceCreator<K extends Enum<K>, V> implements
        InstanceCreator<EnumMap<K, V>> {
    private final Class<K> enumClazz;

    public EnumMapInstanceCreator(final Class<K> enumClazz) {
        super();
        this.enumClazz = enumClazz;
    }

    @Override
    public EnumMap<K, V> createInstance(final Type type) {
        return new EnumMap<K, V>(enumClazz);
    }
}
