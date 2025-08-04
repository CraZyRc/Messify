package me.CraZy.messify.Managers.Groups;

import me.CraZy.messify.Managers.MessageManager;
import me.CraZy.messify.Utils.Logger;
import me.CraZy.messify.Utils.Translator;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {
  private static GroupManager Instance;
  public List<Group> Groups = new ArrayList<>();

  public static GroupManager getInstance() {
    if (GroupManager.Instance == null) {
      GroupManager.Instance = new GroupManager();
    }
    return GroupManager.Instance;
  }

  public void loadGroups() {
    this.Groups.clear();
    Logger.info(Translator.GROUPMANAGER_LOADING_GROUPS.Format());
    if (MessageManager.getGroupKeys().isEmpty()) {
      Logger.info(Translator.GROUPMANAGER_LOADING_NOGROUPSFOUND.Format());
      return;
    }
    for (var group : MessageManager.getGroupKeys()) {
      if (MessageManager.getSettings(group) != null) {
        var settings = MessageManager.getSettings(group);
        Group managerGroup = new Group(group, settings);
        this.Groups.add(managerGroup);
        Logger.success(Translator.GROUPMANAGER_LOADING_SUCCESS.Format(managerGroup));
      } else Logger.info(Translator.GROUPMANAGER_LOADING_NOMESSAGESFOUND.Format(group));
    }
  }

  public void addGroup(Group group) {
    Groups.add(group);
    MessageManager.addGroup(group.getName(), group.getRandom());
  }

  public void removeGroup(Group group) {
    MessageManager.removeGroup(group);
    Groups.remove(group);
  }

  public Group getGroup(String name) {
    for (var group : this.Groups) {
      if (group.getName().equals(name)) {
        return group;
      }
    }
    return null;
  }
}
