package com.manik.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.*;

public class LogReaderImpl implements LogReader<Set<String>>{

    private final SingleLogger LOGGER = SingleLogger.getInstance();

    @Override
    public Set<String> readLogsFilesNames(String pathToLogs) {
        LOGGER.log(Level.INFO, "Read File names from log directory");
        Path logDig = Paths.get(pathToLogs);

        Set<String> setOfFileNames = new TreeSet<>();
        try {
            Files.list(Paths.get(logDig.toString()))
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        setOfFileNames.add(logDig + "\\" + file.getFileName().toString());
                    });
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.toString());
        }
        return setOfFileNames;
    }

    @Override
    public Set<String> extractStringFromLogFile(Set<String> listOfLogFiles) {
        Set<String> setOfLogLinesWithTypeOfMessage = new TreeSet<>();

        LOGGER.log(Level.INFO, "Extract strings from log files");

        listOfLogFiles.forEach(file -> {
            try {
                Files.lines(Paths.get(file), StandardCharsets.UTF_8).forEach(readString -> {
                            setOfLogLinesWithTypeOfMessage.add(readString);
                        }
                        );
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.toString());
            }
        });

        return setOfLogLinesWithTypeOfMessage;
    }
}
