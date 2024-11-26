## HoopWatcher - NBA Player Comparison Tool

Comparison tool to view players from 2000 to 2024 and find similar players based on advanced statistical data.

### What it does

- Search for players by name and year.
- Display player statistics including points, assists, rebounds, steals, blocks, team, and position.
- Find and display the top 5 most similar players based on advanced statistical data.
- Show player headshots if available.

### Setup

- Ensure you have **JDK 11** or higher installed.
- Download and run the jar release file.
  
### Usage

![](https://github.com/cp3249/hoopwatcher/blob/main/example.gif)

1. **Select a year** from the dropdown menu.
2. **Enter a player name** in the search field.
3. **Click search** to display matching players names from that year.
4. **Select a player** from the list to view their stats and similar players.

### How are similar players found?

1. Similar players are calculated by first constructing a high dimensional vector representation of the selected player using advanced stats as components.
   
2. Then we calculate the cosine distance of that player against all others in a stored database. The top 5 smallest difference in players are stored and diplayed.
   
3. the stats used for comparisons are {avgPoints, avgMinutesPlayed, avgMadeFieldGoals, avgAttemptedFieldGoals, avgMadeThreePointFieldGoals, avgAttemptedThreePointFieldGoals,
avgMadeFreeThrows, avgAttemptedFreeThrows, avgOffensiveRebounds, avgDefensiveRebounds, avgAssists, avgSteals, avgBlocks, avgTurnovers, avgPersonalFouls, trueShootingPercentage,
threePointAttemptRate, freeThrowAttemptRate, offensiveReboundPercentage, defensiveReboundPercentage, totalReboundPercentage, assistPercentage, stealPercentage, blockPercentage,
turnoverPercentage, usagePercentage, offensiveBoxPlusMinus, defensiveBoxPlusMinus, boxPlusMinus, valueOverReplacementPlayer, playerEfficiencyRating, winSharesPer48Minutes,
offensiveWinShares, defensiveWinShares, winShares}

### Resources and data

All images and data used for this project can be found in src/main/resources/com/hoopwatcher.
Feel free to use them for whatever you like!
