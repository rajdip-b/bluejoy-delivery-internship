package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static void main( String[] args ) throws FileNotFoundException {
        var employees = getEmployees(); // <positionId, Employee>
        addShifts(employees);

        var output = "OUTPUT: \n\n";

        var analytics = new Analytics(employees);

        output += "Employees who have worked for 7 consecutive days: \n";
        output += analytics.hasWorkedForConsecutive7Days().stream()
                .map(Employee::toString)
                .collect(Collectors.joining("\n"));

        output += "\n\nEmployees who have worked for more than 14 hours in a single shift: \n";
        output += analytics.worksMoreThan14HoursInSingleShift().stream()
                .map(Employee::toString)
                .collect(Collectors.joining("\n"));

        output += "\n\nEmployees who have worked for less than 10 hours but more than 1 hour between shifts: \n";
        output += analytics.haveLessThan10HoursBetweenShifts().stream()
                .map(Employee::toString)
                .collect(Collectors.joining("\n"));

        writeToOutput(output);
    }

    private static void writeToOutput(String output) {
        var out = new File("output.txt");
        try {
            out.createNewFile();
            var writer = new FileWriter(out);
            writer.write(output);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Map<String, Employee> getEmployees() throws FileNotFoundException {
        var file = new File("assignment.csv");
        var sc = new Scanner(file);
        var employees = new HashMap<String, Employee>(); // <positionId, Employee>

        // Remove the first line
        sc.nextLine();
        while (sc.hasNextLine()) {
            // For each line,
            var line = sc.nextLine();

            // Parse the employee
            var employee = Helper.parseEmployee.apply(line);
            var positionId = employee.getPositionId();

            // Add the employee to the map
            employees.put(positionId, employee);
        }

        return employees;
    }

    private static void addShifts(Map<String, Employee> tally) throws FileNotFoundException {
        var file = new File("assignment.csv");
        var sc = new Scanner(file);

        // Remove the first line
        sc.nextLine();
        while (sc.hasNextLine()) {
            // For each line,
            var line = sc.nextLine();

            // Parse the employee
            var positionId = line.split(",")[0];

            // Parse the shift
            var shift = Helper.parseShift.apply(line);

            // Add the shift to the employee
            var shifts = tally.get(positionId).getShifts();
            var timeIn = Helper.convertToYYYYMMDD.apply(shift.getTimeIn());

            // If the employee has already worked for that day, update the time out
            // and the duration, else add the shift to the employee
            if (shifts.containsKey(timeIn)) {
                var s = shifts.get(timeIn);
                s.updateTimeOut(shift);
                shifts.replace(timeIn, s);
            } else {
                shifts.put(timeIn, shift);
            }
        }
    }
}
