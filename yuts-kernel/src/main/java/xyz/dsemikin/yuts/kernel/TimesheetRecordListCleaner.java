package xyz.dsemikin.yuts.kernel;

import xyz.dsemikin.yuts.data.TimesheetRecord;
import xyz.dsemikin.yuts.data.TypeOfEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TimesheetRecordListCleaner {

    public List<TimesheetRecord> cleanupTimesheetRecordsList(final List<TimesheetRecord> inTimesheetRecords) {

        // 1. Sort by date-time
        final List<TimesheetRecord> sortedList = inTimesheetRecords.stream().sorted(Comparator.comparing(TimesheetRecord::dateTime)).toList();

        // 2. Remove pairs come-go with same date-time (0-length periods).
        //    We need to do it, because otherwise the sorting is not going to work well.
        //    - we want that when sorted by time, the "event type" is: come - go - come - go ... etc.
        if (sortedList.isEmpty()) {
            return sortedList;
        }

        final List<TimesheetRecord> filteredList = new ArrayList<>();
        final List<TimesheetRecord> coincidentRecords = new ArrayList<>();
        for (final TimesheetRecord timesheetRecord : sortedList) {
            if (coincidentRecords.isEmpty()) {
                coincidentRecords.add(timesheetRecord);
                continue;
            }
            TimesheetRecord previousTimesheetRecord = coincidentRecords.get(coincidentRecords.size() - 1);
            if (previousTimesheetRecord.dateTime().isAfter(timesheetRecord.dateTime())) {
                throw new RuntimeException("The list is not properly sorted");
            } else if (previousTimesheetRecord.dateTime().isEqual(timesheetRecord.dateTime())) {
                coincidentRecords.add(timesheetRecord);
            } else {
                // previousTimesheetRecord.dateTime() < timesheetRecord.dateTime()
                filteredList.addAll(filterCoincidentRecords(coincidentRecords));
                coincidentRecords.clear();
                coincidentRecords.add(timesheetRecord);
            }
        }
        filteredList.addAll(filterCoincidentRecords(coincidentRecords));
        return filteredList;
    }

    private List<TimesheetRecord> filterCoincidentRecords(final List<TimesheetRecord> coincidentRecords) {
        if (coincidentRecords.isEmpty()) {
            return new ArrayList<>(); // we always return copy of the list, not the list itself
        }
        // Check, that all elements in the list have the same datetime
        final LocalDateTime dateTime = coincidentRecords.get(0).dateTime();
        if (coincidentRecords.stream().anyMatch(timesheetRecord -> !timesheetRecord.dateTime().isEqual(dateTime))) {
            throw new RuntimeException("All records must have the same datetime value.");
        }
        final List<TimesheetRecord> beginRecords = coincidentRecords.stream().filter(timesheetRecord -> timesheetRecord.typeOfEvent().equals(TypeOfEvent.WORK_BEGIN)).toList();
        final List<TimesheetRecord> endRecords = coincidentRecords.stream().filter(timesheetRecord -> timesheetRecord.typeOfEvent().equals(TypeOfEvent.WORK_END)).toList();
        final int difference = beginRecords.size() - endRecords.size();
        if (difference == 0) {
            return new ArrayList<>();
        } else if (difference > 0) {
            return new ArrayList<>(beginRecords.subList(0, difference));
        } else {
            // difference < 0
            return new ArrayList<>(endRecords.subList(0, -difference));
        }
    }
}
