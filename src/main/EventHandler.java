package main;

import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class EventHandler implements Listener {

    private static final String index = Main.index;

    @org.bukkit.event.EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.joinMessage(Component.text(index + e.getPlayer().getName() + "님이 접속 하셨습니다."));
    }

    @org.bukkit.event.EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.quitMessage(Component.text(index + e.getPlayer().getName() + "님이 퇴장 하셨습니다."));
    }

    @org.bukkit.event.EventHandler
    public void onDeath(PlayerRespawnEvent e) {
        if (CMDHandler.isStarted) e.getPlayer().setGameMode(GameMode.SPECTATOR);
    }

    @org.bukkit.event.EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().isOp()) return;
        if (!CMDHandler.canUseBlock) e.setCancelled(true);
    }

    @org.bukkit.event.EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        if (CMDHandler.isAir && event.getPlayer().getGameMode() != GameMode.SPECTATOR) {
            if (event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.GRASS_BLOCK) {
                event.getPlayer().setHealth(0.0);
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onFall(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && !CMDHandler.isStarted) {
            e.setCancelled(true);
        }
    }
}
