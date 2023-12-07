import static org.junit.jupiter.api.Assertions.*;

import edu.augustana.EquipmentList;
import org.junit.jupiter.api.Test;

public class testEquipmentList {

    private EquipmentList makeSampleEquipmentList() {
        EquipmentList equipmentList = new EquipmentList();
        return equipmentList;
    }

    //this method tests the addEquipment method in EquipmentList.java
    @Test
    void testAddEquipment() {
        EquipmentList test = makeSampleEquipmentList();
        assertEquals(0, test.getEquipmentMap().size());
        String[] equipmentList = new String[]{"Beam", "Floor Beam Resi", "Foam Block",
        "High Bar", "Low Bar", "Pacman/Wall", "slider"};
        test.addEquipment(equipmentList);
        assertEquals(7, test.getEquipmentMap().size());
        test.addEquipment(new String[]{"Panel Mat", "Parallel Bars", "Mushroom"});
        assertEquals(10, test.getEquipmentMap().size());
    }
}
