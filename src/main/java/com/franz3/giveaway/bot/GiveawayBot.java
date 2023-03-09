package com.franz3.giveaway.bot;

import com.franz3.giveaway.bot.listeners.SlashCommand;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
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
        Guild guild = shardManager.getGuildById("guildID");
        // create commands
        try{
            guild.updateCommands().addCommands(
                    Commands.slash("help", "get help and learn how to use the bot and what features it has"),
                    Commands.slash("add", "add tokens to a user")
                            .addOption(OptionType.USER, "name", "name of the targeted user", true)
                            .addOption(OptionType.INTEGER, "number", "number of tokens you want to add", true),
                    Commands.slash("remove", "remove tokens from a user")
                            .addOption(OptionType.USER, "name", "name of the targeted user", true)
                            .addOption(OptionType.INTEGER, "number", "number of tokens you want to remove", true),
                    Commands.slash("set", "set tokens of a user")
                            .addOption(OptionType.USER, "name", "name of the targeted user", true)
                            .addOption(OptionType.INTEGER, "number", "number of tokens you want to set", true),
                    Commands.slash("tokens", "displays tokens of user")
                            .addOption(OptionType.USER, "name", "name of the targeted user"),
                    Commands.slash("role", "define who can participate in the giveaways")
                            .addOption(OptionType.USER, "name", "name of the targeted user", true)
            ).queue();
        }catch (NullPointerException exception){
            System.out.println("guild id is wrong");
        }
        // add listener
        shardManager.addEventListener(new SlashCommand(), guildID);
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
