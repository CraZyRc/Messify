package me.CraZy.messify.Utils;

import me.CraZy.messify.Config;
import me.CraZy.messify.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatExpansion {
  private static final int CENTER_PX = 154;


  public static String replacePlaceholders(Player player, String message) {
    if (message != null && !message.isEmpty()) {
      message = message
              .replace("%online_players%", String.valueOf(Bukkit.getOnlinePlayers().size()))
              .replace("%max_players%", String.valueOf(Bukkit.getMaxPlayers()))
              .replace("%player_name%", player.getName())
              .replace("%player_display%", player.getDisplayName())
              .replace("%n", "\n");

      // Replace with preset list in Config file
      for (List<String> placeholder : Config.MessageConfig.PLACEHOLDERS) {
        message = message.replace(placeholder.get(0), placeholder.get(1));
      }
      if (Main.hasPlaceholderAPI) {
        message = PlaceholderAPI.setPlaceholders(player, message);
      }

      return Color(message);
    } else {
      return "";
    }
  }

  public static int getMessageLength(String message){

    int messagePxSize = 0;
    boolean previousCode = false;
    boolean isBold = false;

    for(char c : message.toCharArray()){
      if(c == '§'){
        previousCode = true;
        continue;
      }else if(previousCode){
        previousCode = false;
        if(c == 'l' || c == 'L'){
          isBold = true;
          continue;
        }else isBold = false;
      }else{
        DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
        messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
        messagePxSize++;
      }
    }
    return messagePxSize;
  }

  public static String sendCenteredMessage(String message) {
    return sendCenteredMessage(message, "");
  }

  public static String sendCenteredMessage(String message, String prefix){
//        message =  colorize(message);
    int halvedMessageSize = getMessageLength(message) / 2;
    int toCompensate = CENTER_PX - halvedMessageSize - getMessageLength(prefix);
    int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
    int compensated = 0;
    StringBuilder sb = new StringBuilder();
    while(compensated < toCompensate){
      sb.append(" ");
      compensated += spaceLength;
    }
    return sb.toString() + message;
  }

  public static String messageFormat(Player player, String message, String prefix) {

  boolean has_prefix = !message.contains("%!prefix%");
  message = message.replace("%!prefix%", "");

  StringBuilder outputString = new StringBuilder();

  message = replacePlaceholders(player, message);

  if (message.contains("%c%")) {
    boolean first = true;
    for (String Line : message.split("\n")) {
      if (Line.contains("%c%")) {
        if(first && has_prefix){
          outputString.append(sendCenteredMessage(Line.replace("%c%", ""), prefix));
        }else {
          outputString.append(sendCenteredMessage(Line.replace("%c%", "")));
        }
      } else {
        outputString.append(Line);
      }
      first = false;
      outputString.append("\n");
    }
  } else {
    outputString.append(message);
  }
  return (has_prefix ? prefix : "") + outputString;
  }

  public static String Color(String message) {
    Pattern pattern = Pattern.compile("#[a-fA-f0-9]{6}");
    Matcher matcher = pattern.matcher(message);
    while (matcher.find()) {
      String hexCode = message.substring(matcher.start(), matcher.end());
      String replaceSharp = hexCode.replace('#', 'x');

      char[] ch = replaceSharp.toCharArray();
      StringBuilder builder = new StringBuilder();
      for (char c : ch) {
        builder.append("&").append(c);
      }

      message = message.replace(hexCode, builder.toString());
      matcher = pattern.matcher(message);
    }
    return ChatColor.translateAlternateColorCodes('&', message);
  }

  public static boolean isJSONValid(String s) {
    try {
      new JSONObject(s);
    } catch (JSONException ex) {
      try {
        new JSONArray(s);
      } catch (JSONException ex1) {
        return false;
      }
    }
    return true;
  }
}
