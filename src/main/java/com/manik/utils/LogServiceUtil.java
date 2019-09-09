package com.manik.utils;

import java.nio.file.Path;

public interface LogServiceUtil<T, U> {
    public T logExtractorByLogLevel(T logStrings, LogLevel logLevel);
    public U logCountingByTimeIntervals(T extractedLogs, int min);
    public U logCountingByTimeIntervals(T extractedLogs, int hour, int min);
    public U logCountingByTimeIntervals(T extractedLogs, int day, int hour, int min);
    public void logSaver(U sortedLogs, Path path, LogLevel logLevel, int interval);
}
