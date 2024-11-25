package com.hoopwatcher.similaritycalculator;

import com.hoopwatcher.playercreator.Player;

public class SimilarityCalculator {

    public static double calculateSimilarity(Player Player1, Player Player2){
        double dotproduct = 0;
        double[] player1stats = Player1.getPlayerStats();
        double[] player2stats = Player2.getPlayerStats();
        for(int i = 0; i < 35; i++){
            dotproduct += (player1stats[i]*player2stats[i]);
        }
        double radian = dotproduct/(Player1.magnitude*Player2.magnitude);
        return radian;
    }
    public static void main(String[] args) {        
    }
}
