package fr.tick.gui.host;

import fr.tick.Hub;
import fr.tick.gui.MainGui;
import fr.tick.utils.GuiUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotIterator;
import fr.minuskube.inv.content.SlotPos;
import net.idalya.core.common.Common;
import net.idalya.core.common.player.IdaPlayer;
import net.idalya.core.common.server.CreateServer;
import net.idalya.core.common.server.IdaServer;
import net.idalya.core.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MyServersGui implements InventoryProvider {

    private final IdaPlayer idaPlayer;

    public MyServersGui(IdaPlayer idaPlayer) {
        this.idaPlayer = idaPlayer;
    }

    public static SmartInventory getInventory(IdaPlayer idaPlayer) {
        return SmartInventory.builder()
                .id("myServers" + Bukkit.getPlayer(idaPlayer.getUuid()).getName())
                .manager(Hub.getInstance().getInventoryManager())
                .provider(new MyServersGui(idaPlayer))
                .size(6, 9)
                .title("§6Idalya §r- §6Mes serveurs")
                .build();
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        for(SlotPos slotPos : GuiUtils.getGlass()) {
            inventoryContents.set(slotPos, ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .setDurability((short) 1)
                    .setName("§c").toItemStack()));
        }

        inventoryContents   .set(SlotPos.of(5, 4), ClickableItem.of(new ItemBuilder(Material.ARROW)
                .setName("§6Retour").toItemStack(), event -> {
            MainGui.INVENTORY.open(player);
        }));

        inventoryContents.newIterator("servers", SlotIterator.Type.HORIZONTAL, 2, 1);

        SlotIterator iterator = inventoryContents.iterator("servers").get();

        if(iterator.column() >= 7) {
            if(iterator.row() >= 3)
                return;
            iterator.column(0);
        }

        for(int i = 0; i < Common.getCommon().getServerManager().getServers().size(); i++) {
            IdaServer server = Common.getCommon().getServerManager().getServers().get(i);
            if(server.getHost().equals(Bukkit.getPlayer(player.getUniqueId()).getName())) {
                iterator.next();
            } else {
                return;
            }
            inventoryContents.set(iterator.row(), iterator.column(), ClickableItem.of(new ItemBuilder(Material.BOOK).setName("§6uhc-" + server.getPort()).toItemStack(), event -> {
                CreateServer.getInstance().send(player, "uhc-" + server.getHost());
            }));
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }

    public IdaPlayer getIdaPlayer() {
        return idaPlayer;
    }
}
