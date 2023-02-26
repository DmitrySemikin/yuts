package xyz.dsemikin.yuts.kernel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dsemikin.yuts.data.TimesheetRecord;
import xyz.dsemikin.yuts.data.TypeOfEvent;
import xyz.dsemikin.yuts.data.WorkPeriod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WorkPeriodsCalculator {

    public List<WorkPeriod> calculateWorkPeriods(final List<TimesheetRecord> timesheetRecords) {
        Logger logger = LoggerFactory.getLogger(WorkPeriodsCalculator.class);
        boolean waitForBegin = true;
        LocalDateTime beginDateTime = LocalDateTime.now();
        LocalDateTime endDateTime;
        final List<Integer> skippedRecordNums = new ArrayList<>();
        final List<WorkPeriod> workPeriods = new ArrayList<>();
        for (int tsNum = 0; tsNum < timesheetRecords.size(); ++tsNum) {
            final TimesheetRecord timesheetRecord = timesheetRecords.get(tsNum);
            if (waitForBegin) {
                if (timesheetRecord.typeOfEvent().equals(TypeOfEvent.WORK_END)) {
                    logger.warn("Record num " + tsNum + ": Waiting for WORK_BEGIN, but got WORK_END. Skipping the record.");
                    skippedRecordNums.add(tsNum);
                    continue;
                }
                beginDateTime = timesheetRecord.dateTime();
                waitForBegin = false; // now wait for END
            } else {
                // wait for END
                if (timesheetRecord.typeOfEvent().equals(TypeOfEvent.WORK_BEGIN)) {
                    logger.warn("Record num " + tsNum + ": Waiting for WORK_END, but got WORK_BEGIN. Skipping the record.");
                    skippedRecordNums.add(tsNum);
                    continue;
                }
                endDateTime = timesheetRecord.dateTime();
                workPeriods.add(new WorkPeriod(beginDateTime, endDateTime));
                waitForBegin = true;
            }
        }
        logger.warn("" + skippedRecordNums.size() + " rows had problems. Row numbers: " + skippedRecordNums);
        return workPeriods;
    }
}
