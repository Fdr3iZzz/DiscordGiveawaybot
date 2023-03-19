package com.franz3.giveaway.bot.listeners;

import com.franz3.giveaway.bot.Main;
import com.franz3.giveaway.bot.giveaway.Database;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.buttons.Button;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.franz3.giveaway.bot.Main.getConnection;
import static com.franz3.giveaway.bot.Main.getStatement;
import static com.franz3.giveaway.bot.giveaway.Message.sendMessage;

public class SlashCommand extends ListenerAdapter {
    JDA jda = Main.getJDA();
    Guild guild = jda.getGuildById(Main.getGuildID());
    Connection connection = getConnection();
    Statement statement = getStatement();
    @Override
    public void onSlashCommandInteraction (SlashCommandInteractionEvent event) {
        String command = event.getName();

            //help
        if (command.equals("help")) {
            event.reply("send help").queue();
            //add
        } else if (command.equals("add")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                try {
                    String name = event.getOption("name").getAsMember().getEffectiveName();
                    int number = event.getOption("number").getAsInt();
                    Database.incrementData(name, number);
                    event.reply(number + " Tokens added to " + name).setEphemeral(true).queue();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                event.reply("You lack perms").setEphemeral(true).queue();
            }
            //remove
        } else if (command.equals("remove")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                try {
                    String name = event.getOption("name").getAsMember().getEffectiveName();
                    int number = event.getOption("number").getAsInt();
                    Database.subtractData(name, number);
                    event.reply(number + " Tokens removed from " + name).setEphemeral(true).queue();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                event.reply("You lack perms").setEphemeral(true).queue();
            }
            //set
        } else if (command.equals("set")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                try {
                    String name = event.getOption("name").getAsMember().getEffectiveName();
                    int number = event.getOption("number").getAsInt();
                    Database.setData(name, number);
                    event.reply(number + " Tokens set to " + name).setEphemeral(true).queue();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                event.reply("You lack perms").setEphemeral(true).queue();
            }
            //tokens
        } else if (command.equals("tokens")) {
            OptionMapping userOption = event.getOption("name");
            if (userOption != null && userOption.getType() == OptionType.USER) {
                try {
                    String name = event.getOption("name").getAsMember().getEffectiveName();
                    int tokens = Database.readData(name);
                    event.reply(name + " has " + tokens + " tokens").setEphemeral(true).queue();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    String name = event.getMember().getEffectiveName();
                    int tokens = Database.readData(name);
                    event.reply(name + " has " + tokens + " tokens").setEphemeral(true).queue();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            // role
        } else if (command.equals("role")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                event.deferReply().queue();
                String name = event.getOption("name").getAsMember().getEffectiveName();
                guild.addRoleToMember(event.getOption("name").getAsMember(), guild.getRolesByName("Game Participants", true).stream().findFirst().orElse(null)).queue();
                try {

                    PreparedStatement statement = connection.prepareStatement("INSERT INTO memberTokens (name, tokens) VALUES (?, ?)");
                    statement.setString(1, name);
                    statement.setInt(2, 0);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                event.getHook().sendMessage(name + " added to the Game Participants").setEphemeral(true).queue();
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
        } else if (command.equals("draw")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                // EVENT HERE
            } else {
                event.reply("You lack perms").setEphemeral(true).queue();
            }
        } else if (command.equals("redraw")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                // EVENT HERE
            } else {
                event.reply("You lack perms").setEphemeral(true).queue();
            }
        }
    }
}
