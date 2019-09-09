package com.manik.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.mockito.ArgumentMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogServiceUtilImplTest {

    Set<String> givenSet;
    LogServiceUtilImpl service = new LogServiceUtilImpl();

    @BeforeEach
    void setUp() {
        givenSet = new TreeSet<>();
        givenSet.add("2019-09-01T20:31:12.853;ERROR;4");
        givenSet.add("2019-09-01T20:57:22.052;ERROR;3");
        givenSet.add("2019-09-01T21:05:22.052;ERROR;3");
        givenSet.add("2019-09-01T21:07:22.052;ERROR;3");
        givenSet.add("2019-09-01T21:17:22.052;ERROR;3");
        givenSet.add("2019-09-02T01:05:22.052;INFO;3");
        givenSet.add("2019-09-02T15:31:12.853;ERROR;4");
        givenSet.add("2019-09-02T16:00:12.853;ERROR;4");
        givenSet.add("2019-09-02T16:30:12.853;ERROR;4");
        givenSet.add("2019-09-04T00:31:12.853;ERROR;4");
        givenSet.add("2019-09-04T01:34:12.853;INFO;4");
        givenSet.add("2019-09-04T02:34:12.853;ERROR;4");
        givenSet.add("2019-09-04T23:58:12.853;ERROR;4");
        givenSet.add("2019-09-05T00:51:12.853;ERROR;4");
        givenSet.add("2019-09-05T01:21:12.853;ERROR;4");
        givenSet.add("2019-09-05T23:21:12.853;WARNING;4");
        givenSet.add("2019-09-05T23:25:12.853;WARNING;4");
    }

    @Test
    void logCountingByTimeIntervals5min() {
        LocalDateTime time1 = LocalDateTime.parse("2019-09-01T21:05");

        Map<LocalDateTime, Integer> resultMap;

        resultMap = service.logCountingByTimeIntervals(givenSet, 5);
        assertNotNull(resultMap);
        assertEquals(15, resultMap.size());
        assertEquals(2, resultMap.get(time1));
    }

    @Test
    void logCountingByTimeIntervals30min() {
        LocalDateTime time1 = LocalDateTime.parse("2019-09-01T21:05");

        Map<LocalDateTime, Integer> resultMap;

        resultMap = service.logCountingByTimeIntervals(givenSet, 30);
        assertNotNull(resultMap);
        assertEquals(12, resultMap.size());
        assertEquals(3, resultMap.get(time1));
    }

    @Test
    void logCountingByTimeIntervals1h() {
        LocalDateTime time1 = LocalDateTime.parse("2019-09-02T15:31");

        Map<LocalDateTime, Integer> resultMap;

        resultMap = service.logCountingByTimeIntervals(givenSet, 1,00);
        assertNotNull(resultMap);
        assertEquals(9, resultMap.size());
        assertEquals(3, resultMap.get(time1));

    }

    @Test
    void logCountingByTimeIntervals1day() {
        LocalDateTime time1 = LocalDateTime.parse("2019-09-01T20:31");

        Map<LocalDateTime, Integer> resultMap;

        resultMap = service.logCountingByTimeIntervals(givenSet, 1,0,00);
        assertNotNull(resultMap);
        assertEquals(3, resultMap.size());
        assertEquals(9, resultMap.get(time1));

    }
}