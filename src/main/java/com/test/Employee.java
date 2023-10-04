package com.test;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode
public class Employee {

    private String positionId;
    private String positionStatus;
    private String fileNumber;
    private String name;
    private Date payCycleStartDate;
    private Date payCycleEndDate;
    private Map<String, Shift> shifts; // <timeIn, Shift>

    public Employee(String positionId, String positionStatus, String fileNumber, String name, Date payCycleStartDate, Date payCycleEndDate) {
        this.positionId = positionId;
        this.positionStatus = positionStatus;
        this.fileNumber = fileNumber;
        this.name = name;
        this.payCycleStartDate = payCycleStartDate;
        this.payCycleEndDate = payCycleEndDate;
        shifts = new HashMap<>();
    }

    public String toString() {
        return String.format("Name: %s, Position ID: %s", name, positionId);
    }

}
