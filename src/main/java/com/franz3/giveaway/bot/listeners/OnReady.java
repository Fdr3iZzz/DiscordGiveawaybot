package com.franz3.giveaway.bot.listeners;

import com.franz3.giveaway.bot.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OnReady extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        JDA jda = Main.getJDA();
        Guild guild = jda.getGuildById(Main.getGuildID());
        // add listener
        jda.addEventListener(new SlashCommand());
        jda.addEventListener(new ButtonInteraction());
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
        // register commands
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("help", "get help and learn how to use the bot and what features it has"));
        commandData.add(Commands.slash("add", "add tokens to a user")
                .addOption(OptionType.USER, "name", "name of the targeted user", true)
                .addOption(OptionType.INTEGER, "number", "number of tokens you want to add", true));
        commandData.add(Commands.slash("remove", "remove tokens from a user")
                .addOption(OptionType.USER, "name", "name of the targeted user", true)
                .addOption(OptionType.INTEGER, "number", "number of tokens you want to remove", true));
        commandData.add(Commands.slash("set", "set tokens of a user")
                .addOption(OptionType.USER, "name", "name of the targeted user", true)
                .addOption(OptionType.INTEGER, "number", "number of tokens you want to set", true));
        commandData.add(Commands.slash("tokens", "displays tokens of user")
                .addOption(OptionType.USER, "name", "name of the targeted user"));
        commandData.add(Commands.slash("role", "define who can participate in the giveaways")
                .addOption(OptionType.USER, "name", "name of the targeted user", true));
        commandData.add(Commands.slash("giveaway", "create the giveaway")
                .addOption(OptionType.STRING, "message", "input the text you wish to have for the giveaway-message", true));
        commandData.add(Commands.slash("draw", "ends the giveaway"));
        commandData.add(Commands.slash("redraw", "redraw if winner is wrong"));
        event.getJDA().updateCommands().addCommands(commandData).queue();
    }
}
