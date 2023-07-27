package com.eternalcode.discordapp.leveling;

import com.eternalcode.discordapp.experience.Experience;
import com.eternalcode.discordapp.experience.ExperienceChangeEvent;
import com.eternalcode.discordapp.observer.Observer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import panda.utilities.text.Formatter;

import java.awt.*;

public class LevelController implements Observer<ExperienceChangeEvent> {

    private final LevelConfig levelConfig;
    private final LevelService levelService;
    private final JDA jda;

    public LevelController(LevelConfig levelConfig, LevelService levelService, JDA jda) {
        this.levelConfig = levelConfig;
        this.levelService = levelService;
        this.jda = jda;
    }

    @Override
    public void update(ExperienceChangeEvent experienceChangeEvent) {
        Experience experience = experienceChangeEvent.experience();

        int level = (int) Math.round(this.levelConfig.points / experience.getPoints());

        this.levelService.find(experience.getUserId()).thenApply(userLevel -> {
            if(level > userLevel.getLevel()) {
                userLevel.setLevel(level);
                this.levelService.saveLevel(userLevel);
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle(this.levelConfig.message.title);
                embedBuilder.setDescription(
                        new Formatter()
                                .register("{user}", this.jda.getUserById(experience.getUserId()).getAsMention())
                                .register("{level}", String.valueOf(level))
                                .format(this.levelConfig.message.description)
                );
                embedBuilder.setColor(Color.decode(this.levelConfig.message.color));
                embedBuilder.setThumbnail(this.levelConfig.message.thumbnail);
                embedBuilder.setFooter(this.levelConfig.message.footer);
                this.jda.getTextChannelById(this.levelConfig.channel)
                        .sendMessageEmbeds(embedBuilder.build())
                        .queue();

                return userLevel;
            } else {
                return null;
            }
        });
    }

}
