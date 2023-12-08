package edu.augustana;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class represents a LessonPlan object, which is a collection of EventContainer objects
 */
public class LessonPlan {
    private String title;

    private List eventList;

    private EquipmentList equipmentList;

    /**
     * Constructor for LessonPlan object
     * @param title - title of the LessonPlan object
     */
    public LessonPlan(String title) {
        this.title = title;
        this.eventList = new ArrayList<EventContainer>();
        equipmentList = new EquipmentList();
    }

    /**
     * Adds an EventContainer object to this LessonPlan object
     * @param addedContainer - EventContainer object to be added
     */
    public void addEventContainer(EventContainer addedContainer) {
        eventList.add(addedContainer);

    }

    /**
     * Renames this LessonPlan object
     * @param name - new name for this LessonPlan object
     */
    public void renamePlan(String name) {
        int index = App.getCurrentCourse().getLessonPlanList().indexOf(this);
        App.getCurrentCourse().getLessonPlanList().remove(index);
        App.getCurrentCourse().getLessonPlanList().add(index, this);
        this.title = name;
    }


    /**
     * Returns a list of EventContainer objects associated with this LessonPlan object
     * @return - list of EventContainer objects associated with this LessonPlan object
     */
    public List getEventList() {return eventList;}

    /**
     * Returns an EventContainer object associated with this LessonPlan object
     * @param eventContainerTitle - title of the EventContainer object to be returned
     * @return - EventContainer object associated with the eventContainerTitle
     */
    public EventContainer getEventContainer(String eventContainerTitle) {
    	for (Object eventContainer : eventList) {
    		if (((EventContainer) eventContainer).getTitle().equals(eventContainerTitle)) {
    			return (EventContainer) eventContainer;
    		}
    	}
    	return null;
    }

    public String getTitle() {
        return title;
    }

    public EquipmentList getEquipmentList() {
        return equipmentList;
    }


    public String toString() {
    	return this.title;
    }
}
