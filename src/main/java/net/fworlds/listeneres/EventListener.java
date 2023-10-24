package net.fworlds.listeneres;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class EventListener extends ListenerAdapter {
//    Reaction console
    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        User user = event.getUser();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        String channelMention = event.getChannel().getAsMention();
        String jumpLink =  event.getJumpUrl();

        String message = user.getAsTag() + "Reacted to a message with " + emoji + " in the " + channelMention + " channel! ";
        event.getGuild().getDefaultChannel().asStandardGuildMessageChannel().sendMessage(message).queue();
    }

//    Ping - Pong
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.contains("ping")) {
            event.getChannel().sendMessage("pong").queue();
        } else if (message.contains("молодец-ботик")) {
            event.getChannel().sendMessage("Спасибо пупсик я стараюсь").queue();
        } else if (message.contains("Как мы относимся к невермору")) {
            event.getChannel().sendMessage("Ужасно он ебаное животное чтобы он здох!").queue();
        } else if (message.contains("Спокойной ночи.")) {
            event.getChannel().sendMessage("Спокойной ночи сладенький").queue();
        }
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        TextChannel txtChannel = event.getJDA().getTextChannelById("1166018239818629191");

        String user = event.getUser().getName();
        String avatar = event.getUser().getEffectiveAvatarUrl();
        System.out.println(avatar);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("New member", null);
        embed.setDescription("Member name: " + user);

        embed.setColor(Color.YELLOW);

        embed.setFooter("Bot created by Foks_f", event.getGuild().getOwner().getUser().getAvatarUrl());

        txtChannel.sendMessageEmbeds(embed.build()).queue();

        //event.getChannel().sendMessageEmbeds(embed.build()).queue();
        embed.clear();
    }

    @Override
    public void onUserUpdateOnlineStatus(@NotNull UserUpdateOnlineStatusEvent event) {
        List<Member> members = event.getGuild().getMembers();
        int onlineMember = 0;
        for (Member member : members) {
            if (member.getOnlineStatus() == OnlineStatus.ONLINE) {
                onlineMember++;
            }
        }

        //User user = event.getJDA().getUserById(799527579900051456L);
        //Member member = event.getGuild().getMemberById(799527579900051456L);

//        event.getJDA().retrieveCommandById(799527579900051456L).queue(user -> {
//
//        });

        //event.getGuild().retrieveMemberById(799527579900051456L).queue();

        User user = event.getUser();
        String message = "**" + user.getAsTag() + "** update their status! There are now " + onlineMember + " user online in this guild!";
        event.getGuild().getDefaultChannel().asStandardGuildMessageChannel().sendMessage(message).queue();
    }
}
