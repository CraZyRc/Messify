package me.CraZy.messify.Commands;

import dev.jorel.commandapi.executors.CommandExecutor;
import me.CraZy.messify.Config;
import me.CraZy.messify.Main;
import me.CraZy.messify.Managers.Broadcast.BroadcastManager;
import me.CraZy.messify.Managers.Groups.GroupManager;
import me.CraZy.messify.Managers.MessageManager;
import me.CraZy.messify.Managers.ResourceManager;
import me.CraZy.messify.Utils.Logger;
import me.CraZy.messify.Utils.Translator;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

public class ReloadCMD extends SubCommand {
  BroadcastManager broadcastManager = BroadcastManager.getInstance();
  GroupManager groupManager = GroupManager.getInstance();
  @Override
  protected String GetName() {
    return Translator.COMMANDS_RELOAD.Format();
  }

  @Override
  protected @Nullable String Permission() {
    return "messify.reload";
  }

  @Override
  protected @Nullable CommandExecutor Executes() {
    var plugin = (Main) Bukkit.getPluginManager().getPlugin("Messify");
    return ((sender, args) -> {
      if (!broadcastManager.getPaused()) broadcastManager.Stop();
      ResourceManager.onReload();
      Translator.onReload();
      if (!Config.configLoad(plugin)) return;

      Logger.onLoad(plugin);
      MessageManager.onLoad(plugin);

      groupManager.loadGroups();

      if (Config.languageLoad(plugin)) {
        Logger.info(Translator.COMMANDS_RELOAD_KNOWNBUG.Format());
        var commandRoot = new MessifyCMD();
        commandRoot.UnRegister();
        commandRoot.Register();
      }


      broadcastManager.updatePlayerGroups();

      if (Config.MessageConfig.ONSTARTUP && !Bukkit.getOnlinePlayers().isEmpty()) broadcastManager.Start();

      sender.sendMessage(Config.AppConfig.PREFIX + Translator.COMMANDS_RELOAD_SUCCESS.Format());
      Logger.success("Done! V" + plugin.getDescription().getVersion());
    });
  }
}
