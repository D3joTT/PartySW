package net.d3jott.partysw.skywars;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.api.UltraSkyWarsAPI;
import io.github.Leonardo0013YT.UltraSkyWars.api.events.PlayerGameJoinEvent;
import io.github.Leonardo0013YT.UltraSkyWars.managers.GameManager;
import io.github.Leonardo0013YT.UltraSkyWars.menus.GameMenu;
import net.d3jott.partysw.party.PartyAndFriends;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    private final PartyAndFriends paf = new PartyAndFriends();

    @EventHandler
    public void onArenaJoin(PlayerGameJoinEvent e) {

        final Player pl = e.getPlayer();

        if (paf.hasParty(pl)) {

            if (paf.isLeader(pl)) {

                if (paf.partySize(pl) + e.getGame().getPlayers().size() <= e.getGame().getMax()) {

                    GameManager gameManager = UltraSkyWars.get().getGm();

                    for (String name : paf.getMembers(pl)) {

                        Player player = Bukkit.getPlayer(name);

                        if ((UltraSkyWarsAPI.isPlayerGame(player)) || UltraSkyWarsAPI.isSpectator(player))
                            gameManager.removePlayerAllGame(player);

                        gameManager.addPlayerGame(player, e.getGame().getId());
                    }
                }

                else {

                    e.setCancelled(true);

                    String arenaType = e.getGame().getGameType().toString();

                    GameMenu gameMenu = UltraSkyWars.get().getGem();

                    switch (arenaType) {
                        case "SOLO":
                            gameMenu.createGameNormalMenu(pl, "ignore");
                            break;
                        case "TEAM":
                            gameMenu.createGameTeamMenu(pl, "ignore");
                            break;
                        case "RANKED":
                            gameMenu.createGameRankedMenu(pl, "ignore");
                            break;
                    }
                }
            }
        }
    }
}
