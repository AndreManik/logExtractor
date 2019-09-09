package com.manik.utils;

import java.util.List;

public interface LogReader<T> {

    T readLogsFilesNames(String pathToLogs);
    T extractStringFromLogFile(T listOfLogFiles);


}
