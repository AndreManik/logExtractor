package com.manik.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.logging.*;

public class LogServiceUtilImpl implements LogServiceUtil<Set<String>, Map<LocalDateTime, Integer>> {

    private final SingleLogger LOGGER = SingleLogger.getInstance();

    @Override
    public Set<String> logExtractorByLogLevel(Set<String> logStrings, LogLevel logLevel) {
        Set<String> resultSet = new TreeSet<>();
        logStrings.forEach(innerString -> {
            if (innerString.contains(logLevel.toString())) {
                resultSet.add(innerString);
            }
        });
        return resultSet;
    }

    @Override
    public Map<LocalDateTime, Integer> logCountingByTimeIntervals(Set<String> extractedLogs, int min) {
        Map<LocalDateTime, Integer> resultMap = new TreeMap<>();
        Stack<LocalDateTime> timeStack = new Stack<>();

        try {
            extractedLogs.forEach(innerString -> {
            //for (String innerString : extractedLogs) {
                LocalDateTime time = null;
                try {
                    time = LocalDateTime.parse(innerString.split(";")[0]);
                if (!timeStack.isEmpty() && (time.isAfter(timeStack.peek()) && time.isBefore(timeStack.peek().plusMinutes(min)))) {
                    LocalDateTime lastTime = timeStack.pop();
                    int countOfTime = resultMap.get(lastTime);
                    resultMap.put(lastTime, countOfTime + 1);
                    timeStack.push(lastTime);
                }
                else {
                    if(!timeStack.isEmpty()) {
                        timeStack.pop();
                    }
                    time = LocalDateTime.of(time.toLocalDate(), LocalTime.of(time.getHour(), time.getMinute(), 00));
                    timeStack.push(time);
                    resultMap.put(time, 1);
                }
                } catch (DateTimeParseException e) {
                    LOGGER.log(Level.WARNING, "The text \"{0}\" could not be parsed", innerString.split(";")[0]);
                }
            //}
            });
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.toString());
        }

        return resultMap;
    }

    @Override
    public Map<LocalDateTime, Integer> logCountingByTimeIntervals(Set<String> extractedLogs, int hour, int min) {
        int interval = hour * 60 + min;
        return logCountingByTimeIntervals(extractedLogs, interval);
    }

    @Override
    public Map<LocalDateTime, Integer> logCountingByTimeIntervals(Set<String> extractedLogs, int day, int hour, int min) {
        int interval = (day * 24 * 60) + (hour * 60) + min;
        return logCountingByTimeIntervals(extractedLogs, interval);
    }

    @Override
    public void logSaver(Map<LocalDateTime, Integer> sortedLogs, Path path, LogLevel logLevel, int interval) {
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH.mm");

        List<String> resultList = new ArrayList<>();

        sortedLogs.forEach((key, value)  -> {
            String hourTime = key.format(timeFormatter) + "-";
            String timeString = key.format(dayFormatter) + ", " + key.format(timeFormatter) +" - " +
                                key.plusMinutes(interval).format(dayFormatter) + ", " + key.plusMinutes(interval).format(timeFormatter) ;
            resultList.add(timeString  + "\nCount of " + logLevel + ": " + value);
        });
        try {
            Files.write(path, resultList, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.toString());
        }
    }
}
