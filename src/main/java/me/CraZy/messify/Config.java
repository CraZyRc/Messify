package me.CraZy.messify;

import me.CraZy.messify.Utils.*;

import java.util.List;
import java.util.Locale;

public class Config {
  private static YMLFile ConfigFile;
  public static boolean Loaded = false;

  public static class AppConfig {
    public static Locale Language = new Locale("en", "US");
    public final static String configFileName = "config.yml", configVersion = "1.0", PREFIX = ChatExpansion.Color("&e&l[&6&lMess&3&lify&e&l] &f>");
    public static String storageFolder = "./plugins/Messify";


    private static boolean LoadConfig(YMLFile configFile) {
      String[] fields = configFile.getString("Language").split("_");
      Language = new Locale(fields[0], fields[1], "");
      return true;
    }
  }

  public static class MessageConfig {
    public static String PREFIX, MESSAGEORDER;
    public static boolean ONSTARTUP;
    public static int INTERVAL;
    public static List<List<String>> PLACEHOLDERS;

    public static boolean LoadConfig(YMLFile configFile) {
      PREFIX                  = ChatExpansion.Color(configFile.getString("Prefix") + " ");
      ONSTARTUP               = configFile.getBoolean("On startup");
      MESSAGEORDER            = configFile.getString("Message order");
      PLACEHOLDERS            = configFile.getList("Placeholders");
      INTERVAL                = configFile.getInt("Interval");
      return true;
    }

  }

  public static boolean onLoad(Main main) {
    try {
      ConfigFile = new YMLFile(AppConfig.storageFolder, AppConfig.configFileName);
    } catch (Exception e) {
      Logger.error(e.getMessage());
      return false;
    }

    if (ConfigFile.created) {
      main.saveResource("config.yml", true);
      Logger.info(Translator.YML_CREATEFILE.Format(ConfigFile));
      try {
        ConfigFile = new YMLFile(AppConfig.storageFolder, AppConfig.configFileName);
      } catch (Exception e) {
        Logger.error(e.getMessage());
        return false;
      }
    }
    return LoadConfig(ConfigFile, main);
  }

  public static boolean languageLoad(Main main) {
    Locale oldLang = AppConfig.Language;
    try {
      ConfigFile = new YMLFile(AppConfig.storageFolder, AppConfig.configFileName);
    } catch (Exception e) {
      Logger.error(e.getMessage());
      return false;
    }

    if (ConfigFile.created) {
      main.saveResource("config.yml", true);
      Logger.info(Translator.YML_CREATEFILE.Format(ConfigFile));
      try {
        ConfigFile = new YMLFile(AppConfig.storageFolder, AppConfig.configFileName);
      } catch (Exception e) {
        Logger.error(e.getMessage());
        return false;
      }
    }
    AppConfig.LoadConfig(ConfigFile);
    return !oldLang.equals(AppConfig.Language);
  }

  public static boolean configLoad(Main main) {
    return LoadConfig(ConfigFile, main);
  }

  protected static boolean LoadConfig(YMLFile ConfigFile, Main main) {
    if (Updater.versionCompare(ConfigFile.getString("Config Version"), AppConfig.configVersion)) {
      Logger.warning(Translator.CONFIG_OLDVERSION.Format(ConfigFile.getString("Config Version")));
    }

    if(!AppConfig.LoadConfig(ConfigFile) || !MessageConfig.LoadConfig(ConfigFile)) {
      return false;
    }

    Loaded = true;
    return true;
  }
}
