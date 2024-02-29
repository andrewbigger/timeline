package com.biggerconcept.timeline.domain;

import java.time.LocalDate;

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
    * Name of year for presentation
    */
   private String name;
   
   /**
    * Builds default year which is the current year.
    */
   public static Year DEFAULT = new Year(
           firstDayThisYear()
   );
   
   public Year(LocalDate firstDay) {
       this.name = String.valueOf(firstDay.getYear());
       this.firstDay = firstDay;
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
       
       return calculateWeeks() / sprintLength;
   }
   
   /**
    * Builds following year from the current year.
    * 
    * @return next year.
    */
   public Year next() {
       return new Year(
               firstDayFrom(firstDay.getYear() + 1)
       );
   }
   
   /**
    * Builds previous year from the current year.
    * 
    * @return previous year.
    */
   public Year previous() {
       return new Year(
               firstDayFrom(firstDay.getYear() - 1)
       );
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
