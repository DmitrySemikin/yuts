package xyz.dsemikin.yuts.kernel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dsemikin.yuts.data.TimesheetRecord;
import xyz.dsemikin.yuts.data.TypeOfEvent;

import java.util.List;

public class TimesheetRecordListValidator {

    /**
     * it is assumed, that {@code timesheetRecords}  are first handled with {@link TimesheetRecordListCleaner}
     *
     * It does not return any value, but just prints out the warnings, if some issues were found.
     * This behavior maybe changed later...
     *
     * @param timesheetRecords   list of records to be validated - must be sorted by datetime and cleaned up.
     */
    public void validateTimesheetRecordList(final List<TimesheetRecord> timesheetRecords) {
        Logger logger = LoggerFactory.getLogger(TimesheetRecordListValidator.class);
        if (timesheetRecords.isEmpty()) {
            return;
        }
        if (timesheetRecords.get(0).typeOfEvent().equals(TypeOfEvent.WORK_END)) {
            logger.warn("First record in the list of timesheet records has the type " + TypeOfEvent.WORK_END);
        }
        if (timesheetRecords.get(timesheetRecords.size() - 1).typeOfEvent().equals(TypeOfEvent.WORK_BEGIN)) {
            logger.warn("Last record in the list of timesheet records has the type " + TypeOfEvent.WORK_BEGIN);
        }

        TimesheetRecord previousTimesheetRecord = timesheetRecords.get(0);
        for (int kk = 1; kk < timesheetRecords.size(); ++kk) {
            TimesheetRecord timesheetRecord = timesheetRecords.get(kk);
            if (previousTimesheetRecord.typeOfEvent().equals(timesheetRecord.typeOfEvent())) {
                // kk and kk+1 because we want to use 1-based numbering in the message.
                logger.warn("The records num " + kk + " and " + (kk+1) + " have same type of event: " + timesheetRecord.typeOfEvent() );
            }
            previousTimesheetRecord = timesheetRecord;
        }
    }
}
