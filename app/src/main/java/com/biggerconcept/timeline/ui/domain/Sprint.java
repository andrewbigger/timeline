package com.biggerconcept.timeline.ui.domain;

/**
 *
 * @author Andrew Bigger
 */
public class Sprint {
    private int number;
    private int points;
    
    public Sprint(int number) {
        this.number = number;
        this.points = 0;
    }
    
    public Sprint(int number, int points) {
        this.number = number;
        this.points = points;
    }
    
    public int getNumber() {
        return number;
    }
    
    public int getPoints() {
        return points;
    }

    public void setNumber(int value) {
        number = value;
    }
    
    public void setPoints(int value) {
        points = value;
    }
    
    public void addPoints(int value) {
        points += value;
    }
    
    public void merge(Sprint sprint) {
        addPoints(sprint.getPoints());
    }

    public int availablePoints(int max) {
        return max - points;
    }
}
