package com.hoopwatcher.playercreator;

public class Player{
    public String playerName;
    public double avgPoints;
    public double avgMinutesPlayed;
    public double avgMadeFieldGoals;
    public double avgAttemptedFieldGoals;
    public double avgMadeThreePointFieldGoals;
    public double avgAttemptedThreePointFieldGoals;
    public double avgMadeFreeThrows;
    public double avgAttemptedFreeThrows;
    public double avgOffensiveRebounds;
    public double avgDefensiveRebounds;
    public double avgAssists;
    public double avgSteals;
    public double avgBlocks;
    public double avgTurnovers;
    public double avgPersonalFouls;
    public double trueShootingPercentage;
    public double threePointAttemptRate;
    public double freeThrowAttemptRate;
    public double offensiveReboundPercentage;
    public double defensiveReboundPercentage;
    public double totalReboundPercentage;
    public double assistPercentage;
    public double stealPercentage;
    public double blockPercentage;
    public double turnoverPercentage;
    public double usagePercentage;
    public double offensiveBoxPlusMinus;
    public double defensiveBoxPlusMinus;
    public double boxPlusMinus;
    public double valueOverReplacementPlayer;
    public double playerEfficiencyRating;
    public double winSharesPer48Minutes;
    public double offensiveWinShares;
    public double defensiveWinShares;
    public double winShares;
    public double magnitude;
    public String team;
    public String position;
    public Integer year;
    public double similarity = 0;

    public Player(String playerName, double avgPoints, double avgMinutesPlayed, double avgMadeFieldGoals, double avgAttemptedFieldGoals, double avgMadeThreePointFieldGoals, double avgAttemptedThreePointFieldGoals, double avgMadeFreeThrows, double avgAttemptedFreeThrows, double avgOffensiveRebounds, double avgDefensiveRebounds, double avgAssists, double avgSteals, double avgBlocks, double avgTurnovers, double avgPersonalFouls, double trueShootingPercentage, double threePointAttemptRate, double freeThrowAttemptRate, double offensiveReboundPercentage, double defensiveReboundPercentage, double totalReboundPercentage, double assistPercentage, double stealPercentage, double blockPercentage, double turnoverPercentage, double usagePercentage, double offensiveBoxPlusMinus, double defensiveBoxPlusMinus, double boxPlusMinus, double valueOverReplacementPlayer, double playerEfficiencyRating, double winSharesPer48Minutes, double offensiveWinShares, double defensiveWinShares, double winShares, double magnitude, String team, String position, Integer year, double similarity) {
        this.playerName = playerName;
        this.avgPoints = avgPoints;
        this.avgMinutesPlayed = avgMinutesPlayed;
        this.avgMadeFieldGoals = avgMadeFieldGoals;
        this.avgAttemptedFieldGoals = avgAttemptedFieldGoals;
        this.avgMadeThreePointFieldGoals = avgMadeThreePointFieldGoals;
        this.avgAttemptedThreePointFieldGoals = avgAttemptedThreePointFieldGoals;
        this.avgMadeFreeThrows = avgMadeFreeThrows;
        this.avgAttemptedFreeThrows = avgAttemptedFreeThrows;
        this.avgOffensiveRebounds = avgOffensiveRebounds;
        this.avgDefensiveRebounds = avgDefensiveRebounds;
        this.avgAssists = avgAssists;
        this.avgSteals = avgSteals;
        this.avgBlocks = avgBlocks;
        this.avgTurnovers = avgTurnovers;
        this.avgPersonalFouls = avgPersonalFouls;
        this.trueShootingPercentage = trueShootingPercentage;
        this.threePointAttemptRate = threePointAttemptRate;
        this.freeThrowAttemptRate = freeThrowAttemptRate;
        this.offensiveReboundPercentage = offensiveReboundPercentage;
        this.defensiveReboundPercentage = defensiveReboundPercentage;
        this.totalReboundPercentage = totalReboundPercentage;
        this.assistPercentage = assistPercentage;
        this.stealPercentage = stealPercentage;
        this.blockPercentage = blockPercentage;
        this.turnoverPercentage = turnoverPercentage;
        this.usagePercentage = usagePercentage;
        this.offensiveBoxPlusMinus = offensiveBoxPlusMinus;
        this.defensiveBoxPlusMinus = defensiveBoxPlusMinus;
        this.boxPlusMinus = boxPlusMinus;
        this.valueOverReplacementPlayer = valueOverReplacementPlayer;
        this.playerEfficiencyRating = playerEfficiencyRating;
        this.winSharesPer48Minutes = winSharesPer48Minutes;
        this.offensiveWinShares = offensiveWinShares;
        this.defensiveWinShares = defensiveWinShares;
        this.winShares = winShares;
        this.magnitude = magnitude;
        this.team = team;
        this.position = position;
        this.year = year;
        this.similarity = similarity;
    }
    public double[] getPlayerStats() {
        return new double[] {avgPoints, avgMinutesPlayed, avgMadeFieldGoals, avgAttemptedFieldGoals, avgMadeThreePointFieldGoals, avgAttemptedThreePointFieldGoals, avgMadeFreeThrows, avgAttemptedFreeThrows, avgOffensiveRebounds, avgDefensiveRebounds, avgAssists, avgSteals, avgBlocks, avgTurnovers, avgPersonalFouls, trueShootingPercentage, threePointAttemptRate, freeThrowAttemptRate, offensiveReboundPercentage, defensiveReboundPercentage, totalReboundPercentage, assistPercentage, stealPercentage, blockPercentage, turnoverPercentage, usagePercentage, offensiveBoxPlusMinus, defensiveBoxPlusMinus, boxPlusMinus, valueOverReplacementPlayer, playerEfficiencyRating, winSharesPer48Minutes, offensiveWinShares, defensiveWinShares, winShares};
    }
}