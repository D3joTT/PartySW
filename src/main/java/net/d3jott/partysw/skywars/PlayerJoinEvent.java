package net.d3jott.partysw.skywars;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.api.UltraSkyWarsAPI;
import io.github.Leonardo0013YT.UltraSkyWars.api.events.PlayerGameJoinEvent;
import io.github.Leonardo0013YT.UltraSkyWars.enums.GameType;
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

                    for (String name : paf.getMembers(pl)) {

                        Player player = Bukkit.getPlayer(name);

                        if ((UltraSkyWarsAPI.isPlayerGame(player)) || UltraSkyWarsAPI.isSpectator(player))
                            UltraSkyWars.get().getGm().removePlayerAllGame(player);

                        UltraSkyWars.get().getGm().addPlayerGame(player, e.getGame().getId());
                    }
                }

                else {

                    e.setCancelled(true);

                    GameType arenaType = e.getGame().getGameType();

                    if (arenaType.toString().equals("SOLO"))
                        UltraSkyWars.get().getGem().createGameNormalMenu(pl, "ignore");

                    if (arenaType.toString().equals("TEAM"))
                        UltraSkyWars.get().getGem().createGameTeamMenu(pl, "ignore");

                    if (arenaType.toString().equals("RANKED"))
                        UltraSkyWars.get().getGem().createGameRankedMenu(pl, "ignore");
                }
            }
        }
    }
}
