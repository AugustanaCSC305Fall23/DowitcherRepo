package edu.augustana;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
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
        eventMap.put(addedContainer.getType(), addedContainer);

    }

    private void removeEventContainer() {

    }

    public void renamePlan(String name) {
        this.title = name;
    }

    public Map getEventMap() {
        return eventMap;
    }

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

    public static LessonPlan loadFromFile(File logFile) throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(logFile);
        LessonPlan lessonPlan = gson.fromJson(reader, LessonPlan.class);
        Map map = lessonPlan.getEventMap();
        for (Object key : map.keySet()) {
            EventContainer eventContainer = new Gson().fromJson(new Gson().toJson(map.get(key)), EventContainer.class);
            lessonPlan.getEventMap().put(eventContainer.getType(), eventContainer);
        }
        return lessonPlan;
    }
    public void saveToFile(File logFile) throws IOException {
        System.out.println("Saving to file: " + logFile);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String serializedLessonPlan = gson.toJson(this);
        PrintWriter writer = new PrintWriter(new FileWriter(logFile));
        writer.println(serializedLessonPlan);
        writer.close();

    }

    public String toString() {
    	return this.title;
    }
}
