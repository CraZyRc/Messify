package me.CraZy.messify.Managers.Broadcast;

import me.CraZy.messify.Config;
import me.CraZy.messify.Main;
import me.CraZy.messify.Utils.ChatExpansion;
import me.CraZy.messify.Utils.Logger;
import me.CraZy.messify.Utils.Translator;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BroadcastManager {
  private static BroadcastManager Instance;
  List<PlayerManager> broadcastPlayers = new ArrayList<>();
  private Main Plugin;
  private boolean Paused = true;
  private int ID = -1;

  public static BroadcastManager getInstance() {
    if (BroadcastManager.Instance == null) {
      BroadcastManager.Instance = new BroadcastManager();
    }
    return BroadcastManager.Instance;
  }

  Runnable Runner = () -> {
    boolean stillRunning = false;

    if (Bukkit.getOnlinePlayers().isEmpty() || broadcastPlayers.isEmpty()) Stop();

    for (PlayerManager player : broadcastPlayers) {

      if (!stillRunning && player.getCanCast()) {
        stillRunning = true;
      }

      if (player.getPlayerGroups().isEmpty() || !player.getCanCast()) continue;

      String Message = player.getNext();

      if (Message.isEmpty()) continue;

      if (ChatExpansion.isJSONValid(Message)) {
        BaseComponent[] message = ComponentSerializer.parse(Message);
        BaseComponent[] finalMessage = new BaseComponent[message.length + 1];
        finalMessage[0] = new TextComponent(Config.MessageConfig.PREFIX);
        System.arraycopy(message, 0, finalMessage, 1, message.length);

        player.getPlayer().spigot().sendMessage(finalMessage);
      } else {
        StringBuilder sendMessage = new StringBuilder();
        sendMessage.append(ChatExpansion.messageFormat(player.getPlayer(), Message, Config.MessageConfig.PREFIX));
        player.getPlayer().sendMessage(sendMessage.toString());
      }

    }
    if (!stillRunning) {
      Stop();
    }
  };

  public void onEnable(Main plugin) {
    this.Plugin = plugin;
  }

  public boolean getPaused() {
    return this.Paused;
  }

  public void Start() {
    if (this.Paused && this.ID == -1) {
      this.ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.Plugin, this.Runner, Config.MessageConfig.INTERVAL * 20L, Config.MessageConfig.INTERVAL * 20L);
      this.Paused = false;
      Logger.info(Translator.BROADCASTMANAGER_START_SUCCESS.Format());
    }
  }

  public void Stop() {
    if (!this.Paused) {
      Bukkit.getScheduler().cancelTask(ID);
      this.Paused = true;
      this.ID = -1;
      Logger.info(Translator.BROADCASTMANAGER_STOP_SUCCESS.Format());
    }
  }


  public void newPlayer(Player player) {
    if (this.broadcastPlayers.isEmpty() && Config.MessageConfig.ONSTARTUP){
      this.Start();
    }
    this.broadcastPlayers.add(new PlayerManager(player));
  }

  public void playerLeave(Player player) {
    int i = -1;
    for (int j = 0; j < this.broadcastPlayers.size(); j++) {
      if (this.broadcastPlayers.get(j).getPlayer() == player) {
        i = j;
        break;
      }
    }
    if (i != -1) { this.broadcastPlayers.remove(i); }
    else Logger.error(Translator.BROADCASTMANAGER_UNKNOWNPLAYER.Format(player));
    if (this.broadcastPlayers.isEmpty()) {
      this.Stop();
    }
  }

  public void updatePlayerGroups() {
    for (PlayerManager player : this.broadcastPlayers) {
      player.updatePlayerGroups();
    }
  }
}
