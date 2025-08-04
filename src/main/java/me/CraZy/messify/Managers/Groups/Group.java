package me.CraZy.messify.Managers.Groups;

import me.CraZy.messify.Managers.MessageManager;

import java.util.*;

public class Group {
  private List<Message> Messages = new ArrayList<>();
  private Integer idCount = 0, Index;
  private String Name;
  private Boolean Random;
  private List<Integer> messageIndex = new ArrayList<>();
  Random random = new Random();


  public Group(String name, boolean random) {
    this.Name = name;
    this.Random = random;
  }

  public Group(String group, Map<String, Object> settings) {
    this.Name = group;
    this.Random = (boolean) settings.get("Random");

    for (var message : (List<String>) settings.get("Messages")) {
      this.Messages.add(new Message(this.idCount, message));
      this.messageIndex.add(this.idCount);
      this.idCount++;
    }

    this.Index = this.Messages.size();
  }

  public List<Message> getMessages(){ return this.Messages; }

  public String getName() { return this.Name; }

  public Boolean getRandom() { return this.Random; }

  public String getNext() {
    if (this.messageIndex.isEmpty()) {
      for (int i = 0; i < this.Messages.size(); i++) {
        this.messageIndex.add(i);
      }
    }

    if (this.Random) {
      int randomPos = this.random.nextInt(messageIndex.size());
      this.Index = this.messageIndex.get(randomPos);
      this.messageIndex.remove(randomPos);
    } else {
      this.Index++;
      if (Index >= this.Messages.size()) {
        Index = 0;
      }
    }
    if (this.Messages.isEmpty()) {
      return "Error, no messages available";
    }
    return this.Messages.get(this.Index).getMessage();

  }

  public void setRandom(boolean random) {
    this.Random = random;
    MessageManager.setSettingValue(this.Name, "Random", random);
  }

  public void addMessage(String message) {
    this.Messages.add(new Message(this.idCount, message));
    MessageManager.addMessage(this.Name, message);
    this.messageIndex.add(this.idCount);
    this.idCount++;
  }

  public void removeMessage(Message message) {
    this.Messages.remove(message);
    this.messageIndex.removeLast();
    MessageManager.removeMessage(this.Name, message);
    this.reassignIDs();
  }

  private void reassignIDs() {
    this.idCount = 0;
    this.messageIndex = new ArrayList<>();
    for (var message : this.Messages) {
      message.setID(idCount);
      this.messageIndex.add(idCount);
      this.idCount++;
    }
  }

  public boolean Validated() {
    return !this.Messages.isEmpty() && this.Random != null;
  }
}
