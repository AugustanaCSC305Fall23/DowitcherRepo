package edu.augustana;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LessonPlan {  
    private Map eventMap;
    private String title;

    private List eventList;

    public LessonPlan(String title) {
        this.title = title;
        this.eventMap = new TreeMap<String, EventContainer>();
        this.eventList = new ArrayList<EventContainer>();
    }

    public void addEventContainer(EventContainer addedContainer) {
        eventMap.put(addedContainer.getType(), addedContainer);
        eventList.add(addedContainer);

    }

    private void removeEventContainer() {

    }

    public void renamePlan(String name) {
        App.currentCourse.getLessonPlanMap().remove(title);
        App.currentCourse.getLessonPlanMap().put(title, this);
        int index = App.getCurrentCourse().getLessonPlanList().indexOf(this);
        App.getCurrentCourse().getLessonPlanList().remove(index);
        App.getCurrentCourse().getLessonPlanList().add(index, this);
        this.title = name;
    }

    public Map getEventMap() {
        return eventMap;
    }

    public List getEventList() {return eventList;}

    public String getTitle() {
        return title;
    }

    public String printTree() {
        System.out.println(this.title);
        for (Object key : eventMap.keySet()) {
            EventContainer eventContainer = (EventContainer) eventMap.get(key);
            System.out.println(eventContainer.getTitle() + "(Event type: " + eventContainer.getType() + ")");
            for (int cardIndex = 0; cardIndex < eventContainer.getCards().size(); cardIndex++) {
                if (cardIndex != 0) {
                    Card card = (Card) CardLibrary.cardMap.get(eventContainer.getCards().get(cardIndex));
                    System.out.println("    \\" + card.getTitle());
                }

            }
            System.out.println("");
        }
    	return "";
    }

    public String toString() {
    	return this.title;
    }
}
