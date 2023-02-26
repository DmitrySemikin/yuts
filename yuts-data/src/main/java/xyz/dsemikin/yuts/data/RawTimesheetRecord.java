package xyz.dsemikin.yuts.data;

public record RawTimesheetRecord(
        String dateTime,
        String typeOfEvent,
        String terminalName,
        String errorCode
)
{
    // intentionally empty
}
