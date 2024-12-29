package fr.tick.listeners;

import fr.tick.Hub;
import fr.tick.gui.MainGui;
import fr.tick.gui.hub.HubGui;
import fr.tick.gui.profil.ProfilGui;
import net.idalya.core.common.Common;
import net.idalya.core.common.player.IdaPlayer;
import net.idalya.core.common.rank.Rank;
import net.idalya.core.tools.ItemBuilder;
import net.idalya.core.utils.SkullCreator;
import net.idalya.core.utils.Tags;
import net.idalya.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Utils.clearPlayer(player);
        player.setGameMode(GameMode.ADVENTURE);
        IdaPlayer iPlayer = Common.getCommon().getPlayerManager().get(player.getUniqueId());
        Common.getCommon().getNotifManager().register(iPlayer);
        assert iPlayer != null;
        player.getInventory().setItem(4, new ItemBuilder(Material.COMPASS).setName("§6Idalya").toItemStack());
        player.getInventory().setItem(0, new ItemBuilder(SkullCreator.itemWithUuid(SkullCreator.createSkull(), player.getUniqueId())).setName("§6Profil").toItemStack());
        player.getInventory().setItem(1, new ItemBuilder(Material.GOLD_NUGGET).setName("§6Boutique").toItemStack());
        player.getInventory().setItem(8, new ItemBuilder(Material.ENDER_PORTAL_FRAME).setName("§6Hubs").toItemStack());

        if(iPlayer.getRank().getPower() > Rank.JOUEUR.getPower()) {
            if(iPlayer.isLobbyAnnonce()) {
                Bukkit.broadcastMessage(iPlayer.getRank().getColor() + iPlayer.getRank().getName() + " " + player.getName() + " " + iPlayer.getAnnounceType().getMessage());
            }
        }

        Hub.getInstance().getScoreboardManager().getScoreboardUtils().onLogin(player);
        String prefix = iPlayer.getRank().getColor() + "§l" + iPlayer.getRank().getPrefix() + iPlayer.getRank().getColor() + " ";
        Tags.setNameTag(player, iPlayer.getRank().getOrder(), prefix, null);
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Hub.getInstance().getScoreboardManager().getScoreboardUtils().onLogout(player);
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        if(itemStack == null || itemStack.getType().equals(Material.AIR))
            return;

        if(!itemStack.hasItemMeta()) {
            return;
        }

        if(itemStack.getItemMeta().getDisplayName().equals("§6Idalya")) {
            MainGui.INVENTORY.open(player);
        } else if(itemStack.getItemMeta().getDisplayName().equals("§6Profil")) {
            ProfilGui.INVENTORY.open(player);
        } else if(itemStack.getItemMeta().getDisplayName().equals("§6Boutique")) {

        } else if(itemStack.getItemMeta().getDisplayName().equals("§6Hubs")) {
            HubGui.INVENTORY.open(player);
        }
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        IdaPlayer idaPlayer = Common.getCommon().getPlayerManager().get(player.getUniqueId());
        if(idaPlayer != null)
            event.setFormat(idaPlayer.getRank().getColor() + idaPlayer.getRank().getName() + " " + player.getName() + " §8» §f" + event.getMessage());
    }

    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    public void onPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void pickup(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void drop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void entitySpawn(EntitySpawnEvent event) {
        event.setCancelled(true);
    }

    public void onInventory(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

}
