package com.franz3.giveaway.bot;

import com.franz3.giveaway.bot.listeners.OnReady;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {
    private static Dotenv config;
    private static JDA jda;
    private static String guildID;
    public static Connection database;

    // getter
    public static JDA getJDA(){
        return jda;
    }
    public static String getGuildID(){
        return guildID;
    }
    public static Dotenv getConfig(){
        return config;
    }
    public static void main(String[] args) throws LoginException, SQLException, ClassNotFoundException {
        config = Dotenv.configure().load();
        String botToken =config.get("TOKEN");
        jda = JDABuilder.createDefault(botToken).build();
        jda.getPresence().setActivity(Activity.competing("Rocket League"));
        guildID = "788844871942144000";
        jda.addEventListener(new OnReady());
        //Class.forName("org.postgresql.Driver");
        //database = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + config.get("USERNAME") + config.get("PASSWORD"));
    }
}
