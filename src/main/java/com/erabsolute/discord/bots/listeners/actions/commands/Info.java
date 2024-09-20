package com.erabsolute.discord.bots.listeners.actions.commands;

import static com.erabsolute.discord.bots.utils.GenericEmbeds.getDefaultEmbed;

import java.awt.*;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.commons.lang3.StringUtils;

public class Info {

  public static EmbedBuilder info(User user, Guild guild, String userRequested) {
    EmbedBuilder embed = getDefaultEmbed(user, guild);
    User userToPrint = user;
    embed.setTitle("Info del usuario:");

    if (StringUtils.isNotBlank(userRequested)) {
      List<Member> users = guild.getMembersByEffectiveName(userRequested, true);
      if (users.isEmpty()) {
        embed.setColor(Color.red);
        embed.setDescription("No se ha encontrado al usuario " + userRequested + ".");
      } else {
        userToPrint = users.get(0).getUser();
      }
    }

    embed.setThumbnail(userToPrint.getAvatarUrl());
    embed.setDescription(
        "> Usuario: " + userToPrint.getAsMention() + "\n > ID del usuario " + userToPrint.getId());
    return embed;
  }
}
