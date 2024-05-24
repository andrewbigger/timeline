package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.Formats.Format;
import com.biggerconcept.appengine.reports.IReport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import com.biggerconcept.appengine.reports.elements.IElement;
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
    
    public Report(String name, Format format) {
        super(name, format);
        this.elements = new ArrayList<>();
    }
    
    public Report(String name, Format format, String description) {
        super(name, format, description);
        this.elements = new ArrayList<>();
    }
    
    @JsonDeserialize(contentAs=Element.class)
    public ArrayList<IElement> getElements() {
       if (elements == null) {
           elements = new ArrayList<>();
       }
       
       return elements;
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
