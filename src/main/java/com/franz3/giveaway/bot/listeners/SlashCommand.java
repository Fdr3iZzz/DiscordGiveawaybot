package com.franz3.giveaway.bot.listeners;

import com.franz3.giveaway.bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.buttons.Button;


import static com.franz3.giveaway.bot.giveaway.Message.sendMessage;

public class SlashCommand extends ListenerAdapter {
    JDA jda = Main.getJDA();
    Guild guild = jda.getGuildById(Main.getGuildID());
    @Override
    public void onSlashCommandInteraction (SlashCommandInteractionEvent event) {
        String command = event.getName();

            //help
        if (command.equals("help")) {
            event.reply("send help").queue();
            //add
        } else if (command.equals("add")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                // EVENT HERE
            } else {
                event.reply("You lack perms").setEphemeral(true).queue();
            }
            event.reply(event.getOption("number").getAsInt() + " Tokens got added to: " + event.getOption("name").getAsMember().getEffectiveName()).queue();
            //remove
        } else if (command.equals("remove")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                // EVENT HERE
            } else {
                event.reply("You lack perms").setEphemeral(true).queue();
            }
            event.reply(event.getOption("number").getAsInt() + " Tokens got removed from: " + event.getOption("name").getAsMember().getEffectiveName()).queue();
            //set
        } else if (command.equals("set")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                // EVENT HERE
            } else {
                event.reply("You lack perms").setEphemeral(true).queue();
            }
            event.reply(event.getOption("name").getAsMember().getEffectiveName() + " Tokens got set to: " + event.getOption("number").getAsInt()).queue();
            //tokens
        } else if (command.equals("tokens")) {
            OptionMapping userOption = event.getOption("name");
            if (userOption != null && userOption.getType() == OptionType.USER) {
                event.reply("Tokens of " + event.getOption("name").getAsMember().getEffectiveName() + " shown").queue();
            } else {
                event.reply("Tokens shown").queue();
            }
            // role
        } else if (command.equals("role")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                event.deferReply().queue();
                guild.addRoleToMember(event.getOption("name").getAsMember(), guild.getRolesByName("Game Participants", true).stream().findFirst().orElse(null)).queue();
                event.getHook().sendMessage(event.getMember().getEffectiveName() + " added to the Game Participants").setEphemeral(true).queue();
            } else {
                event.reply("You lack perms").queue();
            }
            // giveaway
        } else if (command.equals("giveaway")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                // EVENT HERE
            } else {
                event.reply("You lack perms").setEphemeral(true).queue();
            }
            // send embed message
            event.replyEmbeds(sendMessage(event.getOption("message").getAsString(), event.getMember().getEffectiveName()).build())
                    .addActionRow(Button.success("enterGiveaway", "Enter")).queue();
        } else if (command.equals("end")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                // EVENT HERE
            } else {
                event.reply("You lack perms").setEphemeral(true).queue();
            }
        }
    }
}
