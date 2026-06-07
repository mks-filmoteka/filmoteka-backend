package io.github.maxsouldrake.filmoteka.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

public class TestUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules();

    @SafeVarargs
    public static <T> Set<T> testSetOf(T... items) {
        return new HashSet<>(Set.of(items));
    }
}