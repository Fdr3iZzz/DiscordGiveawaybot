package com.franz3.giveaway.bot.listeners;

import com.franz3.giveaway.bot.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;

public class OnReady extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        JDA jda = Main.getJDA();
        Guild guild = jda.getGuildById(Main.getGuildID());
        // add listener
        jda.addEventListener(new SlashCommand());
        // create role if not existing
        Role gameParticipantsRole = guild.getRolesByName("Game Participants", true).stream().findFirst().orElse(null);
        if (gameParticipantsRole == null) {
            guild.createRole()
                    .setName("Game Participants")
                    .setColor(0xFFA500)
                    .queue(createdRole -> {
                        System.out.println("Created role: " + createdRole.getName());

                    });
        }
    }
}
