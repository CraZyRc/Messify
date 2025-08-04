package me.CraZy.messify.Commands;

import dev.jorel.commandapi.StringTooltip;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.executors.CommandExecutor;
import me.CraZy.messify.Config;
import me.CraZy.messify.Utils.ChatExpansion;
import me.CraZy.messify.Utils.Translator;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class RawCMD extends SubCommand {
  @Override
  protected String GetName() {
    return Translator.COMMANDS_RAW.Format();
  }

  @Override
  protected @Nullable String Permission() {
    return "messify.raw";
  }

  @Override
  protected Argument<?>[] Arguments() {
    return new Argument[]{
            new GreedyStringArgument(Translator.COMMANDS_GROUP_ADD_MESSAGE.Format()).replaceSuggestions(ArgumentSuggestions.stringsWithTooltips(
                    StringTooltip.ofString( Translator.COMMANDS_GROUP_ADD_EXAMPLE1.Format(), Translator.COMMANDS_TIPS_RAW_TEXTFORMAT.Format()),
                    StringTooltip.ofString( Translator.COMMANDS_GROUP_ADD_EXAMPLE2.Format(), Translator.COMMANDS_TIPS_RAW_TEXTFORMAT.Format()),
                    StringTooltip.ofString( Translator.COMMANDS_GROUP_ADD_EXAMPLE3.Format(), Translator.COMMANDS_TIPS_RAW_TEXTFORMAT.Format())
            ))
    };
  }

  @Override
  protected @Nullable CommandExecutor Executes() {
    return ((sender, args) -> {
      String input = args.<String>getUnchecked(0);
      for (Player player : Bukkit.getOnlinePlayers()) {
        StringBuilder sendMessage = new StringBuilder();
        if (ChatExpansion.isJSONValid(input)) {
          BaseComponent[] message = ComponentSerializer.parse(input);
          BaseComponent[] finalMessage = new BaseComponent[message.length + 1];
          finalMessage[0] = new TextComponent(Config.MessageConfig.PREFIX);
          System.arraycopy(message, 0, finalMessage, 1, message.length);

          player.spigot().sendMessage(finalMessage);


      } else {
          sendMessage.append(ChatExpansion.messageFormat(player, input, Config.MessageConfig.PREFIX));
          player.sendMessage(sendMessage.toString());
        }

      }
    });
  }
}
