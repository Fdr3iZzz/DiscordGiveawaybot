package com.franz3.giveaway.bot.listeners;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SlashCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction (SlashCommandInteractionEvent event) {
        // ShardManager shardManager = getShardManager();
        // Guild guild = jda.getGuildById("guildID");
        String command = event.getName();
        //help
        if(command.equals("help")){
            event.reply("send help").queue();
        //add
        } else if(command.equals("add")){
            event.reply(event.getOption("number").getAsInt() + " Tokens got added to: " + event.getOption("name").getAsMember().getEffectiveName()).queue();
        //remove
        }else if(command.equals("remove")){
            event.reply(event.getOption("number").getAsInt() + " Tokens got removed from: " + event.getOption("name").getAsMember().getEffectiveName()).queue();
        //set
        }else if(command.equals("set")){
            event.reply(event.getOption("name").getAsMember().getEffectiveName() + " Tokens got set to: " + event.getOption("number").getAsInt()).queue();
        //tokens
        }else if(command.equals("tokens")){
            OptionMapping userOption = event.getOption("name");
            if (userOption != null && userOption.getType() == OptionType.USER) {
                event.reply("Tokens of " + event.getOption("name").getAsMember().getEffectiveName() + " shown").queue();
            }else{
                event.reply("Tokens shown").queue();
            }

        } else if (command.equals("role")){
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)){
                //guild.addRoleToMember(event.getOption("name").getAsMember(), guild.getRolesByName("Game Participants", true).stream().findFirst().orElse(null)).queue();
            }else {
                event.reply("You lack perms").queue();
            }

        }
    }
//create commands
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
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

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
