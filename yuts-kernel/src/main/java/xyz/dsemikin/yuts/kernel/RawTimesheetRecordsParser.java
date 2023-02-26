package xyz.dsemikin.yuts.kernel;

import xyz.dsemikin.yuts.data.RawTimesheetRecord;
import xyz.dsemikin.yuts.data.TimesheetRecord;
import xyz.dsemikin.yuts.data.TypeOfEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RawTimesheetRecordsParser {

    public static final String SUCCESS_ERR_CODE = "Erfolgreich";
    public static final String WORK_BEGIN_CSV = "Kommen";
    public static final String WORK_END_CSV = "Gehen";

    public List<TimesheetRecord> parseRecordsData(final List<RawTimesheetRecord> rawTimesheetRecords) {
        return rawTimesheetRecords.stream()
                .map(this::parseRecordData)
                .collect(Collectors.toList());
    }

    private TimesheetRecord parseRecordData(final RawTimesheetRecord rawTimeSheetRecord) {
        if (!Objects.equals(rawTimeSheetRecord.errorCode(), SUCCESS_ERR_CODE)) {
            throw new RuntimeException("Error code of the record " + rawTimeSheetRecord + " is not " + SUCCESS_ERR_CODE);
        }
        return new TimesheetRecord(
                parseDateTime(rawTimeSheetRecord.dateTime()),
                parseTypeOfEvent(rawTimeSheetRecord.typeOfEvent()),
                rawTimeSheetRecord.terminalName(),
                rawTimeSheetRecord.errorCode()
        );
    }

    private LocalDateTime parseDateTime(final String dateTimeStringFromCsv) {
        // This is not the best way of doing it, but really simple one.
        // String example: "07.02.2023 13:48:00"
        final String[] dateTimeWords = dateTimeStringFromCsv.split(" ");
        final String dateStr = dateTimeWords[0];
        final String timeStr = dateTimeWords[1];
        final String[] dateWords = dateStr.split("\\.");
        final String[] timeWords = timeStr.split(":");


        final int day = Integer.parseInt(dateWords[0]);
        final int month = Integer.parseInt(dateWords[1]);
        final int year = Integer.parseInt(dateWords[2]);
        final int hour = Integer.parseInt(timeWords[0]);
        final int minute = Integer.parseInt(timeWords[1]);
        final int second = Integer.parseInt(timeWords[2]);

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    private TypeOfEvent parseTypeOfEvent(final String typeOfEventStringFromCsv) {
        return switch (typeOfEventStringFromCsv) {
            case WORK_BEGIN_CSV -> TypeOfEvent.WORK_BEGIN;
            case WORK_END_CSV -> TypeOfEvent.WORK_END;
            default -> throw new RuntimeException("Unexpected type of event " + typeOfEventStringFromCsv);
        };
    }

}
