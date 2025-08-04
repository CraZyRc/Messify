package me.CraZy.messify.Commands;

import me.CraZy.messify.Commands.Group.*;
import me.CraZy.messify.Utils.Translator;

public class GroupCMD extends SubCommand {

  protected String GetName() {
    return Translator.COMMANDS_GROUP.Format();
  }

  @Override
  protected String Permission() { return "messify.group"; }

  protected SubCommand[] SubCommands() {
    return new SubCommand[] {
            new GroupCreateCMD(),
            new GroupRemoveGroupCMD(),
            new GroupRemoveMessageCMD(),
            new GroupAddCMD(),
            new GroupListGroupsCMD(),
            new GroupListMessageCMD()
    };
  }
}
