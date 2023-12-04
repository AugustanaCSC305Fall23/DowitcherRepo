package edu.augustana;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Course {
    private String courseName;
    private List<LessonPlan> lessonPlanList;

    private Map<String, LessonPlan> lessonPlanMap;

    public Course(String courseName, List<LessonPlan> lessonPlanList) {
        this.courseName = courseName;
        this.lessonPlanList = lessonPlanList;
        this.lessonPlanMap = new TreeMap<>();
    }

    public Course (String courseName) {
        this(courseName, new ArrayList<>());
    }

    public String getCourseName() {
        return courseName;
    }

    public void addLessonPlan(LessonPlan lessonPlan) {
        lessonPlanList.add(lessonPlan);
        lessonPlanMap.put(lessonPlan.getTitle(), lessonPlan);
    }



    public void removeLessonPlan(LessonPlan lessonPlan) {
        lessonPlanList.remove(lessonPlan);
    }

    public List<LessonPlan> getLessonPlanList() {
        return lessonPlanList;
    }

    public Map getLessonPlanMap() {
    	return lessonPlanMap;
    }

    public void saveToFile(File logFile) throws IOException {
        System.out.println("Saving to file: " + logFile);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String serializedCourse = gson.toJson(this);
        PrintWriter writer = new PrintWriter(logFile);
        writer.println(serializedCourse);
        writer.close();
    }

    public static Course loadFromFile(File logFile) throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(logFile);
        Course course = gson.fromJson(reader, Course.class);
        Map map = course.getLessonPlanMap();
        for (Object key : map.keySet()) {
            LessonPlan lessonPlan = new Gson().fromJson(new Gson().toJson(map.get(key)), LessonPlan.class);
            course.getLessonPlanMap().put(lessonPlan.getTitle(), lessonPlan);
            for (Object eventContainerKey : lessonPlan.getEventMap().keySet()) {
                EventContainer eventContainer = new Gson().fromJson(new Gson().toJson(lessonPlan.getEventMap().get(eventContainerKey)), EventContainer.class);
                lessonPlan.getEventMap().put(eventContainer.getType(), eventContainer);
                for (int cardIndex = 0; cardIndex < eventContainer.getCards().size(); cardIndex++) {
                    Card card = (Card) new Gson().fromJson(new Gson().toJson(CardLibrary.cardMap.get(eventContainer.getCards().get(cardIndex))), Card.class);
                    eventContainer.getCards().set(cardIndex, card.getCode());
                }
            }
        }
        System.out.printf("Loaded course: %s\n", course.getCourseName());
        System.out.println("Course Lesson Plans:");
        for (Object key : course.getLessonPlanMap().keySet()) {
            LessonPlan lessonPlan = (LessonPlan) course.getLessonPlanMap().get(key);
            System.out.printf("Lesson Plan: %s\n", lessonPlan.getTitle());
            System.out.println("Lesson Plan Event Containers:");
            for (Object eventContainerKey : lessonPlan.getEventMap().keySet()) {
                EventContainer eventContainer = (EventContainer) lessonPlan.getEventMap().get(eventContainerKey);
                System.out.printf("Event Container: %s\n", eventContainer.getType());
                System.out.println("Event Container Cards:");
                for (String cardCode : eventContainer.getCards()) {
                    System.out.printf("Card Code: %s\n", cardCode);
                }
            }
        }
        return course;
    }
}
