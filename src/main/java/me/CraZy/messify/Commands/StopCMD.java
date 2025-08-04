package me.CraZy.messify.Commands;

import dev.jorel.commandapi.executors.CommandExecutor;
import me.CraZy.messify.Config;
import me.CraZy.messify.Managers.Broadcast.BroadcastManager;
import me.CraZy.messify.Utils.Translator;
import org.jetbrains.annotations.Nullable;

public class StopCMD extends SubCommand {
  BroadcastManager Broadcaster = BroadcastManager.getInstance();
  @Override
  protected String GetName() {
    return Translator.COMMANDS_STOP.Format();
  }

  @Override
  protected @Nullable String Permission() {
    return "messify.stop";
  }

  @Override
  protected @Nullable CommandExecutor Executes() {
    return ((sender, args) -> {

      Broadcaster.Stop();
      if (Broadcaster.getPaused()) {
        sender.sendMessage(Config.AppConfig.PREFIX + Translator.COMMANDS_STOP_SUCCESS.Format());
      } else {
        sender.sendMessage(Config.AppConfig.PREFIX + Translator.COMMANDS_STARTSTOP_ERROR.Format());
      }
    });
  }
}
