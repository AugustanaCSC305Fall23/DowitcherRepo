package edu.augustana;


import java.util.Map;
import java.util.TreeMap;

public class LessonPlan {
    private Map eventMap;
    private String title;

    public LessonPlan(String title) {
        this.title = title;
        this.eventMap = new TreeMap<String, EventContainer>();
    }

    public void addEventContainer(EventContainer addedContainer) {
        eventMap.put(addedContainer.getTitle(), addedContainer);

    }

    private void removeEventContainer() {

    }

    private void renamePlan() {

    }

}
