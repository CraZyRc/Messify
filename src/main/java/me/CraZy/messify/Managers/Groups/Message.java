package me.CraZy.messify.Managers.Groups;

import me.CraZy.messify.Utils.ChatExpansion;

public class Message {
  private int ID ;
  private String Message;
  private boolean isJson;

  public Message(int id, String message) {
    this.ID = id;
    this.Message = message;
    this.isJson = ChatExpansion.isJSONValid(message);

  }

  public int getID() { return this.ID; }

  public String getMessage() { return this.Message; }

  public boolean getIsJson() { return this.isJson; }

  public int getLength() { return Message.length(); }

  public void setID(int id) {
    this.ID = id;
  }

  public void setMessage(String message) {
    this.Message = message;
    this.isJson = ChatExpansion.isJSONValid(message);
  }


  public String substring(int i, int i1) {
    return this.Message.substring(i, i1);
  }

  public String replace(String lineBreak, String s) {
    return this.Message.replace(lineBreak, s);
  }

}
