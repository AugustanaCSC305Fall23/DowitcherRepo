package edu.augustana;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LessonPlan {
    private String title;

    private List eventList;

    private EquipmentList equipmentList;

    public LessonPlan(String title) {
        this.title = title;
        this.eventList = new ArrayList<EventContainer>();
        equipmentList = new EquipmentList();
    }

    public void addEventContainer(EventContainer addedContainer) {
        eventList.add(addedContainer);

    }

    private void removeEventContainer() {

    }

    public void renamePlan(String name) {
        int index = App.getCurrentCourse().getLessonPlanList().indexOf(this);
        App.getCurrentCourse().getLessonPlanList().remove(index);
        App.getCurrentCourse().getLessonPlanList().add(index, this);
        this.title = name;
    }


    public List getEventList() {return eventList;}

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
