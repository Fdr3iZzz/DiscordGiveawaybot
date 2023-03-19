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
import java.sql.Statement;


public class Main {
    private static Dotenv config;
    private static JDA jda;
    private static String guildID;
    private static Connection connection;
    private static Statement statement;

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
    public static Connection getConnection() {
        return connection;
    }
    public static Statement getStatement() {
        return statement;
    }
    public static void main(String[] args) throws LoginException, SQLException, ClassNotFoundException {
        config = Dotenv.configure().load();
        String botToken =config.get("TOKEN");
        jda = JDABuilder.createDefault(botToken).build();
        jda.getPresence().setActivity(Activity.competing("Rocket League"));
        guildID = "788844871942144000";
        jda.addEventListener(new OnReady());
        // set up database
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://130.61.146.48:5432/giveawaybot", config.get("NAME"), config.get("PASSWORD"));
        statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS memberTokens ("
                + "id SERIAL PRIMARY KEY NOT NULL,"
                + "name TEXT NOT NULL,"
                + "tokens INT NOT NULL)");
    }
}
