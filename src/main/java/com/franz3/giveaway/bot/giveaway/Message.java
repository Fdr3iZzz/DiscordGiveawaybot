package com.franz3.giveaway.bot.giveaway;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;

public class Message {

    public static EmbedBuilder  sendMessage (String textInput, String authorName){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(":tada: Giveaway :tada:")
                .setDescription(textInput)
                .setColor(Color.RED)
                .setAuthor(authorName)
                .setImage("https://cdn2.unrealengine.com/rl-playerschoice-bundle-3840x2160-723248b32611.jpg");
        return builder;
    }
    public static EmbedBuilder  drawWinnerMessage (Member winner){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("And the winner is...")
                .setColor(Color.RED)
                .addField(winner.getAsMention(), ":partying_face: congratulations :partying_face:", false);
        return builder;
    }
}
