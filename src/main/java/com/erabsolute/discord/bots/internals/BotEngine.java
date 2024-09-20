package com.erabsolute.discord.bots.internals;

import com.erabsolute.discord.bots.listeners.SlashCommandsListener;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class BotEngine {
  public void startBot() {
    List<GatewayIntent> intents = new ArrayList<>();
    intents.add(GatewayIntent.MESSAGE_CONTENT);
    String token = System.getenv("botToken");
    JDA api =
        JDABuilder.createDefault(token)
            .addEventListeners(new SlashCommandsListener())
            .enableIntents(intents)
            .build();
  }
}
