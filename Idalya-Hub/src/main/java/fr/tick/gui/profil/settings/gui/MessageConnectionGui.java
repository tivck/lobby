package fr.tick.gui.profil.settings.gui;

import fr.tick.Hub;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.idalya.core.common.Common;
import net.idalya.core.common.player.IdaPlayer;
import net.idalya.core.common.player.announce.AnnounceType;
import net.idalya.core.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MessageConnectionGui implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("messageConnection")
            .manager(Hub.getInstance().getInventoryManager())
            .provider(new MessageConnectionGui())
            .size(3,9)
            .title("§6Idalya §r- §6Profil §r- Message de Connection")
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        IdaPlayer idaPlayer = Common.getCommon().getPlayerManager().get(player.getUniqueId());

        for(AnnounceType announceType : AnnounceType.values()) {
            inventoryContents.add(ClickableItem.of(new ItemBuilder(Material.PAPER).setName("§6" + announceType.name() + (idaPlayer.getAnnounceType().equals(announceType) ? " §f(§céquipé§f)" : ""))
                    .setLore("§c",
                            "§f» §eClique-Gauche pour sélectionner cet annonce",
                            "§f» §eClique-Droit pour prévisualiser l'annonce",
                            "§e").toItemStack(), event -> {

                if(idaPlayer.getAnnounceType().equals(announceType)) {
                    return;
                }

                if(event.getClick().isLeftClick()) {
                    idaPlayer.setAnnounceType(announceType);
                    player.sendMessage("§6PREVISUALISATION §f» §eVous avez bien changé votre annonce !");
                } else if(event.getClick().isRightClick()) {
                    player.sendMessage("§6PREVISUALISATION §f» " + announceType.getMessage());
                }
            }));
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
