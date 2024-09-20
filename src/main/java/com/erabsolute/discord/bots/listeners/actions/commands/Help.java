package com.erabsolute.discord.bots.listeners.actions.commands;

import static com.erabsolute.discord.bots.utils.GenericEmbeds.getDefaultEmbed;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public class Help {

  public static EmbedBuilder help(User user, Guild guild) {
    EmbedBuilder embed = getDefaultEmbed(user, guild);
    String des =
        "Comandos disponibles:\n\n"
            + "> help: Muestra este panel de ayuda\n"
            + "> ping: Te devuelve el ping del bot\n"
            + "> reiniciar: Reinicia el bot, hace falta tener el rol <@&980096058820534273>\n"
            + "> mostrarinfo: Muestra datos del usuario seleccionado\n";
    embed.setDescription(des);
    return embed;
  }
}
