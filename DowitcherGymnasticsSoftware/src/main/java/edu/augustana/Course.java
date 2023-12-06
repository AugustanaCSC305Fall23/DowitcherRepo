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


    public Course(String courseName, List<LessonPlan> lessonPlanList) {
        this.courseName = courseName;
        this.lessonPlanList = lessonPlanList;
    }

    public Course (String courseName) {
        this(courseName, new ArrayList<>());
    }

    public String getCourseName() {
        return courseName;
    }

    public void addLessonPlan(LessonPlan lessonPlan) {
        lessonPlanList.add(lessonPlan);
//        lessonPlanMap.put(lessonPlan.getTitle(), lessonPlan);
    }



    public void removeLessonPlan(LessonPlan lessonPlan) {
        lessonPlanList.remove(lessonPlan);
    }

    public List<LessonPlan> getLessonPlanList() {
        return lessonPlanList;
    }

    public LessonPlan getLessonPlan(String lessonPlanTitle) {
    	for (LessonPlan lessonPlan : lessonPlanList) {
    		if (lessonPlan.getTitle().equals(lessonPlanTitle)) {
    			return lessonPlan;
    		}
    	}
    	return null;
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
        for (LessonPlan lessonPlan : course.getLessonPlanList()) {
            for (Object eventContainerObject : lessonPlan.getEventList()) {
                EventContainer eventContainer = new Gson().fromJson(new Gson().toJson(eventContainerObject), EventContainer.class);
                int eventIndex = lessonPlan.getEventList().indexOf(eventContainerObject);
                lessonPlan.getEventList().set(eventIndex, eventContainer);
//                for (int cardIndex = 0; cardIndex < eventContainer.getCards().size(); cardIndex++) {
//                    Card card = new Gson().fromJson(new Gson().toJson(CardLibrary.cardMap.get(eventContainer.getCards().get(cardIndex))), Card.class);
//                    eventContainer.getCards().set(cardIndex, card.getCode());
//                }

            }
        }
        return course;
    }

}
