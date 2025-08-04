package me.CraZy.messify.Commands;

public class MessifyCMD extends SubCommand {

  protected String GetName() {
    return "messify";
  }

  @Override
  protected String Permission() { return "messify"; }

  protected String[] Aliases() {
    return new String[] {
            "mess",
            "mfy"
    };
  }

  protected SubCommand[] SubCommands() {
    return new SubCommand[] {
            new RawCMD(),
            new StartCMD(),
            new StopCMD(),
            new ReloadCMD(),
            new GroupCMD()
    };
  }
}
