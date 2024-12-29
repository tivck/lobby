package fr.tick.gui;

import fr.tick.Hub;
import fr.tick.gui.host.CreateHostGui;
import fr.tick.gui.host.HostGui;
import fr.tick.gui.host.MyServersGui;
import fr.tick.gui.notifications.NotifGui;
import fr.tick.utils.GuiUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import net.idalya.core.common.Common;
import net.idalya.core.common.player.IdaPlayer;
import net.idalya.core.common.rank.Rank;
import net.idalya.core.common.server.CreateServer;
import net.idalya.core.common.server.IdaServer;
import net.idalya.core.tools.ItemBuilder;
import net.idalya.core.utils.SkullCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MainGui implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("mainGui")
            .manager(Hub.getInstance().getInventoryManager())
            .provider(new MainGui())
            .size(6, 9)
            .title("§6Idalya §r- §6Principal")
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        IdaPlayer iPlayer = Common.getCommon().getPlayerManager().get(player.getUniqueId());

        for(SlotPos slotPos : GuiUtils.getGlass()) {
            contents.set(slotPos, ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .setDurability((short) 1)
                    .setName("§c").toItemStack()));
        }

        contents.set(SlotPos.of(2, 4), ClickableItem.of(new ItemBuilder(SkullCreator.itemWithBase64(
                SkullCreator.createSkull(),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGFiZDcwM2U1YjhjODhkNGIxZmNmYTk0YTkzNmEwZDZhNGY2YWJhNDQ1Njk2NjNkMzM5MWQ0ODgzMjIzYzUifX19"))
                .setName("§e§lUHC").toItemStack(), event -> {

            HostGui.INVENTORY.open(player);
        }));
        contents.set(SlotPos.of(2, 5), ClickableItem.of(new ItemBuilder(SkullCreator.itemWithBase64(
                SkullCreator.createSkull(),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTI0ZDg5ZDkyZDJhZGFiYmU1OTM4YTY2ZTdkYjdhNjM0OTA1YWM2YTllNjE5YmY4MzRhYzVmNWY5MDczNGY3NSJ9fX0="))
                .setName("§6Mini-Jeux").toItemStack(), event -> {

        }));
        contents.set(SlotPos.of(3, 4), ClickableItem.of(new ItemBuilder(SkullCreator.itemWithBase64(
                SkullCreator.createSkull(),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZiYjlmYjk3YmE4N2NiNzI3Y2QwZmY0NzdmNzY5MzcwYmVhMTljY2JmYWZiNTgxNjI5Y2Q1NjM5ZjJmZWMyYiJ9fX0="))
                .setName("§a§lFreecube").toItemStack(), event -> {
            CreateServer.getInstance().send(player, "freecube");
        }));
        contents.set(SlotPos.of(2, 8), ClickableItem.empty(new ItemBuilder(Material.SLIME_BALL)
                .setName("§6Créer un groupe").toItemStack()));
        contents.set(SlotPos.of(3, 8), ClickableItem.empty(new ItemBuilder(Material.GOLD_NUGGET)
                .setName("§6Demandes en attente").toItemStack()));
        contents.set(SlotPos.of(1, 1), ClickableItem.of(new ItemBuilder(Material.PAPER, iPlayer.getNotifications().size())
                .setName("§6Notifications").toItemStack(), event -> {
            NotifGui.getInventory(iPlayer).open(player);
        }));
        contents.set(SlotPos.of(2, 0), ClickableItem.of(new ItemBuilder(Material.STORAGE_MINECART)
                .setName("§6Créer un serveur").toItemStack(), event -> {
            if(iPlayer.getRank().getPower() < Rank.HOST.getPower()) {
                player.closeInventory();
                player.sendMessage("§6§lHOST §f» §eVous ne pouvez pas créer d'host");
                return;
            } else {
                for(IdaServer servers : Common.getCommon().getServerManager().getServers()) {
                    if(servers.getHost().equals(player.getName())) {
                        player.closeInventory();
                        player.sendMessage("§6§lHOST §f» §eVous avez déjà un host en cours !");
                        return;
                    }
                }
            }

            CreateHostGui.INVENTORY.open(player);
        }));

        contents.set(SlotPos.of(3, 0), ClickableItem.of(new ItemBuilder(Material.PAINTING)
                .setName("§6Mes serveurs").toItemStack(), event -> {
            MyServersGui.getInventory(iPlayer).open(player);
        }));
        contents.set(SlotPos.of(5, 4), ClickableItem.of(new ItemBuilder(Material.ARROW)
                .setName("§6Fermer le menu").toItemStack(), event -> {
            player.closeInventory();
        }));

    }

    @Override
    public void update(Player player, InventoryContents contents) {
    }
}
