package me.CraZy.messify.Commands.Group;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.executors.CommandExecutor;
import me.CraZy.messify.Commands.Arguments;
import me.CraZy.messify.Commands.SubCommand;
import me.CraZy.messify.Managers.Groups.Group;
import me.CraZy.messify.Managers.Groups.GroupManager;
import me.CraZy.messify.Managers.Groups.Message;
import me.CraZy.messify.Utils.Translator;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nullable;

public class GroupListMessageCMD extends SubCommand {

  @Override
  protected String GetName() {
    return Translator.COMMANDS_GROUP_LIST.Format();
  }

  @Override
  protected @Nullable String Permission() {
    return "messify.group.list";
  }

  @Override
  protected Argument<?>[] Arguments() {
    return new Argument[]{
            Arguments.Groups(),
    };
  }

  @Override
  protected @Nullable CommandExecutor Executes() {
    ChatColor w = ChatColor.WHITE;
    ChatColor y = ChatColor.YELLOW;
    ChatColor a = ChatColor.AQUA;
    String line = y + "\n| ";

    return ((sender, args) -> {
      StringBuilder outputString;
      var group = args.<Group>getUnchecked(0);
      int number = 0;

      outputString = new StringBuilder(y + "\n[>------------------------------------<]\n" + "| " + w + Translator.COMMANDS_GROUP.Format() + ": " + group.getName());

      // If the list isn't empty, add the messages. If it is, send a messages stating there are no messages
      if (!group.getMessages().isEmpty()) {
        for (Message Message : group.getMessages()) {
          outputString.append(line).append(w).append(number).append(". ").append(a);
          if (Message.getLength() > 43) { // Check if the message isn't too long for the screen
            outputString.append(Message.substring(0, 43).replace("\n",""));
          } else {
            outputString.append(Message.replace("\n",""));
          }
          number++;
        }
      } else {
        outputString.append(line).append(w).append(Translator.COMMANDS_GROUP_LIST_STRINGNOTFOUND.Format(Translator.ARGUMENTS_GROUP_MESSAGES.Format()));
      }


      outputString.append(y).append("\n| \n[>------------------------------------<]");
      sender.sendMessage(outputString.toString()); // ? God have mercy on me if this works firsthand.
    });
  }
}
