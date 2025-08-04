package me.CraZy.messify.Utils;

import me.CraZy.messify.Config;
import me.CraZy.messify.Managers.Groups.Group;
import me.CraZy.messify.Managers.ResourceManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public enum Translator {
      TRANSLATOR                                          ("Translator")
  ,   MAIN_OLDVERSION                                     ("Main.oldVersion")
  ,   MAIN_CONFIGLOADFAIL                                 ("Main.configLoadFail")
  ,   UPDATEFAIL                                          ("Update.updateFail", String.class)
  ,   LOGGER_WARNING                                      ("Logger.Warning", String.class)
  ,   LOGGER_ERROR                                        ("Logger.Error", String.class)
  ,   YML_SAVEDFILE                                       ("YML.savedFile", YMLFile.class)
  ,   YML_DELETEDFILE                                     ("YML.deletedFile", YMLFile.class)
  ,   YML_CREATEFILE                                      ("YML.createFile", YMLFile.class)
  ,   YML_CREATEFAIL                                      ("YML.createFail", YMLFile.class)
  ,   CONFIG_OLDVERSION                                   ("Config.oldVersion", String.class)
  ,   COMMANDS_RAW                                        ("Commands.Raw")
  ,   COMMANDS_GROUP                                      ("Commands.Group")
  ,   COMMANDS_GROUP_ADD                                  ("Commands.Group.Add")
  ,   COMMANDS_GROUP_ADD_MESSAGE                          ("Commands.Group.Add.Message")
  ,   COMMANDS_GROUP_ADD_EXAMPLE1                         ("Commands.Group.Add.Example1")
  ,   COMMANDS_GROUP_ADD_EXAMPLE2                         ("Commands.Group.Add.Example2")
  ,   COMMANDS_GROUP_ADD_EXAMPLE3                         ("Commands.Group.Add.Example3")
  ,   COMMANDS_GROUP_ADD_ERROR                            ("Commands.Group.Add.Error", String.class)
  ,   COMMANDS_GROUP_ADD_SUCCESS                          ("Commands.Group.Add.Success", Group.class)
  ,   COMMANDS_GROUP_CREATE                               ("Commands.Group.Create")
  ,   COMMANDS_GROUP_CREATE_GROUPNAME                     ("Commands.Group.Create.Groupname")
  ,   COMMANDS_GROUP_CREATE_INTERVAL                      ("Commands.Group.Create.Interval")
  ,   COMMANDS_GROUP_CREATE_RANDOM                        ("Commands.Group.Create.Random")
  ,   COMMANDS_GROUP_CREATE_SUCCESS                       ("Commands.Group.Create.Success", String.class)
  ,   COMMANDS_GROUP_CREATE_ERROR                         ("Commands.Group.Create.Error")
  ,   COMMANDS_GROUP_CREATE_ERROR_INTERVAL                ("Commands.Group.Create.Error.Interval")
  ,   COMMANDS_GROUP_LIST                                 ("Commands.Group.List")
  ,   COMMANDS_GROUP_LIST_STRINGNOTFOUND                  ("Commands.Group.List.stringNotFound", String.class)
  ,   COMMANDS_GROUP_REMOVE                               ("Commands.Group.Remove")
  ,   COMMANDS_GROUP_REMOVE_CONFIRM                       ("Commands.Group.Remove.Confirm", String.class)
  ,   COMMANDS_GROUP_REMOVE_SUCCESS                       ("Commands.Group.Remove.Success", String.class)
  ,   COMMANDS_RELOAD                                     ("Commands.Reload")
  ,   COMMANDS_RELOAD_SUCCESS                             ("Commands.Reload.Success")
  ,   COMMANDS_RELOAD_KNOWNBUG                            ("Commands.Reload.knownBug")
  ,   COMMANDS_START                                      ("Commands.Start")
  ,   COMMANDS_START_SUCCESS                              ("Commands.Start.Success")
  ,   COMMANDS_STARTSTOP_ERROR                            ("Commands.Start/Stop.Error")
  ,   COMMANDS_STOP                                       ("Commands.Stop")
  ,   COMMANDS_STOP_SUCCESS                               ("Commands.Stop.Success")

  ,   ARGUMENTS_GROUP_GROUPS                              ("Arguments.Group.Groups")
  ,   ARGUMENTS_GROUP_MESSAGES                            ("Arguments.Group.Messages")
  ,   ARGUMENTS_INVALIDINPUT                              ("Arguments.invalidInput", String.class)

  ,   COMMANDS_TIPS_RAW_TEXTFORMAT                        ("Commands.Tips.Raw.textFormat")
  ,   COMMANDS_TIPS_GROUP_ADD_EXAMPLE                     ("Commands.Tips.Group.Add.Example")
  ,   COMMANDS_TIPS_GROUP_CREATE_GROUPNAME                ("Commands.Tips.Group.Create.Groupname")
  ,   COMMANDS_TIPS_GROUP_CREATE_INTERVAL                 ("Commands.Tips.Group.Create.Interval")
  ,   COMMANDS_TIPS_GROUP_CREATE_RANDOM                   ("Commands.Tips.Group.Create.Random")

  ,   ARGUMENTS_TIPS_GROUP                                ("Arguments.Tips.Group")
  ,   ARGUMENTS_TIPS_MESSAGE                              ("Arguments.Tips.Message")

  ,   GROUPMANAGER_LOADING_GROUPS                         ("GroupManager.Loading.Groups")
  ,   GROUPMANAGER_LOADING_NOGROUPSFOUND                  ("GroupManager.Loading.noGroupsFound")
  ,   GROUPMANAGER_LOADING_NOMESSAGESFOUND                ("GroupManager.Loading.noMessagesFound", Group.class)
  ,   GROUPMANAGER_LOADING_SUCCESS                        ("GroupManager.Loading.Success", Group.class)

  ,   BROADCASTMANAGER_START_SUCCESS                      ("BroadcastManager.Start.Success")
  ,   BROADCASTMANAGER_STOP_SUCCESS                       ("BroadcastManager.Stop.Success")
  ,   BROADCASTMANAGER_UNKNOWNPLAYER                      ("BroadcastManager.unknownPlayer", Player.class)
  ;

  public String key;
  public String value = "";
  public String formattedValue;

  private Translator(String key) {
    this.key = key;
    this.requiredTypes = new Object[0];
    LookupTranslation();
  }

  public Object[] requiredTypes;

  private Translator(String key, Object... requiredTypes) {
    this(key);
    this.requiredTypes = requiredTypes;
  }


  private void LookupTranslation() {
    this.value = ResourceManager.getProperty(this.key);

    if (this.value.isEmpty()) {
      Logger.error(this.key + " has no value, cannot initialize");
    }

  }

  public String Format() {
    return Format(this, new Object[0]);
  }

  public String Format(Object... replacements) {
    return Translator.Format(this, replacements);
  }

  public static String Format(Translator type) {
    return Format(type, new Object[0]);
  }

  public static String Format(Translator type, Object... replacements) {
    assert typesToString(type.requiredTypes).equals(typesToString(replacements))
            : "Translator format failed for " + type.key + ": " + typesToString(type.requiredTypes) + " | " + typesToString(replacements);


    type.formattedValue = type.value;
    // * Config formatting
    type.configFormat();

    List<String> stringReplacements = new ArrayList<String>();
    for (Object replacement : replacements) {
      if (replacement instanceof Group) type.Format((Group) replacement);
      if (replacement instanceof YMLFile) type.Format((YMLFile) replacement);
      if (replacement instanceof Player) type.Format((Player) replacement);
      if (replacement instanceof String) stringReplacements.add((String) replacement);
    }
    for (String item : stringReplacements) {
      type.Format(item);
    }
    return ChatExpansion.Color(type.formattedValue);
  }

  private void Format(Group group) {
    this.formattedValue = this.formattedValue
            .replace("{groupName}", group.getName()
            .replace("{Random)", String.valueOf(group.getRandom()))
            );
  }

  private void Format(Player player) {
    this.formattedValue = this.formattedValue.replace("{Player}", player.getName());
  }

  private void Format(YMLFile file) {
    this.formattedValue = this.formattedValue.replace("{File}", file.file.getName());
  }

  private void Format(String item) {
    this.formattedValue = this.formattedValue.replaceFirst("\\{.*?\\}", Matcher.quoteReplacement(item));
  }

  private void configFormat() {
    this.formattedValue = this.formattedValue
            .replace("{configVersion}", Config.AppConfig.configVersion);
  }

  public String toString() {
    return ChatExpansion.Color(this.value);
  }


  private static String typesToString(Object[] types) {
    List<String> out = new ArrayList<String>();
    for (Object i : types) {
      String name = "";
      if (i == null) name = "null";
      else if (i instanceof Class) name = ((Class<?>) i).getName();
      else name = i.getClass().getName();
      out.add(name.substring(name.lastIndexOf('.') + 1));
    }
    if (types.length == 0) return "";
    else return " '" + String.join("', '", out) + "' ";
  }

  public static void onLoad() {
    for (Translator item : values()) {
      item.LookupTranslation();
    }
  }

  public static void onReload() {
    for (Translator item : values()) {
      item.LookupTranslation();
    }
  }
}
