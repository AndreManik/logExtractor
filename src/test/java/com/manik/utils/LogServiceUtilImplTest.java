package com.manik.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogServiceUtilImplTest {

    Set<String> givenSet;
    LogServiceUtilImpl service = new LogServiceUtilImpl();

    @BeforeEach
    void setUp() {
        givenSet = new HashSet<>();
        givenSet.add("2019-09-03T20:31:12.853;ERROR;4");
        givenSet.add("2019-09-03T20:57:22.052;ERROR;3");
        givenSet.add("2019-09-03T21:57:22.052;ERROR;3");
    }

    @Test
    void logCountByHours() {

        LocalDateTime time1 = LocalDateTime.parse("2019-09-03T20:00:00.000");
        LocalDateTime time2 = LocalDateTime.parse("2019-09-03T21:00:00.000");

        Map<LocalDateTime, Integer> resultMap;

        resultMap = service.logCountByHours(givenSet);
        assertNotNull(resultMap);
        assertEquals(2, resultMap.size());
        assertEquals(2, resultMap.get(time1));
        assertEquals(1, resultMap.get(time2));
    }
}