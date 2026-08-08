package io.github.mksfilmoteka.catalog.util;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import tools.jackson.databind.json.JsonMapper;

import java.util.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

public class TestUtil {

    public static final JsonMapper JSON_MAPPER = JsonMapper.builder().findAndAddModules().build();

    @SafeVarargs
    public static <T> Set<T> testSetOf(T... items) {
        return new HashSet<>(Set.of(items));
    }

    @SafeVarargs
    public static <T> List<T> testListOf(T... items) {
        return new ArrayList<>(List.of(items));
    }

    public static Jwt testJwt(Map<String, Object> claims) {
        return Jwt.withTokenValue("token")
                .header("alg", "none")
                .claims(existingClaims -> existingClaims.putAll(claims))
                .build();
    }

    public static RequestPostProcessor adminJwt() {
        return jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}