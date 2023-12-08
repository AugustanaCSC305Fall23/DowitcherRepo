package edu.augustana;

import java.util.*;

/**
 * This class represents an EquipmentList object, which is a collection of equipment
 */
public class EquipmentList {
    private Map<String, Integer> equipmentAmountMap;

    /**
     * Constructor for EquipmentList object
     */
    public EquipmentList() {
        equipmentAmountMap = new TreeMap<>();
    }

    /**
     * Adds equipment to this EquipmentList object
     * @param equipmentList - list of equipment to be added
     */
    public void addEquipment(String[] equipmentList) {
        for (String equipment : equipmentList) {
            equipment = equipment.strip();
            if (!equipment.equalsIgnoreCase("none")) {
                if (equipmentAmountMap.containsKey(equipment)) {
                    equipmentAmountMap.put(equipment, equipmentAmountMap.get(equipment) + 1);
                } else {
                    equipmentAmountMap.put(equipment, 1);
                }
            }
        }
//        for (Object equipmentObject : App.getCurrentLessonPlan().getEquipmentList().getList()) {
//            String equipment = (String) equipmentObject;
//            System.out.println(equipment);
//        }
    }

    /**
     * Removes equipment from this EquipmentList object
     * @param equipmentList - list of equipment to be removed
     */
    public void removeEquipment(String[] equipmentList) {
        for (String equipment : equipmentList) {
            equipment = equipment.strip();
            if (equipmentAmountMap.containsKey(equipment)) {
                equipmentAmountMap.put(equipment, equipmentAmountMap.get(equipment) - 1);
            }
            if (equipmentAmountMap.get(equipment) == 0) {
                equipmentAmountMap.remove(equipment);
            }
        }
//        for (Object equipmentObject : App.getCurrentLessonPlan().getEquipmentList().getList()) {
//            String equipment = (String) equipmentObject;
//            System.out.println(equipment);
//        }
    }

    /**
     * Getter method for the map of equipment and their amounts
     * @return - map of equipment and their amounts
     */
    public Map getEquipmentMap() {
        return equipmentAmountMap;
    }

    /**
     * Getter method for the list of equipment
     * @return - list of equipment
     */
    public List getList() {
        List<String> equipmentList = new ArrayList<String>();
        equipmentList.addAll(equipmentAmountMap.keySet());
        return equipmentList;
    }
}
