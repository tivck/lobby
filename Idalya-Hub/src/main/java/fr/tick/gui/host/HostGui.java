package fr.tick.gui.host;

import com.sun.javafx.scene.DirtyBits;
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
import net.idalya.core.common.server.CreateServer;
import net.idalya.core.common.server.IdaServer;
import net.idalya.core.tools.ItemBuilder;
import net.idalya.core.utils.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HostGui implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("hostGui")
            .manager(Hub.getInstance().getInventoryManager())
            .provider(new HostGui())
            .size(6, 9)
            .title("§6Idalya §r- §6Parties")
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

        contents.newIterator("hosts", SlotIterator.Type.HORIZONTAL, SlotPos.of(2, 2));

        SlotIterator iterator = contents.iterator("hosts").get();

        if(iterator.column() >= 6) {
            if(iterator.row() >= 3)
                return;
            iterator.column(0);
        }

        String base;
        for(IdaServer serverHost : Common.getCommon().getServerManager().getServers()) {
            iterator.next();
            base = serverHost.getStatus().getSkullValue();
            contents.set(SlotPos.of(iterator.row(), iterator.column()), ClickableItem.of(new ItemBuilder(SkullCreator.itemWithBase64(SkullCreator.createSkull(), base)).setName("§f» §5uhc-" + serverHost.getPort()).setLore("",
                    "§8| §5HOST §7» " + serverHost.getHost(),
                    "§8| §5STATUS §7» " + serverHost.getStatus().getLore(),
                    "§8| §5TYPE §7» " + serverHost.getServerType().getName(),
                    "§8| §5SLOTS §7» " + serverHost.getPlayers().size() + "/" + serverHost.getSlots()).toItemStack(), event -> {
                CreateServer.getInstance().send(player, "uhc-" + serverHost.getPort());
            }));
        }
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
