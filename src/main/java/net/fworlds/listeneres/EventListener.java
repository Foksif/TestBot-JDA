package net.fworlds.listeneres;


import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EventListener extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        User user = event.getUser();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        String channelMention = event.getChannel().getAsMention();
        String jumpLink =  event.getJumpUrl();

        String message = user.getAsTag() + "Reacted to a message with " + emoji + " in the " + channelMention + " channel! ";
        System.out.println(message);

    }
}
