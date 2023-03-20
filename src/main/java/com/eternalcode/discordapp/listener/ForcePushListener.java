package com.eternalcode.discordapp.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ForcePushListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        event.getMessage().getEmbeds().forEach(embed -> {
            if (embed.getTitle() != null && embed.getTitle().contains("forced-push")) {
                event.getMessage().delete().queue();
            }
        });
    }
}