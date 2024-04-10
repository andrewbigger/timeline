package com.biggerconcept.timeline.domain;

import com.biggerconcept.projectus.domain.Epic;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Release domain model
 * 
 * @author Andrew Bigger
 */
public class Release {
   /**
    * Numbers releases
    * 
    * @param releases releases to number.
    */
    public static void numberReleases(ArrayList<Release> releases) {
       for (int i = 0; i < releases.size(); i++) {
           releases.get(i).setNumber(i + 1);
       }
   }
   
   private UUID id;
   private int number;
   private String name;
   private String description;
   private ArrayList<UUID> epicIds;
   
   public Release() {
       id = UUID.randomUUID();
       number = 0;
       name = "";
       description = "";
       epicIds = new ArrayList<>();
   }
   
   /**
    * Returns release id.
    * 
    * @return id;
    */
   public UUID getId() {
       return id;
   }
   
   /**
    * Getter for release number.
    * 
    * @return release number
    */
   public int getNumber() {
       return number;
   }
   
   /**
    * Getter for name
    * 
    * @return release name
    */
   public String getName() {
       return name;
   }
  
   /**
    * Getter for epic ids
    * 
    * @return epic IDs
    */
   public ArrayList<UUID> getEpicIds() {
       if (epicIds == null) {
           epicIds = new ArrayList<>();
       }
       
       return epicIds;
   }
   
   /**
    * Returns release description
    * 
    * @return description
    */
   public String getDescription() {
       return description;
   }
   
   /**
    * Setter for ID
    * 
    * @param value new id
    */
   public void setId(UUID value) {
       id = value;
   }
   
   /**
    * Setter for number
    * 
    * @param value new release number
    */
   public void setNumber(int value) {
       number = value;
   }
   
   /**
    * Setter for name
    * 
    * @param value new name
    */
   public void setName(String value) {
       name = value;
   }
   
   /**
    * Setter for epic ids.
    * 
    * @param value new set of epics
    */
   public void setEpicIds(ArrayList<UUID> value) {
       epicIds = value;
   }
   
   /**
    * Setter for description
    * 
    * @param value new description
    */
   public void setDescription(String value) {
       description = value;
   }
   
   /**
    * Adds an epic to the release.
    * 
    * @param epic to add to release
    */
   public void addEpic(Epic epic) {
       getEpicIds().add(epic.getId());
   }
   
   /**
    * Removes epic from the release.
    * 
    * @param epic epic to remove
    */
   public void removeEpic(Epic epic) {
       getEpicIds().remove(epic.getId());
   }
   
   /**
    * Retrieves epics by ID
    * 
    * @return set of epics
    */
   public ArrayList<Epic> epics(Document doc) {
       ArrayList<Epic> epics = new ArrayList<>();
       
       for (UUID id : getEpicIds()) {
           Epic found = doc.findEpicById(id);
           
           if (found != null) {
               epics.add(found);
           }
       }
       
       return epics;
   }
   
   /**
    * Returns last epic ID
    * 
    * @return last epic ID
    */
   public UUID lastEpicId() {
       if (getEpicIds().size() == 0) {
           return null;
       }
       
       return getEpicIds().get(getEpicIds().size() - 1);
   }
}
