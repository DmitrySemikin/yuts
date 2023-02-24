package xyz.dsemikin.yuts.data;

public record RawTimeSheetRecord(
        String dateTime,
        String typeOfEvent,
        String terminalName,
        String errorCode
)
{
    // intentionally empty
}
