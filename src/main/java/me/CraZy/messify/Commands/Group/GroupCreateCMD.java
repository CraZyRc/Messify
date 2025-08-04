package me.CraZy.messify.Commands.Group;

import dev.jorel.commandapi.StringTooltip;
import dev.jorel.commandapi.arguments.*;
import dev.jorel.commandapi.executors.CommandExecutor;
import me.CraZy.messify.Commands.SubCommand;
import me.CraZy.messify.Config;
import me.CraZy.messify.Managers.Broadcast.BroadcastManager;
import me.CraZy.messify.Managers.Groups.Group;
import me.CraZy.messify.Managers.Groups.GroupManager;
import me.CraZy.messify.Utils.Translator;
import org.jetbrains.annotations.Nullable;

public class GroupCreateCMD extends SubCommand {
  private final static GroupManager groupManager = GroupManager.getInstance();
  private final static BroadcastManager broadcastManager = BroadcastManager.getInstance();
  @Override
  protected String GetName() {
    return Translator.COMMANDS_GROUP_CREATE.Format();
  }

  @Override
  protected @Nullable String Permission() {
    return "messify.group.create";
  }

  @Override
  protected Argument<?>[] Arguments() {
    return new Argument[]{
            new StringArgument(Translator.COMMANDS_GROUP_CREATE_GROUPNAME.Format()).replaceSuggestions(ArgumentSuggestions.stringsWithTooltips(
                          StringTooltip.ofString("King",                            Translator.COMMANDS_TIPS_GROUP_CREATE_GROUPNAME.Format())
                    ,     StringTooltip.ofString("VIP+++",                          Translator.COMMANDS_TIPS_GROUP_CREATE_GROUPNAME.Format())
                    ,     StringTooltip.ofString("Diamond",                         Translator.COMMANDS_TIPS_GROUP_CREATE_GROUPNAME.Format())
            )),
            new BooleanArgument(Translator.COMMANDS_GROUP_CREATE_RANDOM.Format()).replaceSuggestions(ArgumentSuggestions.stringsWithTooltips(
                          StringTooltip.ofString("true",                            Translator.COMMANDS_TIPS_GROUP_CREATE_RANDOM.Format())
                    ,     StringTooltip.ofString("false",                           Translator.COMMANDS_TIPS_GROUP_CREATE_RANDOM.Format())
            ))
    };
  }

  @Override
  protected @Nullable CommandExecutor Executes() {
    return ((sender, args) -> {
      var groupName = args.<String>getUnchecked(0);
      var groupRandom = args.<Boolean>getUnchecked(1);

      if (groupName == null || groupRandom == null) {
        sender.sendMessage(Config.AppConfig.PREFIX + Translator.COMMANDS_GROUP_CREATE_ERROR.Format());
        return;
      }

      Group group = new Group(groupName, groupRandom);
      groupManager.addGroup(group);
      broadcastManager.updatePlayerGroups();

      sender.sendMessage(Config.AppConfig.PREFIX + Translator.COMMANDS_GROUP_CREATE_SUCCESS.Format(groupName));

    });
  }
}
