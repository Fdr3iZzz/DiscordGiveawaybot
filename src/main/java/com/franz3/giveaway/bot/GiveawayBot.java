package com.franz3.giveaway.bot;

import com.franz3.giveaway.bot.listeners.SlashCommand;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.RoleAction;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.awt.*;


public class GiveawayBot {
    private final Dotenv config;
    private final ShardManager shardManager;
    private final String guildID;


    public GiveawayBot() throws LoginException{
        config = Dotenv.configure().load();
        String botToken =config.get("TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(botToken);
        builder.setActivity(Activity.competing("Rocket League"));
        shardManager = builder.build();
        guildID = "788844871942144000";
    }
    public void onReady(ReadyEvent event) {
        Guild guild = shardManager.getGuildById(guildID);
        // add listener
        shardManager.addEventListener(new SlashCommand());
        // create role if not existing
        Role role = guild.getRolesByName("Game Participants", true).stream().findFirst().orElse(null);
        if(role == null){
            RoleAction roleAction = guild.createRole();
            roleAction.setName("Game Participants");
            roleAction.setColor(Color.CYAN);
            role.getManager().setHoisted(true).queue();
            Role participantRole = roleAction.complete();
        }
    }
    public ShardManager getShardManager(){
        return shardManager;
    }
    public Dotenv getConfig(){
        return config;
    }
    public static void main(String[] args) {

        try {
            GiveawayBot bot = new GiveawayBot();
        } catch (LoginException e){
            System.out.println("wrong bot token");
        }

    }
}
