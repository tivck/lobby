package fr.tick.gui.profil;

import fr.tick.Hub;
import fr.tick.gui.MainGui;
import fr.tick.gui.profil.settings.SettingsGui;
import fr.tick.utils.GuiUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import net.idalya.core.common.Common;
import net.idalya.core.common.player.IdaPlayer;
import net.idalya.core.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfilGui implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("profilGui")
            .manager(Hub.getInstance().getInventoryManager())
            .provider(new ProfilGui())
            .size(6,9)
            .title("§6Idalya §r- §6Profil")
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        IdaPlayer idaPlayer = Common.getCommon().getPlayerManager().get(player.getUniqueId());

        for(SlotPos slotPos : GuiUtils.getGlass()) {
            contents.set(slotPos, ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .setDurability((short) 1)
                    .setName("§c").toItemStack()));
        }

        contents.set(SlotPos.of(5, 4), ClickableItem.of(new ItemBuilder(Material.ARROW)
                .setName("§6Quitter le menu").toItemStack(), event -> {
            player.closeInventory();
        }));

        contents.set(SlotPos.of(1, 7), ClickableItem.empty(new ItemBuilder(Material.PRISMARINE_CRYSTALS)
                .setName("§6Liste d'amis").toItemStack()));
        contents.set(SlotPos.of(2, 3), ClickableItem.of(new ItemBuilder(Material.REDSTONE_COMPARATOR)
                .setName("§6Paramètres").toItemStack(), event -> {
            SettingsGui.INVENTORY.open(player);
        }));
        contents.set(SlotPos.of(2, 4), ClickableItem.empty(new ItemBuilder(Material.PAINTING)
                .setName("§6Statistiques").toItemStack()));
        contents.set(SlotPos.of(2, 5), ClickableItem.empty(new ItemBuilder(Material.ARMOR_STAND)
                .setName("§6Données Actuelles").setLore(
                        "§c",
                        "§f» §6Joueur§r: " + player.getName(),
                        "§f» §6Grade§r: " + idaPlayer.getRank().getColor() + idaPlayer.getRank().getName(),
                        "§f» §6Temps Restant§r: §c" + (idaPlayer.getExpirationRank().toInstant().toEpochMilli() < new Date().toInstant().toEpochMilli() ? "Permanent" : new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(idaPlayer.getExpirationRank())),
                        "§f» §6Première connexion: §c" + new SimpleDateFormat("dd/MM/yyyy").format(idaPlayer.getFirstJoin()),
                        "§f",
                        "§f» §6Pré-Whitelists§r: " + idaPlayer.getPrewl(),
                        "§f» §6Tickets d'Hosts§r: " + idaPlayer.getHosts(),
                        "§f",
                        "§f» Nitro Boost: §c✗",
                        "§f» Link: §c✗").toItemStack()));
        contents.set(SlotPos.of(3, 4), ClickableItem.empty(new ItemBuilder(Material.EXPLOSIVE_MINECART)
                .setName("§6Sanctions").toItemStack()));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
