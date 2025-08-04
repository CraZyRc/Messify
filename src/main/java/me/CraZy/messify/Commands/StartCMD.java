package me.CraZy.messify.Commands;

import dev.jorel.commandapi.executors.CommandExecutor;
import me.CraZy.messify.Config;
import me.CraZy.messify.Managers.Broadcast.BroadcastManager;
import me.CraZy.messify.Utils.Translator;
import org.jetbrains.annotations.Nullable;

public class StartCMD extends SubCommand {
  BroadcastManager Broadcaster = BroadcastManager.getInstance();
  @Override
  protected String GetName() {
    return Translator.COMMANDS_START.Format();
  }

  @Override
  protected @Nullable String Permission() {
    return "messify.start";
  }

  @Override
  protected @Nullable CommandExecutor Executes() {
    return ((sender, args) -> {
      Broadcaster.Start();
      if (!Broadcaster.getPaused()) {
        sender.sendMessage(Config.AppConfig.PREFIX + Translator.COMMANDS_START_SUCCESS.Format());
      } else {
        sender.sendMessage(Config.AppConfig.PREFIX + Translator.COMMANDS_STARTSTOP_ERROR.Format());
      }
    });
  }
}
