package com.manik.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class LogServiceUtilImpl implements LogServiceUtil<Set<String>, Map<LocalDateTime, Integer>> {
    @Override
    public Set<String> logExtractorByLogLevel(Path path, LogLevel logLevel) {
        Set<String> resultSet = new HashSet<>();
        try {
            Files.list(Paths.get(path.toString()))
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            Files.lines(file, StandardCharsets.UTF_8).forEach(innerString -> {
                                if (innerString.contains(logLevel.toString())) {
                                    resultSet.add(innerString);
                                }
                            });

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public Map<LocalDateTime, Integer> logCountByHours(Set<String> extractedLogs) {
        Map<LocalDateTime, Integer> resultMap = new TreeMap<>();
        extractedLogs.forEach(innerString -> {
            try {
                LocalDateTime time = LocalDateTime.parse(innerString.split(";")[0]);
                time = time.of(time.toLocalDate(), LocalTime.of(time.getHour(), 0));
                resultMap.put(time, resultMap.getOrDefault(time, 0) + 1);
            } catch (DateTimeParseException e) {
                //e.printStackTrace();
            }
        });

        return resultMap;
    }

    @Override
    public void logSaver(Map<LocalDateTime, Integer> sortedLogs, Path path, LogLevel logLevel) {
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH.mm");

        List<String> resultList = new ArrayList<>();

        sortedLogs.forEach((key, value)  -> {
            String hourTime = key.format(timeFormatter) + "-";
            hourTime += key.getHour() == 23 ? "00.00" : LocalTime.of(key.getHour() + 1, 00).format(timeFormatter);;
            String timeString = key.format(dayFormatter) + ", " + hourTime;
            resultList.add(timeString  + "\nCount of " + logLevel + ": " + value);
        });
        try {
            Files.write(path, resultList, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
