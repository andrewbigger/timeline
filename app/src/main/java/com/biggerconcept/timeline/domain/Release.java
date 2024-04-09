package com.biggerconcept.timeline.domain;

import java.util.UUID;

/**
 * Release domain model
 * 
 * @author Andrew Bigger
 */
public class Release {
   private UUID id;
   private String name;
   
   public Release() {
       id = UUID.randomUUID();
       name = "";
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
    * Getter for name
    * 
    * @return release name
    */
   public String getName() {
       return name;
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
    * Setter for name
    * 
    * @param value new name
    */
   public void setName(String value) {
       name = value;
   }

}
