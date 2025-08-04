package me.CraZy.messify.Commands.Group;

import dev.jorel.commandapi.StringTooltip;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.executors.CommandExecutor;
import me.CraZy.messify.Commands.Arguments;
import me.CraZy.messify.Commands.SubCommand;

import me.CraZy.messify.Config;
import me.CraZy.messify.Managers.Groups.Group;
import me.CraZy.messify.Utils.Translator;
import org.jetbrains.annotations.Nullable;

public class GroupAddCMD extends SubCommand {
  @Override
  protected String GetName() {
    return Translator.COMMANDS_GROUP_ADD.Format();
  }

  @Override
  protected @Nullable String Permission() {
    return "messify.group.add";
  }

  @Override
  protected Argument<?>[] Arguments() {
    return new Argument[]{
            Arguments.Groups(),
            new GreedyStringArgument(Translator.COMMANDS_GROUP_ADD_MESSAGE.Format()).replaceSuggestions(ArgumentSuggestions.stringsWithTooltips(
                            StringTooltip.ofString(Translator.COMMANDS_GROUP_ADD_EXAMPLE1.Format(),               Translator.COMMANDS_TIPS_GROUP_ADD_EXAMPLE.Format())
                    ,       StringTooltip.ofString(Translator.COMMANDS_GROUP_ADD_EXAMPLE2.Format(),               Translator.COMMANDS_TIPS_GROUP_ADD_EXAMPLE.Format())
                    ,       StringTooltip.ofString(Translator.COMMANDS_GROUP_ADD_EXAMPLE3.Format(),               Translator.COMMANDS_TIPS_GROUP_ADD_EXAMPLE.Format())
            ))
    };
  }

  @Override
  protected @Nullable CommandExecutor Executes() {
    return ((sender, args) -> {
      var group = args.<Group>getUnchecked(0);
      var message = args.<String>getUnchecked(1);

      if (group == null) {
        sender.sendMessage(Config.AppConfig.PREFIX + Translator.COMMANDS_GROUP_ADD_ERROR.Format(Translator.COMMANDS_GROUP.Format()));
        return;
      } else if (message == null) {
        sender.sendMessage(Config.AppConfig.PREFIX + Translator.COMMANDS_GROUP_ADD_ERROR.Format(Translator.COMMANDS_GROUP_ADD_MESSAGE.Format()));

      }

      group.addMessage(message);
      sender.sendMessage(Config.AppConfig.PREFIX + Translator.COMMANDS_GROUP_ADD_SUCCESS.Format(group));

    });
  }
}
