package me.CraZy.messify.Commands;

import dev.jorel.commandapi.IStringTooltip;
import dev.jorel.commandapi.StringTooltip;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import me.CraZy.messify.Managers.Groups.Group;
import me.CraZy.messify.Managers.Groups.GroupManager;
import me.CraZy.messify.Managers.Groups.Message;
import me.CraZy.messify.Utils.Translator;

import java.util.ArrayList;
import java.util.List;

public class Arguments {

  private final static GroupManager Manager = GroupManager.getInstance();


  /**
   * Custom Argument for retrieving Groups from the GroupsManager
   * @Argument name: {@code Group}
   * @return Argument<Group>
   */
  public static Argument<Group> Groups() {
    return new CustomArgument<Group, String>(new StringArgument(Translator.ARGUMENTS_GROUP_GROUPS.Format()), info -> {
      for (var group : Manager.Groups) {
        if (group.getName().equals(info.input())) {
          return group;
        }
      }
      throw CustomArgument.CustomArgumentException.fromString(Translator.ARGUMENTS_INVALIDINPUT.Format(info.input()));
    }).replaceSuggestions(ArgumentSuggestions.stringsWithTooltips(Info -> {
      List<IStringTooltip> IDS = new ArrayList<>();

      for (var group : Manager.Groups) {
        IDS.add(StringTooltip.ofString(group.getName(), Translator.ARGUMENTS_TIPS_GROUP.Format()));
      }
      return IDS.toArray(new IStringTooltip[0]);
    }));
  }


  /**
   * Custom Argument for retrieving Messages from the previously input Group
   * @Argument name: {@code Message}
   * @return Argument<Message>
   */
  public static Argument<Message> Messages() {
    return new CustomArgument<Message, String>(new StringArgument(Translator.ARGUMENTS_GROUP_MESSAGES.Format()), info -> {
      var group = info.previousArgs().<Group>getUnchecked(0);
      if (group == null) { return null; }

      for (Message message : group.getMessages()) {
        if (message.getID() == Integer.parseInt(info.input())) {
          return message;
        }
      }
      throw CustomArgument.CustomArgumentException.fromString(Translator.ARGUMENTS_INVALIDINPUT.Format(info.input()));
    }).replaceSuggestions(ArgumentSuggestions.stringsWithTooltips(info -> {
      var group = info.previousArgs().<Group>getUnchecked(0);
      List<IStringTooltip> IDS = new ArrayList<>();

      for (var message : group.getMessages()) {
        IDS.add(StringTooltip.ofString(String.valueOf(message.getID()), message.getMessage()));
      }
      return IDS.toArray(new IStringTooltip[0]);
    }));
  }
}
