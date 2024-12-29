package fr.tick.gui.profil.settings;

import fr.tick.Hub;
import fr.tick.gui.profil.ProfilGui;
import fr.tick.gui.profil.settings.gui.MessageConnectionGui;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import net.idalya.core.common.Common;
import net.idalya.core.common.player.IdaPlayer;
import net.idalya.core.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SettingsGui implements InventoryProvider {

    public List<SlotPos> glass = Arrays.asList(SlotPos.of(0,0), SlotPos.of(0,1), SlotPos.of(1, 0),
            SlotPos.of(3, 0), SlotPos.of(4, 0), SlotPos.of(4, 1),
            SlotPos.of(3, 8), SlotPos.of(4, 7), SlotPos.of(4, 8),
            SlotPos.of(0, 7), SlotPos.of(0, 8), SlotPos.of(1, 8));

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("settingsGui")
            .manager(Hub.getInstance().getInventoryManager())
            .provider(new SettingsGui())
            .size(5,9)
            .title("§6Idalya §r- §6Profil §r- §6Paramètres")
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        IdaPlayer idaPlayer = Common.getCommon().getPlayerManager().get(player.getUniqueId());

        for(SlotPos slotPos : this.glass) {
            inventoryContents.set(slotPos, ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .setName("§c").setDurability((short) 1)
                    .toItemStack()));
        }

        inventoryContents.set(SlotPos.of(4, 4), ClickableItem.of(new ItemBuilder(Material.ARROW)
                .setName("§6Retour").toItemStack(), event -> {
            ProfilGui.INVENTORY.open(player);
        }));

        inventoryContents.set(1, 3, ClickableItem.of(new ItemBuilder(Material.NAME_TAG)
                .setName("§6Annonce connexion au Lobby")
                .setLore("",
                        "§f» " + (idaPlayer.isLobbyAnnonce() ? "§6" : "§7") + "Oui",
                        "§f» " + (!idaPlayer.isLobbyAnnonce() ? "§6" : "§7") + "Non").toItemStack(), event -> {

                idaPlayer.setLobbyAnnonce(!idaPlayer.isLobbyAnnonce());
                INVENTORY.open(player);
        }));

        inventoryContents.set(1, 4, ClickableItem.of(new ItemBuilder(Material.RAW_FISH)
                .setName("§6Demande d'Amis")
                .setLore("",
                        "§f» " + (idaPlayer.isFriendResquest() ? "§6" : "§7") + "Oui",
                        "§f» " + (!idaPlayer.isFriendResquest() ? "§6" : "§7") + "Non").toItemStack(), event -> {

            idaPlayer.setFriendResquest(!idaPlayer.isFriendResquest());
            INVENTORY.open(player);
        }));

        inventoryContents.set(1, 5, ClickableItem.of(new ItemBuilder(Material.INK_SACK)
                .setName("§6Visibilité des joueurs au Lobby")
                .setLore("",
                        "§f» " + (idaPlayer.isPlayerVisibility() ? "§6" : "§7") + "Oui",
                        "§f» " + (!idaPlayer.isPlayerVisibility() ? "§6" : "§7") + "Non").toItemStack(), event -> {

            idaPlayer.setPlayerVisibility(!idaPlayer.isPlayerVisibility());

            for(Player pls : Bukkit.getOnlinePlayers()) {
                if(idaPlayer.isPlayerVisibility()) {
                    player.showPlayer(pls);
                } else {
                    player.hidePlayer(pls);
                }
            }

            INVENTORY.open(player);
        }));

        inventoryContents.set(2, 4, ClickableItem.of(new ItemBuilder(Material.PAPER)
                .setName("§6Messages privés")
                .setLore("",
                        "§f» " + (idaPlayer.isPrivateMessage() ? "§6" : "§7") + "Oui",
                        "§f» " + (!idaPlayer.isPrivateMessage() ? "§6" : "§7") + "Non").toItemStack(), event -> {

            idaPlayer.setPrivateMessage(!idaPlayer.isPrivateMessage());
            INVENTORY.open(player);
        }));

        inventoryContents.set(2, 5, ClickableItem.of(new ItemBuilder(Material.STICK)
                .setName("§6Message connexion au Lobby")
                .setLore("§c",
                        "§f» §6Annonce actuelle §7: §c" + idaPlayer.getAnnounceType().name(),
                        "§4",
                        "§fCliquez-ici pour changer votre annonce").toItemStack(), event -> {

            MessageConnectionGui.INVENTORY.open(player);
        }));

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
