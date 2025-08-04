package me.CraZy.messify.Managers;

import me.CraZy.messify.Config;
import me.CraZy.messify.Managers.Groups.Group;
import me.CraZy.messify.Managers.Groups.Message;
import me.CraZy.messify.Utils.Logger;
import me.CraZy.messify.Utils.Translator;
import me.CraZy.messify.Utils.YMLFile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class MessageManager {
  private static YMLFile messageFile;

  public static void onLoad(Plugin main) {
    loadFile(main);
  }

  public static boolean loadFile(Plugin main) {

    try {
      messageFile = new YMLFile(Config.AppConfig.storageFolder + "/messages.yml");
    } catch (Exception e) {
      Logger.error(e.getMessage());
      return false;
    }

    try {
      if (messageFile.created) {
        Logger.success(Translator.YML_CREATEFILE.Format(messageFile));
        main.saveResource("messages.yml", true);
      }
    } catch (Exception e) {
      Logger.error(e.getMessage());
      return false;
    }
    return true;
  }

  public static Set<String> getGroupKeys() {
    return messageFile.getMap("Group.").keySet();
  }


  public static Map<String, Object> getSettings(String key) {
    ConfigurationSection section = messageFile.FileConfig.getConfigurationSection("Group." + key);
    if (section == null) return null;
    return section.getValues(false);
  }



  public static void setSettingValue(String group, String key, String value) {
    try {
      messageFile.set("Group." + group + "." + key, value);
      messageFile.save();
    } catch (Exception e ) {
      throw new RuntimeException(e);
    }
  }

  public static void setSettingValue(String group, String key, int value) {
    try {
      messageFile.set("Group." + group + "." + key, value);
      messageFile.save();
    } catch (Exception e ) {
      throw new RuntimeException(e);
    }
  }

  public static void setSettingValue(String group, String key, boolean value) {
    try {
      messageFile.set("Group." + group + "." + key, value);
      messageFile.save();
    } catch (Exception e ) {
      throw new RuntimeException(e);
    }
  }

  public static void addMessage(String group, String message) {
    try {
      messageFile.getList("Group." + group + ".Messages").add(message);
      messageFile.save();
    } catch (Exception e ) {
      throw new RuntimeException(e);
    }

  }

  public static void removeMessage(String group, Message message) {
    var messageList = messageFile.getList("Group." + group + ".Messages");
    try {
      messageList.remove(message.getMessage());
      messageFile.set("Group." + group + ".Messages", messageList);
      messageFile.save();
    } catch (Exception e ) {
      throw new RuntimeException(e);
    }
  }

  public static void addGroup(String key, boolean random) {
    try {
      messageFile.set("Group." + key + ".Random", random);
      messageFile.set("Group." + key + ".Messages", new ArrayList<>());
      messageFile.save();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void removeGroup(Group group) {
    try {
      messageFile.set("Group." + group.getName(), null);
      messageFile.save();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
