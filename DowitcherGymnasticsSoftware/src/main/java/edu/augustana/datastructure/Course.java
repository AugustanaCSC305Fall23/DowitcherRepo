package edu.augustana.datastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Course object, which is a collection of LessonPlan objects
 */
public class Course {
    private String courseName;
    private List<LessonPlan> lessonPlanList;


    /**
     * Constructor for Course object
     * @param courseName - name of the Course object
     * @param lessonPlanList - list of LessonPlan objects associated with this Course object
     */
    public Course(String courseName, List<LessonPlan> lessonPlanList) {
        this.courseName = courseName;
        this.lessonPlanList = lessonPlanList;
    }

    /**
     * Constructor for Course object
     * @param courseName - name of the Course object
     */
    public Course (String courseName) {
        this(courseName, new ArrayList<>());
    }

    /**
     * Getter method for the name of this Course object
     * @return - name of this Course object
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Adds a LessonPlan object to this Course object
     * @param lessonPlan - LessonPlan object to be added
     */
    public void addLessonPlan(LessonPlan lessonPlan) {
        lessonPlanList.add(lessonPlan);
    }



    /**
     * Removes a LessonPlan object from this Course object
     * @param lessonPlan - LessonPlan object to be removed
     */
    public void removeLessonPlan(LessonPlan lessonPlan) {
        lessonPlanList.remove(lessonPlan);
    }

    /**
     * Getter method for the list of LessonPlan objects associated with this Course object
     * @return - list of LessonPlan objects associated with this Course object
     */
    public List<LessonPlan> getLessonPlanList() {
        return lessonPlanList;
    }

    /**
     * Getter method for the LessonPlan object associated with the given title
     * @param lessonPlanTitle - title of the LessonPlan object to be returned
     * @return - LessonPlan object associated with the given title
     */
    public LessonPlan getLessonPlan(String lessonPlanTitle) {
    	for (LessonPlan lessonPlan : lessonPlanList) {
    		if (lessonPlan.getTitle().equals(lessonPlanTitle)) {
    			return lessonPlan;
    		}
    	}
    	return null;
    }


    /**
     * Saves this Course object to a file
     * @param logFile - file to save this Course object to
     * @throws IOException - if the file cannot be saved
     */
    public void saveToFile(File logFile) throws IOException {
        System.out.println("Saving to file: " + logFile);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String serializedCourse = gson.toJson(this);
        PrintWriter writer = new PrintWriter(logFile);
        writer.println(serializedCourse);
        writer.close();
    }

    /**
     * Loads a Course object from a file
     * @param logFile - file to load the Course object from
     * @return - Course object loaded from the file
     * @throws IOException - if the file cannot be loaded
     */
    public static Course loadFromFile(File logFile) throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(logFile);
        Course course = gson.fromJson(reader, Course.class);
        for (LessonPlan lessonPlan : course.getLessonPlanList()) {
            for (Object eventContainerObject : lessonPlan.getEventList()) {
                EventContainer eventContainer = new Gson().fromJson(new Gson().toJson(eventContainerObject), EventContainer.class);
                int eventIndex = lessonPlan.getEventList().indexOf(eventContainerObject);
                lessonPlan.getEventList().set(eventIndex, eventContainer);

            }
        }
        return course;
    }

    public void renameCourse(String newCourseName) {
    	courseName = newCourseName;
    }

}
