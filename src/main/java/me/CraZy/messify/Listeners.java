package me.CraZy.messify;

import me.CraZy.messify.Managers.Broadcast.BroadcastManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {
  private static Listeners Instance;
  BroadcastManager Manager = BroadcastManager.getInstance();

  public static Listeners getInstance() {
    if (Listeners.Instance == null) Listeners.Instance = new Listeners();
    return Listeners.Instance;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Manager.newPlayer(event.getPlayer());
  }
  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    Manager.playerLeave(event.getPlayer());
  }

}
