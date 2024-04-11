package com.biggerconcept.timeline.ui.domain;

/**
 * In memory representation of a Sprint.
 * 
 * @author Andrew Bigger
 */
public class Sprint implements Cloneable {
    /**
     * Sprint number
     */
    private int number;
    
    /**
     * Active points
     */
    private int points;
    
    /**
     * Default constructor.
     * 
     * @param number sprint number
     */
    public Sprint(int number) {
        this.number = number;
        this.points = 0;
    }
    
    /**
     * Full constructor.
     * 
     * @param number sprint number
     * @param points active points
     */
    public Sprint(int number, int points) {
        this.number = number;
        this.points = points;
    }
    
    /**
     * Getter for number
     * 
     * @return sprint number
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Getter for points.
     * 
     * @return active points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Setter for number
     * 
     * @param value new sprint number
     */
    public void setNumber(int value) {
        number = value;
    }
    
    /**
     * Setter for points.
     * 
     * @param value new points value
     */
    public void setPoints(int value) {
        points = value;
    }
    
    /**
     * Adds points to sprint.
     * 
     * @param value points to add
     */
    public void addPoints(int value) {
        points += value;
    }
    
    /**
     * Merges given sprint into this one.
     * 
     * @param sprint sprint to merge
     */
    public void merge(Sprint sprint) {
        addPoints(sprint.getPoints());
    }

    /**
     * Calculates number of available points in the
     * sprint from the given maximum.
     * 
     * @param max maximum number of points
     * 
     * @return available points
     */
    public int availablePoints(int max) {
        return max - points;
    }
    
    /**
     * Clones sprint.
     * 
     * @return cloned sprint
     * 
     * @throws CloneNotSupportedException when unable to clone sprint
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
