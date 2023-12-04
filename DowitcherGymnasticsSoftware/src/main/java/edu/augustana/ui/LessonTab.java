package edu.augustana.ui;

import edu.augustana.LessonPlan;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LessonTab extends Tab {

    private static Map<String, LessonTab> lessonTabMap = new TreeMap<>();

    private static List lessonTabList = new ArrayList();
    private String title;

    private LessonPlan lessonPlan;

    private LessonPlanUI lessonPlanUI;

    public LessonTab(LessonPlan lessonPlan) {
        this.setText(lessonPlan.getTitle());
        this.lessonPlan = lessonPlan;
        this.lessonPlanUI = (LessonPlanUI) LessonPlanUI.getLessonPlanUIMap().get(lessonPlan.getTitle());
        lessonTabList.add(this);
        lessonTabMap.put(lessonPlan.getTitle(), this);
    }

    public void setTitle(String newTitle) {
//        lessonTabMap.remove(lessonPlan.getTitle());
        this.title = newTitle;
        lessonTabMap.put(newTitle, this);
        this.setText(newTitle);
    }

    public String getTitle() {
        return this.getText();
    }
}
