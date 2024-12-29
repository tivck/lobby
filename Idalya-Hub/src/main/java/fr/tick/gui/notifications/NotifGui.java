package fr.tick.gui.notifications;

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
import net.idalya.core.common.player.notifications.Notif;
import net.idalya.core.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

public class NotifGui implements InventoryProvider {

    private final IdaPlayer idaPlayer;

    public NotifGui(IdaPlayer idaPlayer) {
        this.idaPlayer = idaPlayer;
    }

    public static SmartInventory getInventory(IdaPlayer idaPlayer) {
        return SmartInventory.builder()
                .id("myNotifs" + Bukkit.getPlayer(idaPlayer.getUuid()).getName())
                .manager(Hub.getInstance().getInventoryManager())
                .provider(new NotifGui(idaPlayer))
                .size(6, 9)
                .title("§6Idalya §r- §6Mes notifications")
                .build();
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        for(SlotPos slotPos : GuiUtils.getGlass()) {
            inventoryContents.set(slotPos, ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .setDurability((short) 1)
                    .setName("§c").toItemStack()));
        }

        inventoryContents.set(SlotPos.of(5, 4), ClickableItem.of(new ItemBuilder(Material.ARROW)
                .setName("§6Retour").toItemStack(), event -> {
            MainGui.INVENTORY.open(player);
        }));

        inventoryContents.newIterator("notifs", SlotIterator.Type.HORIZONTAL, SlotPos.of(1, 1));

        SlotIterator iterator = inventoryContents.iterator("notifs").get();

        if(iterator.column() == 7) {
            iterator.column(1);
            if(iterator.row() >= 4)
                return;
        }

        for(int i = 0; i < idaPlayer.getNotifications().size(); i++) {
            Notif notification = idaPlayer.getNotifications().get(i);
            if(notification.getReceiver().equals(player.getUniqueId())) {
                iterator.next();
            } else {
                return;
            }
            inventoryContents.set(SlotPos.of(iterator.row(), iterator.column()), ClickableItem.of(new ItemBuilder(Material.PAPER)
                    .setName("§6Notif §f: §e§l" + notification.getNotifType().name())
                    .setLore("§c",
                            "§r• §6Reçu de §f: §e" + notification.getSender(),
                            "§r• §6Datant de §f: §e" + new SimpleDateFormat("dd/MMM:yyyy HH:mm:ss").format(notification.getWhen()),
                            "§8",
                            "§r• §6Cliquez pour lire le message").toItemStack(), event -> {
                player.closeInventory();
                Common.getCommon().getNotifManager().del(idaPlayer.getUuid(), notification.getId());
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
