package com.manik.utils;

import java.nio.file.Path;

public interface LogServiceUtil<T, U> {
    public T logExtractorByLogLevel(Path paths, LogLevel logLevel);
    public U logCountByHours(T extractedLogs);
    public void logSaver(U sortedLogs, Path path, LogLevel logLevel);
}
