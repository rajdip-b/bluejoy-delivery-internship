package com.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

public class Helper {

    public static Function<String, Double> parseTimeCard = (String timeCard) -> {
        var arr = timeCard.split(":");
        // If the time card is null, or empty, an exception would be thrown. This
        // is considered as the employee didn't work for that shift.
        try {
            var hours = Double.parseDouble(arr[0]);
            var minutes = Double.parseDouble(arr[1]);
            return hours + (minutes / 60);
        } catch (Exception e) {
            return 0.0;
        }
    };

    public static Function<Date, String> convertToYYYYMMDD = (Date date) -> {
        var sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null)
            return "";
        return sdf.format(date);
    };

    public static Function<String, Date> parseDate = (String date) -> !Objects.equals(date, "") ? new Date(date) : null;

    public static Function<String, Employee> parseEmployee = (String line) -> {
        var arr = line.split(",");

        var positionId = arr[0];
        var positionStatus = arr[1];
        var payCycleStartDate = arr[5];
        var payCycleEndDate = arr[6];
        var firstName = arr[8].substring(1, arr[8].length() - 1);
        var lastName = arr[7].substring(1);
        var fileNumber = arr[9];

        var name = firstName + " " + lastName;
        var parsedPayCycleStartDate = parseDate.apply(payCycleStartDate);
        var parsedPayCycleEndDate = parseDate.apply(payCycleEndDate);
        return new Employee(positionId, positionStatus, fileNumber, name, parsedPayCycleStartDate, parsedPayCycleEndDate);
    };

    public static Function<String, Shift> parseShift = (String line) -> {
        var arr = line.split(",");

        var timeIn = arr[2];
        var timeOut = arr[3];
        var timeCard = arr[4];

        var parsedTimeCard = parseTimeCard.apply(timeCard);
        var parsedTimeIn = parseDate.apply(timeIn);
        var parsedTimeOut = parseDate.apply(timeOut);
        return new Shift(parsedTimeIn, parsedTimeOut, parsedTimeCard);
    };

}
