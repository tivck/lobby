package fr.tick.gui.hub;

import fr.tick.Hub;
import fr.tick.gui.host.HostGui;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import net.idalya.core.tools.ItemBuilder;
import net.minecraft.server.v1_8_R3.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class HubGui implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("hubGui")
            .manager(Hub.getInstance().getInventoryManager())
            .provider(new HubGui())
            .size(3, 9)
            .title("§6Idalya §r- §6Hubs")
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        inventoryContents.set(SlotPos.of(1, 1), ClickableItem.of(new ItemBuilder(Material.ENDER_PEARL).setName("§6§lLOBBY-01").toItemStack(), event -> {

            if(player.getServer().getName().equals("lobby-01")) {
                player.sendMessage("§cVous êtes déjà dans ce lobby.");
                return;
            }

        }));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
