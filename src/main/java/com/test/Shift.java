package com.test;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class Shift {

    private Date timeIn;
    private Date timeOut;
    private Double duration;

    public Shift(Date timeIn, Date timeOut, double duration) {
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.duration = duration;
    }

    public void updateTimeOut(Shift shift) {
        this.timeOut = shift.getTimeOut();
        this.duration += shift.getDuration();
    }

}
