package net.fworlds.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class CommandManager extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("welcome")) {
            String userTag = event.getUser().getAsTag();
            event.reply("Welcome to the server, **" + userTag + "**!").setEphemeral(true).queue();
        } else if (command.equals("roles")) {
            //run the '/roles' command
            event.deferReply().queue();
            String response = "";

            for (Role role : event.getGuild().getRoles()) {
                 response += role.getAsMention() + "\n";
            }

            event.getHook().sendMessage(response).queue();


        } else if (command.equals("say")) {
            OptionMapping messageOption = event.getOption("message");
            if (messageOption != null) {
                String message = messageOption.getAsString();
                event.getChannel().sendMessage(message).queue();
                event.reply("Yor message was sent!").setEphemeral(true).queue();
            }
        } else if (command.equals("embied")) {

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("This is a tilte", null);
            embed.setDescription("This is a description");
            embed.addField("Phrase 1)", "Stuff", false);
            embed.addField("Phrase 2)", "Stuff", false);

            embed.setColor(Color.RED);

            embed.setFooter("Bot created by Foks_f", event.getGuild().getOwner().getUser().getAvatarUrl());
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            embed.clear();
            
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("welcome", "Get welcome text"));
        commandData.add(Commands.slash("roles", "Display all roles"));
        commandData.add(Commands.slash("embied", "test embied"));

        // Command: /say <message>
        OptionData opttion1 = new OptionData(OptionType.STRING, "message", "The message", true);
        commandData.add(Commands.slash("say", "send message").addOptions(opttion1));


        event.getGuild().updateCommands().addCommands(commandData).queue();

    }
}
