package com.biggerconcept.timeline.domain;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents a year.
 * 
 * @author Andrew Bigger
 */
public class Year {
   /**
    * First day of the year.
    */
   private LocalDate firstDay;
   
   /**
    * Last day of the year. 
    */
   private LocalDate lastDay;
   
   /**
    * Start sprint
    */
   private int startSprint;
   
   /**
    * Name of year for presentation
    */
   private String name;
   
   /**
    * Builds default year which is the current year.
    */
   public static Year DEFAULT = new Year(
           firstDayThisYear(),
           1
   );
   
   /**
    * Full Constructor.
    * 
    * @param firstDay first day of year
    * @param startSprint start sprint number
    */
   public Year(LocalDate firstDay, int startSprint) {
       this.name = String.valueOf(firstDay.getYear());
       this.firstDay = firstDay;
       this.lastDay = lastDayFrom(firstDay);
       this.startSprint = startSprint;
   }
   
   public Year(int year) {
       this.name = String.valueOf(year);
       this.firstDay = LocalDate.of(year, 1, 1);
       this.lastDay = lastDayFrom(firstDay);
   }
   
   /**
    * Getter for name.
    * 
    * @return name
    */
   public String getName() {
       return name;
   }
   
   /**
    * Getter for first day.
    * 
    * @return first day of year as local date.
    */
   public LocalDate getFirstDay() {
       return firstDay;
   }
   
   /**
    * Getter for start sprint number.
    * 
    * @return start sprint
    */
   public int getStartSprint() {
       return startSprint;
   }

   /**
    * Getter for last day
    * 
    * @return last day of year as local date
    */
   public LocalDate getLastDay() {
       return lastDay;
   }
   
   /**
    * Setter for name.
    * 
    * @param value new name
    */
   public void setName(String value) {
       name = value;
   }
   
   /**
    * Setter for first day.
    * 
    * @param value new first day
    */
   public void setFirstDay(LocalDate value) {
       firstDay = value;
   }
   
   /**
    * Setter for last day.
    * 
    * @param value new last day
    */
   public void setLastDay(LocalDate value) {
       lastDay = value;
   }
   
   /**
    * Sets start sprint
    * 
    * @param value new start sprint value
    */
   public void setStartSprint(int value) {
       startSprint = value;
   }
   
   /**
    * Calculates the number of days in the year.
    * 
    * @return number of days.
    */
   public int calculateDays() {
       int days = 365;
       
       if (firstDay.isLeapYear()) {
           days = 366;
       }
       
       return days;
   }
   
   /**
    * Calculates number of weeks in year.
    * 
    * @return number of weeks.
    */
   public int calculateWeeks() {
       return calculateDays() / 7;
   }
   
   /**
    * Calculates the number of sprints in a year.
    * 
    * @param sprintLength length of sprint.
    * 
    * @return number of sprints.
    */
   public int calculateSprints(int sprintLength) {
       if (sprintLength == 0) {
           return 0;
       }
       
       if (sprintLength == 2) {
           return 24;
       }
       
       return 52 / sprintLength;
   }
   
   /**
    * Calculates last sprint
    * 
    * @param prefs document preferences
    * @return last sprint number
    */
   public int lastSprint(Preferences prefs) {
       return startSprint + calculateSprints(prefs.getSprintLength());
   }
   
   /**
    * Builds list of sprint numbers
    * 
    * @param prefs document preferences
    * @return list of sprints
    */
   public ArrayList<Integer> sprintNumbers(Preferences prefs) {
       int startSprint = getStartSprint();
       int endSprint = lastSprint(prefs);
       
       ArrayList<Integer> sprints = new ArrayList<>();
       
       for (int i = startSprint; i < endSprint; i++) {
           sprints.add(i);
       }
       
       return sprints;
   }
   
   /**
    * Returns number of sprints in the year.
    * 
    * @param prefs document preferences
    * @return sprint count
    */
   public int countAvailableSprints(Preferences prefs) {
       return sprintNumbers(prefs).size();
   }
   
   /**
    * Returns number of sprints available in a quarter.
    * 
    * @param prefs document preferences
    * 
    * @return number of sprints available in the quarter
    */
   public int countAvailableSprintsPerQuarter(Preferences prefs) {
       return countAvailableSprints(prefs) / 4;
   }
   
   /**
    * Returns first sprint in given quarter
    * 
    * @param quarter quarter number
    * @param prefs document preferences
    * 
    * @return sprint number at beginning of quarter
    */
   public int firstSprintInQuarter(int quarter, Preferences prefs) {
       if (quarter == 1) {
            return getStartSprint();
        }
        
        return ((quarter - 1) * countAvailableSprintsPerQuarter(prefs)) 
                + getStartSprint();
   }
   
   /**
    * Returns the last sprint in the quarter.
    * 
    * @param quarter quarter number
    * @param prefs document preferences
    * 
    * @return last sprint number for the quarter
    */
   public int lastSprintInQuarter(int quarter, Preferences prefs) {
       return firstSprintInQuarter(quarter, prefs) 
               + (countAvailableSprintsPerQuarter(prefs) - 1);
   }
   
   /**
    * Returns number of available points in the year.
    * 
    * @param prefs document preferences
    * @return available points
    */
   public int countAvailablePoints(Preferences prefs) {
       return prefs.calculateAvailablePointsIn(countAvailableSprints(prefs));
   }
   
   /**
    * Builds following year from the current year.
    * 
    * @param prefs document preferences
    * 
    * @return next year.
    */
   public Year next(Preferences prefs) {
       return new Year(
               firstDayFrom(firstDay.getYear() + 1),
               lastSprint(prefs)
       );
   }
   
   /**
    * Builds previous year from the current year.
    * 
    * @param prefs document preferences
    * 
    * @return previous year.
    */
   public Year previous(Preferences prefs) {
       int yearSprints = calculateSprints(prefs.getSprintLength());
       
       if ((getStartSprint() - yearSprints) < prefs.getStartSprintNumber()) {
           return this;
       }
       
       return new Year(
               firstDayFrom(firstDay.getYear() - 1),
               getStartSprint() - yearSprints
       );
   }
   
   /**
    * Returns number of years since given year
    * 
    * @param year start year
    * 
    * @return years between
    */
   public int yearsSince(Year year) {
       return getFirstDay().getYear() - year.getFirstDay().getYear();
   }
   
   /**
    * Builds the first day of a given year.
    * 
    * @param year year to build first day.
    * 
    * @return first day of given year.
    */
   private static LocalDate firstDayFrom(int year) {
       return LocalDate.of(year, 1, 1);
   }
   
   /**
    * Builds the first day of the current year as a local date.
    * 
    * @return first day of the current year.
    */
   private static LocalDate firstDayThisYear() {
       LocalDate now = LocalDate.now();
       
       return firstDayFrom(now.getYear());
    }
   
   /**
    * Calculates the last day of the year from the given number
    * of days.
    * 
    * @param firstDay day to start from
    * @return last day of year as local date
    */
   private static LocalDate lastDayFrom(LocalDate firstDay) {
       int days = 365;
       
       if (firstDay.isLeapYear()) {
           days = 366;
       }
       
       return firstDay.plusDays(days);
   }
  
}
