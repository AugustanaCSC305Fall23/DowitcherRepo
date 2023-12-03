package edu.augustana;

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
}
