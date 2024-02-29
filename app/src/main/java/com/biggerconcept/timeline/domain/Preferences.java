package com.biggerconcept.timeline.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.biggerconcept.projectus.domain.Sprint;
import com.biggerconcept.projectus.domain.Size;

/**
 * Application preferences.
 * 
 * @author Andrew Bigger
 */
public class Preferences {
    /**
     * Example sprint for reference sprint one
     */
    private static final Sprint DEFAULT_REF_SPRINT_ONE = new Sprint("1", 20);
    
    /**
     * Example sprint for reference sprint two
     */
    private static final Sprint DEFAULT_REF_SPRINT_TWO = new Sprint("2", 18);
    
    /**
     * Example sprint for reference sprint three
     */
    private static final Sprint DEFAULT_REF_SPRINT_THREE = new Sprint("3", 22);
    
    /**
     * Example sprint for reference sprint four
     */
    private static final Sprint DEFAULT_REF_SPRINT_FOUR = new Sprint("4", 12);
    
    /**
     * Set size for an extra small task.
     */
    @JsonInclude(Include.NON_NULL)
    private int extraSmallTaskSize;
    
    /**
     * Set size for a small task.
     */
    @JsonInclude(Include.NON_NULL)
    private int smallTaskSize;
    
    /**
     * Set size for a medium task.
     */
    @JsonInclude(Include.NON_NULL)
    private int mediumTaskSize;
    
    /**
     * Set size for a large task.
     */
    @JsonInclude(Include.NON_NULL)
    private int largeTaskSize;
    
    /**
     * Set size for an extra large task.
     */
    @JsonInclude(Include.NON_NULL)
    private int extraLargeTaskSize;
    
     /**
     * Reference sprint one
     */
    @JsonInclude(Include.NON_NULL)
    private Sprint refSprintOne;
    
    /**
     * Reference sprint two
     */
    @JsonInclude(Include.NON_NULL)
    private Sprint refSprintTwo;
    
    /**
     * Reference sprint three
     */
    @JsonInclude(Include.NON_NULL)
    private Sprint refSprintThree;
    
    /**
     * Reference sprint four
     */
    @JsonInclude(Include.NON_NULL)
    private Sprint refSprintFour;

    /**
    * Returns estimate for given task size.
    * 
    * @param size task size
    * 
    * @return estimate as number
    */
    public int estimateFor(Size.TaskSize size) {
        switch(size) {
            case XS:
                return extraSmallTaskSize;
            case S:
                return smallTaskSize;
            case M:
                return mediumTaskSize;
            case L:
                return largeTaskSize;
            case XL:
                return extraLargeTaskSize;
            default:
                return 0;
        }
    }
    
    /**
     * Builds a set of default preferences.
     * 
     * @return 
     */
    public static Preferences defaultPreferences() {
        Preferences p = new Preferences();
        
        p.extraSmallTaskSize = Size.DEFAULT_XS_TASK_SIZE;
        p.smallTaskSize = Size.DEFAULT_S_TASK_SIZE;
        p.mediumTaskSize = Size.DEFAULT_M_TASK_SIZE;
        p.largeTaskSize = Size.DEFAULT_L_TASK_SIZE;
        p.extraLargeTaskSize = Size.DEFAULT_XL_TASK_SIZE;
        
        p.refSprintOne = DEFAULT_REF_SPRINT_ONE;
        p.refSprintTwo = DEFAULT_REF_SPRINT_TWO;
        p.refSprintThree = DEFAULT_REF_SPRINT_THREE;
        p.refSprintFour = DEFAULT_REF_SPRINT_FOUR;
        
        return p;
    }
    
    /**
     * Getter for extra small task size setting.
     * 
     * @return extra small task size
     */
    public int getExtraSmallTaskSize() {
        return extraSmallTaskSize;
    }
    
    /**
     * Getter for small task size setting.
     * 
     * @return small task size
     */
    public int getSmallTaskSize() {
        return smallTaskSize;
    }
    
    /**
     * Getter for medium task size setting.
     * 
     * @return medium task size
     */
    public int getMediumTaskSize() {
        return mediumTaskSize;
    }
    
    /**
     * Getter for large task size setting.
     * 
     * @return large task size
     */
    public int getLargeTaskSize() {
        return largeTaskSize;
    }
    
    /**
     * Getter for extra large task size setting.
     * 
     * @return extra large task size
     */
    public int getExtraLargeTaskSize() {
        return extraLargeTaskSize;
    }
    
    /**
     * Getter for reference sprint one.
     * 
     * @return reference sprint
     */
    public Sprint getRefSprintOne() {
        if (refSprintOne == null) {
            return DEFAULT_REF_SPRINT_ONE;
        }
        
        return refSprintOne;
    }
    
    /**
     * Getter for reference sprint two.
     * 
     * @return reference sprint
     */
    public Sprint getRefSprintTwo() {
        if (refSprintTwo == null) {
            return DEFAULT_REF_SPRINT_TWO;
        }
        
        return refSprintTwo;
    }
    
    /**
     * Getter for reference sprint three.
     * 
     * @return reference sprint
     */
    public Sprint getRefSprintThree() {
        if (refSprintThree == null) {
            return DEFAULT_REF_SPRINT_THREE;
        }
        
        return refSprintThree;
    }
    
    /**
     * Getter for reference sprint four.
     * 
     * @return reference sprint
     */
    public Sprint getRefSprintFour() {
        if (refSprintFour == null) {
            return DEFAULT_REF_SPRINT_FOUR;
        }
        
        return refSprintFour;
    }
    
    /**
     * String setter for extra small task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value string integer value to set as extra small size
     */
    public void setExtraSmallSize(String value) {
        setExtraSmallSize(
                Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for extra small task size setting.
     * 
     * @param value integer value to set as extra small size
     */
    public void setExtraSmallSize(int value) {
        extraSmallTaskSize = value;
    }
    
    /**
     * Setter for small task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value string integer value to set as small size
     */
    public void setSmallSize(String value) {
        setSmallSize(
            Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for small task size setting.
     * 
     * @param value integer value to set as small size
     */
    public void setSmallSize(int value) {
        smallTaskSize = value;
    }
    
     /**
     * Setter for medium task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value string integer value to set as medium size
     */
    public void setMediumSize(String value) {
        setMediumSize(
            Integer.parseInt(value)
        );
    }
   
    /**
     * Setter for medium task size setting.
     * 
     * @param value integer value to set as medium size
     */
    public void setMediumSize(int value) {
        mediumTaskSize = value;
    }
    
    /**
     * Setter for large task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value string value to set as large size
     */
    public void setLargeSize(String value) {
        setLargeSize(
            Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for large task size setting.
     * 
     * @param value integer value to set as large size
     */
    public void setLargeSize(int value) {
        largeTaskSize = value;
    }
    
    /**
     * Setter for extra large task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value string value to set as extra large size
     */
    public void setExtraLargeSize(String value) {
        setExtraLargeSize(
            Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for extra large task size setting.
     * 
     * @param value integer value to set as extra large size
     */
    public void setExtraLargeSize(int value) {
        extraLargeTaskSize = value;
    }
    
    /**
     * Setter for reference sprint one.
     * 
     * @param value new sprint
     */
    public void setRefSprintOne(Sprint value) {
        refSprintOne = value;
    }
    
    /**
     * Setter for reference sprint two.
     * 
     * @param value new sprint
     */
    public void setRefSprintTwo(Sprint value) {
        refSprintTwo = value;
    }
    
    /**
     * Setter for reference sprint three.
     * 
     * @param value new sprint
     */
    public void setRefSprintThree(Sprint value) {
        refSprintThree = value;
    }
    
    /**
     * Setter for reference sprint four.
     * 
     * @param value new sprint
     */
    public void setRefSprintFour(Sprint value) {
        refSprintFour = value;
    }
    
    public com.biggerconcept.projectus.domain.Preferences 
        asProjectusPreferences() {
        com.biggerconcept.projectus.domain.Preferences p = 
                new com.biggerconcept.projectus.domain.Preferences();
        
        p.setExtraSmallSize(getExtraSmallTaskSize());
        p.setSmallSize(getSmallTaskSize());
        p.setMediumSize(getMediumTaskSize());
        p.setLargeSize(getLargeTaskSize());
        p.setExtraLargeSize(getExtraLargeTaskSize());
        p.setRefSprintOne(getRefSprintOne());
        p.setRefSprintTwo(getRefSprintTwo());
        p.setRefSprintThree(getRefSprintThree());
        p.setRefSprintFour(getRefSprintFour());

        return p;
    }
    
}
