package me.CraZy.messify.Managers.Broadcast;

import me.CraZy.messify.Config;
import me.CraZy.messify.Managers.Groups.Group;
import me.CraZy.messify.Managers.Groups.GroupManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
  private List<Group> playerGroups = new ArrayList<>();
  private boolean canCast = false;
  private int groupIndex = -1, messageIndex = -1;
  private Player Player;

  public PlayerManager(Player player) {
    this.Player = player;

    for (Group group : GroupManager.getInstance().Groups) {
      if (group.Validated() && player.hasPermission("messify." + group.getName().toLowerCase())) {
        playerGroups.add(group);
      }
    }

    if (!playerGroups.isEmpty()) this.canCast = true;
  }

  public Player getPlayer() {
    return Player;
  }

  public String getNext() {
    if (playerGroups.isEmpty()) {
      this.canCast = false;
      return "";
    }
    canCast = true;

    if (Config.MessageConfig.MESSAGEORDER.equalsIgnoreCase("alternate")) {
      this.groupIndex++;
      if (groupIndex >= playerGroups.size()) groupIndex = 0;
    } else if (groupIndex == -1) groupIndex = 0;

    Group group = playerGroups.get(this.groupIndex);
    if (group.getMessages().isEmpty()) {
      playerGroups.remove(group);
      return this.getNext();
    }

    if (Config.MessageConfig.MESSAGEORDER.equalsIgnoreCase("linear")) {
      this.messageIndex++;
      if (group.getMessages().size() == this.messageIndex) {
        this.messageIndex = 0;
        this.groupIndex++;
        if (groupIndex >= playerGroups.size()) groupIndex = 0;
      }
    }

    return group.getNext();
  }

  public void updatePlayerGroups() {
    this.playerGroups = new ArrayList<>();
    for (Group group : GroupManager.getInstance().Groups) {
      if (group.Validated() && this.Player.hasPermission("messify." + group.getName().toLowerCase())) {
        this.playerGroups.add(group);
      }
    }
  }

  public List<Group> getPlayerGroups() {
    return playerGroups;
  }

  public boolean getCanCast() {
    return this.canCast;
  }
}
