package fr.tick.utils;

import fr.minuskube.inv.content.SlotPos;

import java.util.Arrays;
import java.util.List;

public class GuiUtils {

    public static List<SlotPos> getGlass() {
        return Arrays.asList(SlotPos.of(0,0), SlotPos.of(0,1), SlotPos.of(1, 0),
                SlotPos.of(4, 0), SlotPos.of(5, 0), SlotPos.of(5, 1),
                SlotPos.of(5, 7), SlotPos.of(5, 8), SlotPos.of(4, 8),
                SlotPos.of(0, 7), SlotPos.of(0, 8), SlotPos.of(1, 8));
    }
}
