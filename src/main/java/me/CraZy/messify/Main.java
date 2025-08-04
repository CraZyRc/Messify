package me.CraZy.messify;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.CraZy.messify.Commands.MessifyCMD;
import me.CraZy.messify.Managers.Broadcast.BroadcastManager;
import me.CraZy.messify.Managers.Groups.GroupManager;
import me.CraZy.messify.Utils.Logger;
import me.CraZy.messify.Managers.MessageManager;
import me.CraZy.messify.Managers.ResourceManager;
import me.CraZy.messify.Utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
  public GroupManager Manager = GroupManager.getInstance();
  private boolean validConfig = false;
  public static boolean hasPlaceholderAPI = false;

  @Override
  public void onLoad() {
    CommandAPI.onLoad(new CommandAPIBukkitConfig(this));

    Logger.onLoad(this);
    this.validConfig = Config.onLoad(this);
    if (!this.validConfig) return;

    ResourceManager.onLoad(); // Translation files
    Translator.onLoad(); // Translation messages
    MessageManager.onLoad(this); // file logic


  }

  @Override
  public void onEnable() {
    if (!validConfig) {
      Logger.error(Translator.MAIN_CONFIGLOADFAIL);
      this.getServer().getPluginManager().disablePlugin(this);
      return;
    }

    CommandAPI.onEnable();
    new MessifyCMD().Register();

    Manager.loadGroups();
    BroadcastManager.getInstance().onEnable(this);
    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      hasPlaceholderAPI = true;
    }

    this.getServer().getPluginManager().registerEvents(Listeners.getInstance(), this);

    //new Updater(this, 0);
    Logger.success("Done! V" + getDescription().getVersion());
  }

  @Override
  public void onDisable() {
    BroadcastManager.getInstance().Stop();
  }
}
