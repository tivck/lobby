package fr.tick.gui.host;

import fr.tick.Hub;
import fr.tick.gui.MainGui;
import fr.tick.utils.GuiUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import net.idalya.core.common.server.CreateServer;
import net.idalya.core.common.server.ServerType;
import net.idalya.core.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CreateHostGui implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("createHostGui")
            .manager(Hub.getInstance().getInventoryManager())
            .provider(new CreateHostGui())
            .size(6, 9)
            .title("§6Idalya §r- §6Créer mon serveur")
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        for(SlotPos slotPos : GuiUtils.getGlass()) {
            contents.set(slotPos, ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .setDurability((short) 1)
                    .setName("§c").toItemStack()));
        }

        contents.set(SlotPos.of(5, 4), ClickableItem.of(new ItemBuilder(Material.ARROW)
                .setName("§6Retour").toItemStack(), event -> {
            MainGui.INVENTORY.open(player);
        }));

        int i = 3;
        for(ServerType type : ServerType.values()) {
            contents.set(SlotPos.of(2, i), ClickableItem.of(new ItemBuilder(Material.NETHER_STAR).setName("§6" + type.getName()).toItemStack(), event -> {
                player.closeInventory();
                CreateServer.getInstance().createServer(type, player);
            }));
            i++;
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
