package com.biggerconcept.timeline.ui.domain;

import com.biggerconcept.timeline.domain.Year;

/**
 *
 * @author Andrew Bigger
 */
public class Sprint {
    private Year year;
    private int number;
    
    public Sprint(Year year, int number) {
        this.year = year;
        this.number = number;
    }
    
    public Year getYear() {
        return year;
    }

    public int getNumber() {
        return number;
    }
    
    public void setYear(Year value) {
        year = value;
    }

    public void setNumber(int value) {
        number = value;
    }
}
