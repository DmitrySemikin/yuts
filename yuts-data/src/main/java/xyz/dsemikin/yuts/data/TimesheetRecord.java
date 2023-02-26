package xyz.dsemikin.yuts.data;

import java.time.LocalDateTime;

public record TimesheetRecord(
        LocalDateTime dateTime,
        TypeOfEvent typeOfEvent,
        String terminalName,
        String errorCode
)
{
    // intentionally empty
}