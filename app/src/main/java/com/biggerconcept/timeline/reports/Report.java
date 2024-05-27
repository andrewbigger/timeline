package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import com.biggerconcept.appengine.reports.elements.IElement;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * In memory representation of a report
 * 
 * @author Andrew Bigger
 */
public class Report extends com.biggerconcept.appengine.reports.Report implements IReport {    
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
    private ArrayList<IElement> elements;
    
    public Report() {
        super();
        this.elements = new ArrayList<>();
    }
    
    public Report(String name) {
        super(name);
        this.elements = new ArrayList<>();
    }
    
    public Report(String name, String description) {
        super(name, description);
        this.elements = new ArrayList<>();
    }
    
    @JsonDeserialize(contentAs=Element.class)
    public ArrayList<IElement> getElements() {
       if (elements == null) {
           elements = new ArrayList<>();
       }
       
       return elements;
    }
    
    @JsonIgnore
    public void setState(State value) {
        for (IElement e : elements) {
            Element target = (Element) e;
            
            target.setState(value);
        }
    }

    public void setElements(ArrayList<IElement> value) {
       elements = value;
   }
   
   public IElement findElement(UUID id) {
       for (IElement el : getElements()) {
           if (el.getId().equals(id)) {
               return el;
           }
       }
       
       return null;
   }
   
   public boolean hasElement(UUID id) {
       IElement found = findElement(id);
       
       return found != null;
   }

   public void addElement(IElement element) {
       getElements().add(element);
   }
   
   public void removeElement(IElement element) {
       getElements().remove(element);
   }

   public void moveElementUp(IElement element) {
       if (element == getElements().get(0)) {
           return;
       }
       
       ArrayList<IElement> elements = getElements();
       
       IElement prev = elements.get(elements.indexOf(element) - 1);
       
       Collections.swap(
                elements,
                elements.indexOf(element),
                elements.indexOf(prev)
        );
   }
   
   public void moveElementDown(IElement element) {
       ArrayList<IElement> elements = getElements();
       
        if (element == elements.get(elements.size() - 1)) {
           return;
       }
       
       IElement next = elements.get(elements.indexOf(element) + 1);
       
       Collections.swap(
                elements,
                elements.indexOf(next),
                elements.indexOf(element)
        );
   }
}
