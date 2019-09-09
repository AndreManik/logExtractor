package com.manik.utils;

import java.time.LocalDateTime;
import java.util.Objects;

public class TimeSampler {
    private final int interval;
    private final LocalDateTime localDateTime;

    public TimeSampler(int interval, LocalDateTime localDateTime) {
        this.interval = interval;
        this.localDateTime = localDateTime;
    }

    public int getInterval() {
        return interval;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSampler that = (TimeSampler) o;
        return interval == that.interval &&
                Objects.equals(localDateTime, that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interval, localDateTime);
    }

    @Override
    public String toString() {
        return "TimeSampler{" +
                "interva=" + interval +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
