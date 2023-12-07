package edu.augustana;

import java.util.*;

public class EquipmentList {
    private Map<String, Integer> equipmentAmountMap;

    public EquipmentList() {
        equipmentAmountMap = new TreeMap<>();
    }

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

    public Map getEquipmentMap() {
        return equipmentAmountMap;
    }

    public List getList() {
        List<String> equipmentList = new ArrayList<String>();
        equipmentList.addAll(equipmentAmountMap.keySet());
        return equipmentList;
    }
}
