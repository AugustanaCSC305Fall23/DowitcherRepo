package edu.augustana.ui;

import edu.augustana.LessonPlan;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class represents a LessonTab object, which is Tab object that contains a LessonPlanUI object
 */
public class LessonTab extends Tab {

    private static Map<String, LessonTab> lessonTabMap = new TreeMap<>();

    private static List lessonTabList = new ArrayList();
    private String title;

    private LessonPlan lessonPlan;

    private LessonPlanUI lessonPlanUI;

    /**
     * Constructor for LessonTab object
     * @param lessonPlan - LessonPlan object associated with this LessonTab object
     */
    public LessonTab(LessonPlan lessonPlan) {
        this.setText(lessonPlan.getTitle());
        this.lessonPlan = lessonPlan;
        this.lessonPlanUI = (LessonPlanUI) LessonPlanUI.getLessonPlanUIMap().get(lessonPlan.getTitle());
        lessonTabList.add(this);
        lessonTabMap.put(lessonPlan.getTitle(), this);
    }

    /**
     * Setter method for this Tab's title
     * @param newTitle - new title for this Tab
     */
    public void setTitle(String newTitle) {
//        lessonTabMap.remove(lessonPlan.getTitle());
        this.title = newTitle;
        lessonTabMap.put(newTitle, this);
        this.setText(newTitle);
    }

    /**
     * Getter method for this Tab's title
     * @return - this Tab's title
     */
    public String getTitle() {
        return this.getText();
    }

    /**
     * Getter method for the list of LessonTab objects
     * @return - list of LessonTab objects
     */
    public static List getLessonTabList() {
    	return lessonTabList;
    }

    /**
     * Getter method for the LessonPlan Tabs map
     * @return - LessonPlan Tabs map
     */
    public static Map getLessonTabMap() {
    	return lessonTabMap;
    }
}
