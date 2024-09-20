package com.erabsolute.discord.bots.utils;

import java.awt.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public class GenericEmbeds {

  public static EmbedBuilder getDefaultEmbed(User user, Guild guild) {
    EmbedBuilder eb = new EmbedBuilder();
    eb.setColor(Color.lightGray);
    eb.setThumbnail("https://i.imgur.com/spli5GS.jpeg");
    eb.setAuthor("erAbsolute", "https://github.com/erAbsolute");
    eb.setFooter(
        "Solicitado por: " + user.getName() + " · " + guild.getName(), user.getAvatarUrl());
    return eb;
  }

  public static EmbedBuilder errorEmbed(User user, Guild guild) {
    EmbedBuilder embed = getDefaultEmbed(user, guild);
    embed.setColor(Color.red);
    embed.setDescription("El comando o los parámetros introducidos, no son válidos.");
    return embed;
  }
}
