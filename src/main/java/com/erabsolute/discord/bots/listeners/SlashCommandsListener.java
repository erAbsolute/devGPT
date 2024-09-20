package com.erabsolute.discord.bots.listeners;

import static com.erabsolute.discord.bots.listeners.actions.commands.Help.help;
import static com.erabsolute.discord.bots.listeners.actions.commands.Info.info;
import static com.erabsolute.discord.bots.listeners.actions.commands.Ping.ping;
import static com.erabsolute.discord.bots.listeners.actions.commands.Qrfy.generateQRCodeImage;
import static com.erabsolute.discord.bots.listeners.actions.commands.Qrfy.qrfy;
import static com.erabsolute.discord.bots.utils.GenericEmbeds.errorEmbed;

import com.erabsolute.discord.bots.utils.Constants;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlashCommandsListener extends ListenerAdapter {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    String command = event.getName();
    User user = event.getUser();
    Guild guild = event.getGuild();

    event.deferReply().setEphemeral(true).queue();

    switch (command) {
      case "help":
        logger.info(Constants.LOG_REQUESTED_COMMAND, user.getName(), command);
        EmbedBuilder helpEmbed = help(user, guild);
        event.getHook().sendMessageEmbeds(helpEmbed.build()).queue();
        break;
      case "ping":
        EmbedBuilder pingEmbed = ping(event.getJDA(), user, guild);
        event.getHook().sendMessageEmbeds(pingEmbed.build()).queue();
        break;
      case "info":
        logger.info(Constants.LOG_REQUESTED_COMMAND, user.getName(), command);
        EmbedBuilder infoEmbed = info(user, guild, event.getOptions().getFirst().getAsString());
        event.getHook().sendMessageEmbeds(infoEmbed.build()).queue();
        break;
      case "qrfy":
        logger.info(Constants.LOG_REQUESTED_COMMAND, user.getName(), command);
        try (ByteArrayOutputStream baos =
            generateQRCodeImage(event.getOptions().getFirst().getAsString()); ) {
          EmbedBuilder qrfyEmbed = qrfy(user, guild);
          event
              .getHook()
              .sendMessageEmbeds(qrfyEmbed.build())
              .addFiles(FileUpload.fromData(baos.toByteArray(), "qr.png"))
              .queue();
          break;
        } catch (Exception e) {
          logger.info("{} crashed the command {}", user.getName(), command);
          event.getHook().sendMessageEmbeds(errorEmbed(user, guild).build()).queue();
          break;
        }
    }
  }

  public void onGuildReady(GuildReadyEvent event) {
    List<CommandData> commandData = new ArrayList<>();
    commandData.add(Commands.slash("help", "Muestra los comandos disponibles."));
    commandData.add(Commands.slash("ping", "Devuelve la latencia del bot."));
    commandData.add(
        Commands.slash("qrfy", "Genera un código QR con el texto que le pases.")
            .addOption(OptionType.STRING, "input", "Texto que queremos pasar a QR"));
    commandData.add(
        Commands.slash("info", "Muestra la información del usuario dado.")
            .addOption(OptionType.STRING, "username", "Usuario que se quiere buscar"));
    event.getGuild().updateCommands().addCommands(commandData).queue();
  }

  private static final Pattern discordInvitePattern =
      Pattern.compile(
          "(?i).*(https?://)?(www\\.)?(discord\\.(gg|io|me|li)|discordapp\\.com/invite)/[a-zA-Z0-9]+",
          Pattern.CASE_INSENSITIVE);

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    String message = event.getMessage().getContentRaw();
    StringBuilder descriptionMessage = new StringBuilder();
    Matcher reportInvite = discordInvitePattern.matcher(message);
    if (reportInvite.matches()) {
      EmbedBuilder report = errorEmbed(event.getAuthor(), event.getGuild());
      report.setDescription(
          descriptionMessage
              .append("El usuario ")
              .append(event.getAuthor().getName())
              .append(" ha posteado una invitación.")
              .toString());
      event.getMessage().delete().queue();
      event.getChannel().sendMessageEmbeds(report.build()).queue();
      return;
    }
  }
}
