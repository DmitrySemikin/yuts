package xyz.dsemikin.yuts.data;

import java.time.Duration;
import java.time.LocalDateTime;

public class WorkPeriod {
    final LocalDateTime periodBegin;
    final LocalDateTime periodEnd;

    public WorkPeriod(final LocalDateTime periodBegin, final LocalDateTime periodEnd) {
        this.periodBegin = periodBegin;
        this.periodEnd = periodEnd;
    }

    public LocalDateTime getPeriodBegin() {
        return periodBegin;
    }

    public LocalDateTime getPeriodEnd() {
        return periodEnd;
    }

    public Duration getDuration() {
        return Duration.between(periodBegin, periodEnd);
    }

    public double getFullDurationInHours() {
        return ((double)getDuration().toSeconds())/3600.0;
    }

    public double getFullDurationInMinutes() {
        return ((double)getDuration().toSeconds())/60.0;
    }
}
