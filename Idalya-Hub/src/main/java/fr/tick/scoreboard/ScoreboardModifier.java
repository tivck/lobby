package fr.tick.scoreboard;

import net.idalya.core.common.Common;
import net.idalya.core.common.player.IdaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
 
/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ScoreboardModifier {
    private final Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;
 
    ScoreboardModifier(Player player) {
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "§r» §6Idalya §r«");
 
        reloadData();
        objectiveSign.addReceiver(player);
    }
 
    public void reloadData() {}
 
    public void setLines(String ip) {

        IdaPlayer idaPlayer = Common.getCommon().getPlayerManager().get(player.getUniqueId());

        objectiveSign.setLine(0, "§c");
        objectiveSign.setLine(1, "§f§lINFORMATIONS");
        objectiveSign.setLine(2, "§r• §6Grade§r: " + idaPlayer.getRank().getColor() + idaPlayer.getRank().getName());
        objectiveSign.setLine(3, "§r• §6Dalys§r: §f" + idaPlayer.getDalys());
        objectiveSign.setLine(4, "§r• §6Euros§r: §f" + idaPlayer.getEuros());
        objectiveSign.setLine(5, "§9");
        objectiveSign.setLine(6, "§f§lHOSTS");
        objectiveSign.setLine(7, "§r• §6Hosts§r: §f" + idaPlayer.getHosts());
        objectiveSign.setLine(8, "§r• §6PreWL§r: §f" + idaPlayer.getPrewl());
        objectiveSign.setLine(9, "§d");
        objectiveSign.setLine(10, "§f§lSERVEUR");
        objectiveSign.setLine(11, "§r• §6En ligne§r: §f" + Bukkit.getOnlinePlayers().size() + " §7(" + Bukkit.getOnlinePlayers().size() + ")");
        objectiveSign.setLine(12, "§r• §6Hub§r: §f" + Bukkit.getServerName());
        objectiveSign.setLine(13, "§8");
        objectiveSign.setLine(14, "§r• §6dsc.gg/IdalyaMC");
        objectiveSign.updateLines();

    }
 
    public void onLogout() {
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
}