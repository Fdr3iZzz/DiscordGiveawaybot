package com.franz3.giveaway.bot.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ButtonInteraction extends ListenerAdapter {
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (event.getComponentId().equals("enterGiveaway")){
            if (hasRole(event.getMember())){
                // EVENT HERE
                event.reply("Success").queue();
            } else {
                event.reply("You lack perms").setEphemeral(true).queue();
            }
        }
    }
    public boolean hasRole(Member member) {
        for (Role role : member.getRoles()){
            if (role.getName().equalsIgnoreCase("Game Participants")){
                return true;
            }
        }
        return false;
    }
}
