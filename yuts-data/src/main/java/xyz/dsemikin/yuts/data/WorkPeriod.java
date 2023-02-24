package xyz.dsemikin.yuts.data;

import java.time.LocalDate;
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

    public LocalDate getPeriodDay() {
        return periodBegin.toLocalDate();
    }
}
