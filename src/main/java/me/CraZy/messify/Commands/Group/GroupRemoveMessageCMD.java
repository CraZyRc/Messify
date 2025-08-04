package me.CraZy.messify.Commands.Group;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.executors.CommandExecutor;
import me.CraZy.messify.Commands.Arguments;
import me.CraZy.messify.Commands.SubCommand;
import me.CraZy.messify.Config;
import me.CraZy.messify.Managers.Groups.Group;
import me.CraZy.messify.Managers.Groups.GroupManager;
import me.CraZy.messify.Managers.Groups.Message;
import me.CraZy.messify.Utils.Translator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;


public class GroupRemoveMessageCMD extends SubCommand {
  static final Hashtable<String, Long> confirmationTable = new Hashtable<>();
  @Override
  protected String GetName() {
    return Translator.COMMANDS_GROUP_REMOVE.Format();
  }

  @Override
  protected @Nullable String Permission() {
    return "messify.group.remove";
  }

  @Override
  protected Argument<?>[] Arguments() {
    return new Argument[]{
            Arguments.Groups(),
            Arguments.Messages()
    };
  }

  @Override
  protected @Nullable CommandExecutor Executes() {
    return ((sender, args) -> {
      var group = args.<Group>getUnchecked(0);
      var message = args.<Message>getUnchecked(1);

        if (sender instanceof Player) {
          Player player = ((Player) sender).getPlayer();
          if (!HasCommandBeenConfirmed(player.getUniqueId().toString())) {
            sender.sendMessage(Config.AppConfig.PREFIX + Translator.COMMANDS_GROUP_REMOVE_CONFIRM.Format(message.getMessage()));
            return;
          }
        }

        sender.sendMessage(Config.AppConfig.PREFIX + Translator.COMMANDS_GROUP_REMOVE_SUCCESS.Format(message.getMessage()));
        group.removeMessage(message);



    });
  }

  /**
   * check whether the player has confirmed the action
   * @param key (Player UUID)
   * @return
   */
  protected boolean HasCommandBeenConfirmed(String key) {
    key = this.GetName() + key;
    if (confirmationTable.containsKey(key) && confirmationTable.get(key) > System.currentTimeMillis()) {
      confirmationTable.remove(key);
      return true;
    } else {
      confirmationTable.put(key, System.currentTimeMillis() + (10 * 1000L)); // 10s
      return false;
    }
  }
}
