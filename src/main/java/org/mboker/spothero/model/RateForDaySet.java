package org.mboker.spothero.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



public class RateForDaySet {
    private List<Integer> days;

    private Integer startTime;

    private Integer endTime;

    private Integer price;


    @JsonCreator
    public RateForDaySet(@JsonProperty("days") String days,
                          @JsonProperty("times") String times,
                          @JsonProperty("price") Integer price){
        this.price = price;
        this.days = Arrays.asList(days.split(",")).stream().map(day ->{
            switch (day) {
                case "sun" : return 1;
                case "mon" : return 2;
                case "tues" : return 3;
                case "wed" : return 4;
                case "thurs" : return 5;
                case "fri" : return 6;
                case "sat" : return 7;
                default:
                    return null;
            }
        }).collect(Collectors.toList());
        List<Integer> timesInts = Arrays.asList(times.split("-")).stream().map(time -> Integer.valueOf(time))
                .collect(Collectors.toList());
        this.startTime = timesInts.get(0);
        this.endTime = timesInts.get(1);
    }


    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

}
