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
            System.out.println(eventContainer.getTitle());
            for (int cardIndex = 0; cardIndex < eventContainer.getCards().size(); cardIndex++) {
                if (cardIndex != 0) {
                    Card card = (Card) eventContainer.getCards().get(cardIndex);
                    System.out.println("    \\" + card.getTitle());
                }

            }
            System.out.println("");
        }
    	return "";
    }

}
