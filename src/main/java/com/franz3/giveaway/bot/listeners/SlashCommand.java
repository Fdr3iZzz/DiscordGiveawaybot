package com.franz3.giveaway.bot.listeners;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.sharding.ShardManager;

public class SlashCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction (SlashCommandInteractionEvent event) {
        ShardManager shardManager = getShardManager();
        Guild guild = jda.getGuildById("guildID");
        //help
        if(event.getName().equals("help")){
            event.reply("send help").queue();
        //add
        } else if(event.getName().equals("add")){
            event.reply(event.getOption("number").getAsInt() + " Tokens got added to: " + event.getOption("name").getAsMember().getEffectiveName()).queue();
        //remove
        }else if(event.getName().equals("remove")){
            event.reply(event.getOption("number").getAsInt() + " Tokens got removed from: " + event.getOption("name").getAsMember().getEffectiveName()).queue();
        //set
        }else if(event.getName().equals("set")){
            event.reply(event.getOption("name").getAsMember().getEffectiveName() + " Tokens got set to: " + event.getOption("number").getAsInt()).queue();
        //tokens
        }else if(event.getName().equals("tokens")){
            OptionMapping userOption = event.getOption("name");
            if (userOption != null && userOption.getType() == OptionType.USER) {
                event.reply("Tokens of " + event.getOption("name").getAsMember().getEffectiveName() + " shown").queue();
            }else{
                event.reply("Tokens shown").queue();
            }

        } else if (event.getName().equals("role")){
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)){
                guild.addRoleToMember(event.getOption("name").getAsMember(), guild.getRolesByName("Game Participants", true).stream().findFirst().orElse(null)).queue();
            }else {
                event.reply("You lack perms").queue();
            }

        }
    }

}
