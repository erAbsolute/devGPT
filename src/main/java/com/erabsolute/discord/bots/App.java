package com.erabsolute.discord.bots;

import com.erabsolute.discord.bots.internals.BotEngine;

public class App {
  public static void main(String[] args) {
    BotEngine botEngine = new BotEngine();
    botEngine.startBot();
  }
}
