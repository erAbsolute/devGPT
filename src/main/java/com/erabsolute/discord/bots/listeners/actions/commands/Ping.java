package com.erabsolute.discord.bots.listeners.actions.commands;

import static com.erabsolute.discord.bots.utils.GenericEmbeds.getDefaultEmbed;

import java.awt.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public class Ping {

  public static EmbedBuilder ping(JDA jda, User user, Guild guild) {
    EmbedBuilder embed = getDefaultEmbed(user, guild);
    long ping = jda.getGatewayPing();
    if (ping <= 30) {
      embed.setColor(Color.green);
    } else if (ping <= 150) {
      embed.setColor(Color.yellow);
    } else {
      embed.setColor(Color.red);
    }
    embed.setDescription("La latencia es de " + ping + " ms.");
    return embed;
  }
}
