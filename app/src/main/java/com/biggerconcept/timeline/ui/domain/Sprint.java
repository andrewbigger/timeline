package com.biggerconcept.timeline.ui.domain;

import com.biggerconcept.timeline.domain.Year;

/**
 *
 * @author Andrew Bigger
 */
public class Sprint {
    private int number;
    
    public Sprint(int number) {
        this.number = number;
    }
    
    public int getNumber() {
        return number;
    }

    public void setNumber(int value) {
        number = value;
    }
}
