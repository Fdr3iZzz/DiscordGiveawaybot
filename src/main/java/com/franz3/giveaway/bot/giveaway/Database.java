package com.franz3.giveaway.bot.giveaway;


import com.franz3.giveaway.bot.Main;

import java.sql.*;


public class Database {
    static Connection connection = Main.getConnection();
    public static void setData(String name, int tokens) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE memberTokens SET tokens = ? WHERE name = ?");
        statement.setInt(1, tokens);
        statement.setString(2, name);
        statement.executeUpdate();
    }
    public static void incrementData(String name, int addedTokens) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE memberTokens SET tokens = tokens + ? WHERE name = ?");
        statement.setInt(1, addedTokens);
        statement.setString(2, name);
        statement.executeUpdate();
    }
    public static void subtractData(String name, int subtractedTokens) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE memberTokens SET tokens = tokens - ? WHERE name = ?");
        statement.setInt(1, subtractedTokens);
        statement.setString(2, name);
        statement.executeUpdate();
    }
    public static int readData(String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT tokens FROM memberTokens WHERE name = ?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("tokens");
        } else {
            return 0;
        }
    }
}
