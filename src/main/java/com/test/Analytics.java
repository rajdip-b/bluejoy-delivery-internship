package com.test;

import java.time.ZoneId;
import java.util.*;

public class Analytics {

    private final Map<String, Employee> employeeMap;

    public Analytics(Map<String, Employee> employees) {
        this.employeeMap = employees;
    }

    public List<Employee> haveLessThan10HoursBetweenShifts() {
        var employees = new ArrayList<Employee>();

        // Iterate through every employee and their shifts combination
        for (var employee : employeeMap.values()) {
            var shifts = new ArrayList<>(employee.getShifts().values());
            shifts.sort(Comparator.comparing(Shift::getTimeIn)); // Sort shifts by timeIn

            // Check the time between consecutive shifts
            for (int i = 0; i < shifts.size() - 1; i++) {
                var currentShift = shifts.get(i);
                var nextShift = shifts.get(i + 1);

                if (currentShift.getTimeOut() != null && nextShift.getTimeIn() != null) {
                    long timeBetweenShiftsMillis = nextShift.getTimeIn().getTime() - currentShift.getTimeOut().getTime();
                    double timeBetweenShiftsHours = timeBetweenShiftsMillis / (60.0 * 60.0 * 1000.0);

                    if (timeBetweenShiftsHours > 1.0 && timeBetweenShiftsHours < 10.0) {
                        employees.add(employee);
                        break; // No need to continue checking for this employee's shifts
                    }
                }
            }
        }

        // Return the list of employees who have less than 10 hours between shifts but greater than 1 hour
        return employees;
    }

    public List<Employee> worksMoreThan14HoursInSingleShift() {
        var employees = new ArrayList<Employee>();

        // Iterate through every employee and their shifts combination
        for (var employee : employeeMap.values()) {
            for (Shift shift : employee.getShifts().values()) {
                // Check if the employee has both timeIn and timeOut
                if (shift.getTimeIn() != null && shift.getTimeOut() != null) {
                    long shiftDurationMillis = shift.getTimeOut().getTime() - shift.getTimeIn().getTime();
                    double shiftDurationHours = shiftDurationMillis / (60.0 * 60.0 * 1000.0);

                    if (shiftDurationHours > 14.0) {
                        employees.add(employee);
                        break; // No need to continue checking for this employee's shifts
                    }
                }
            }
        }

        // Return the list of employees who have worked for more than 14 hours in a single shift
        return employees;
    }

    public List<Employee> hasWorkedForConsecutive7Days() {
        var employees = new ArrayList<Employee>();

        // Iterate through every employee and their shifts combination
        for (var employee : employeeMap.values()) {
            var dates = employee.getShifts().values().stream()
                    .filter(shift -> shift.getTimeIn() != null)
                    .map(shift -> shift.getTimeIn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .sorted()
                    .toList();

            // Check if the employee has worked for 7 consecutive days
            int consecutive = 0;
            for (int i = 0; i < dates.size() - 1; i++) {
                var date = dates.get(i);
                var nextDate = dates.get(i + 1);
                if (date.plusDays(1).equals(nextDate)) {
                    consecutive++;
                    if (consecutive >= 6) { // Check for 7 consecutive days
                        employees.add(employee);
                        break; // No need to continue checking for this employee
                    }
                } else {
                    consecutive = 0;
                }
            }
        }

        return employees;
    }

//    public List<Employee> hasWorkedForConsecutive7Days() {
//        var employees = new ArrayList<Employee>();
//
//        // Iterate through every employee and their shifts combination
//        for (var entry : tally.entrySet()) {
//            // Get the current employee
//            var employee = entry.getKey();
//            // Get the employee's shifts
//            var shifts = entry.getValue();
//
//            // Hash set allows us to remove duplication. the timeIn of a shift
//            // is stored in the form of a string in the format of yyyy-MM-dd
//            var conscutiveDays = new HashSet<String>();
//
//            // For each shift, add the timeIn to the hash set
//            for (var shift : shifts) {
//                // In case the in time is null, skip the shift
//                if (shift.getTimeIn() == null)
//                    continue;
//                conscutiveDays.add(new SimpleDateFormat("yyyy-MM-dd").format(shift.getTimeIn()));
//            }
//
//            // Convert the shift dates to LocalDate as it helps us in
//            // comparing dates
//            var dates = new ArrayList<LocalDate>();
//            conscutiveDays.forEach(d -> dates.add(LocalDate.parse(d)));
//            dates.sort(Comparator.naturalOrder());
//
//            // Check if the employee has worked for 7 consecutive days
//            var consecutive = 0;
//            for (int i = 0; i < dates.size() - 1; i++) {
//                var date = dates.get(i);
//                var nextDate = dates.get(i + 1);
//                if (date.plusDays(1).equals(nextDate)) {
//                    consecutive++;
//                } else {
//                    consecutive = 0;
//                }
//            }
//
//            // If the employee has worked for 7 consecutive days, add the
//            if (consecutive >= 7) {
//                employees.add(employee);
//            }
//        }
//
//        // Return the list of employees who have worked for 7 consecutive days
//        return employees;
//    }

}
