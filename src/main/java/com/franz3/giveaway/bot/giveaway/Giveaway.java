package com.franz3.giveaway.bot.giveaway;

import java.util.ArrayList;
import java.util.Random;

public class Giveaway {
    public void drawWinner (){

        ArrayList<String> participants = new ArrayList<>();
        participants.add("TestName");
        int amount = participants.size();
        Random random = new Random();
        int randomNumber = random.nextInt(amount +1);
        System.out.println(randomNumber);
    }
}
