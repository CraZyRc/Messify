package me.CraZy.messify.Commands.Group;

import dev.jorel.commandapi.executors.CommandExecutor;
import me.CraZy.messify.Commands.SubCommand;
import me.CraZy.messify.Managers.Groups.GroupManager;
import me.CraZy.messify.Utils.Translator;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nullable;

public class GroupListGroupsCMD extends SubCommand {
  private final static GroupManager Manager = GroupManager.getInstance();

  @Override
  protected String GetName() {
    return Translator.COMMANDS_GROUP_LIST.Format();
  }

  @Override
  protected @Nullable String Permission() {
    return "messify.group.list";
  }

  @Override
  protected @Nullable CommandExecutor Executes() {
    ChatColor w = ChatColor.WHITE;
    ChatColor y = ChatColor.YELLOW;
    ChatColor a = ChatColor.AQUA;
    String line = y + "\n| ";

    return ((sender, args) -> {
      StringBuilder outputString;
      int number = 0;

      // If entered without an argument, send a list of all the groups
      outputString = new StringBuilder(y + "\n[>------------------------------------<]\n" + "| " + w + Translator.ARGUMENTS_GROUP_GROUPS.Format() + ": ");

      // If the list isn't empty, add the groups. If it is, send an error messages stating there are no groups
      if (!Manager.Groups.isEmpty()) {
        for (var Group : Manager.Groups) {
          outputString.append(line).append(w).append(number).append(". ").append(a).append(Group.getName());
          number++;
        }
      } else {
        outputString.append(line).append(w).append(Translator.COMMANDS_GROUP_LIST_STRINGNOTFOUND.Format(Translator.ARGUMENTS_GROUP_GROUPS.Format()));
      }

      outputString.append(y).append("\n| \n[>------------------------------------<]");
      sender.sendMessage(outputString.toString()); // ? God have mercy on me if this works firsthand.
    });
  }
}
