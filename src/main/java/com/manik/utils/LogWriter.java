package com.manik.utils;

import java.util.List;

public interface LogWriter<T> {
    void saveLogStringsToFile(String pathToLog, T stringsToSave);
}
