package io.github.maxsouldrake.filmoteka.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules();

    @SafeVarargs
    public static <T> Set<T> testSetOf(T... items) {
        return new HashSet<>(Set.of(items));
    }

    @SafeVarargs
    public static <T> List<T> testListOf(T... items) {
        return new ArrayList<>(List.of(items));
    }
}