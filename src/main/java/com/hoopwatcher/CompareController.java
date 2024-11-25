package com.hoopwatcher;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.hoopwatcher.playercreator.Player;
import com.hoopwatcher.similaritycalculator.SimilarityCalculator;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class CompareController {

    @FXML private ComboBox<Integer> playerYear;
    @FXML private TextField playerName;
    @FXML private ListView<String> playerList;
    @FXML private ListView<GridPane> similarPlayers;
    @FXML private Text playerNameText;
    @FXML private Text yearText;
    @FXML private Text pointsText;
    @FXML private Text assistsText;
    @FXML private Text reboundsText;
    @FXML private Text stealsText;
    @FXML private Text blocksText;
    @FXML private Text teamNameText;
    @FXML private Text positionText;
    @FXML private ImageView PlayerImage = new ImageView();
    String selectedItem;
    public Player playerobject;
    public Player playerobjectnorm;
    public Integer year;
    private static final String dburl = "jdbc:sqlite::resource:" + CompareController.class.getResource("/com/hoopwatcher/db/basketball_player_stats_by_year.db").toString();
    private static final String normdburl = "jdbc:sqlite::resource:" + CompareController.class.getResource("/com/hoopwatcher/db/basketball_player_stats_by_year_normalized.db").toString();
    
    private static Connection connect(String url) throws SQLException {
        return DriverManager.getConnection(url);
    }

    private GridPane createPlayerCard(String name, Integer year, double ppg, double apg, double rpg, double bpg, double spg, String imagePath) {
        GridPane card = new GridPane();
        card.setHgap(3);
        card.setVgap(6);
        card.setPadding(new Insets(1, 0, 1, 0));
        URL imageresource = getClass().getResource(imagePath);
        if (Objects.isNull(imageresource)){
            imageresource = getClass().getResource("defaulticon.png");
        }
        Image playerimageurl = new Image(imageresource.toExternalForm());
        
        ImageView playerImage = new ImageView();
        playerImage.setImage(playerimageurl);

        playerImage.setFitHeight(60);
        playerImage.setFitWidth(45);
        PlayerImage.setPreserveRatio(true);
        PlayerImage.setSmooth(true);
        card.add(playerImage, 0, 0, 1, 2);

        Label nameLabel = new Label(name);
        Label yearLabel = new Label(String.valueOf(year));
        Label empty = new Label("      ");
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        card.add(nameLabel, 1, 0, 2, 1);
        card.add(yearLabel, 3, 0, 2, 1);
        card.add(empty, 0, 0, 2, 1);

        Label ppgLabel = new Label("PPG: " + String.format("%.2f",ppg));
        Label apgLabel = new Label("APG: " + String.format("%.2f",apg));
        Label rpgLabel = new Label("RPG: " + String.format("%.2f",rpg));
        Label bpgLabel = new Label("BPG: " + String.format("%.2f",bpg));
        Label spgLabel = new Label("SPG: " + String.format("%.2f",spg));
        yearLabel.setStyle("-fx-text-fill: white;");
        ppgLabel.setStyle("-fx-text-fill: white;");
        apgLabel.setStyle("-fx-text-fill: white;");
        rpgLabel.setStyle("-fx-text-fill: white;");
        bpgLabel.setStyle("-fx-text-fill: white;");
        spgLabel.setStyle("-fx-text-fill: white;");

        card.add(ppgLabel, 1, 1);
        card.add(apgLabel, 2, 1);
        card.add(rpgLabel, 3, 1);
        card.add(bpgLabel, 4, 1);
        card.add(spgLabel, 5, 1);
        return card;
    }

    @FXML
    private void searchPlayer() {
        year = playerYear.getValue();
        String searchString = playerName.getText();

        if (year != null && !searchString.isEmpty()) {
            playerList.getItems().clear();
            List<String> results = fetchPlayerList(searchString, year);
            playerList.getItems().addAll(results);
        }
    }

    private void findSimilarPlayers() {
        similarPlayers.getItems().clear();
        List<Player> playerListAllYears = new ArrayList<>();
        Map<String, Player> similarPlayerMap = new LinkedHashMap<>();

        try (Connection normConn = connect(normdburl)) {
            for (int currentYear = 2000; currentYear <= 2024; currentYear++) {
                String tableName = "player_stats_" + currentYear;
                String query = "SELECT * FROM " + tableName + " WHERE player_name NOT LIKE ?";
                try (PreparedStatement pstmt = normConn.prepareStatement(query)) {
                    pstmt.setString(1, "%" + selectedItem + "%");
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        Player playerTemp = createPlayerFromResultSet(rs, currentYear);
                        playerTemp.similarity = SimilarityCalculator.calculateSimilarity(playerobjectnorm, playerTemp);
                        playerListAllYears.add(playerTemp);
                    }
                }
            }

            playerListAllYears.sort(Comparator.comparingDouble(player -> -player.similarity));

            for (Player player : playerListAllYears) {
                if (similarPlayerMap.size() < 5 && !similarPlayerMap.containsKey(player.playerName)) {
                    similarPlayerMap.put(player.playerName, player);
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not fetch normalized stats message:" + e.getMessage());
            return;
        }

        try (Connection conn = connect(dburl)) {
            for (Player player : similarPlayerMap.values()) {
                String tableName = "player_stats_" + player.year;
                String query = "SELECT * FROM " + tableName + " WHERE player_name = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, player.playerName);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        Player fullPlayer = createPlayerFromResultSet(rs, player.year);
                        similarPlayers.getItems().add(createPlayerCard(fullPlayer.playerName, fullPlayer.year, fullPlayer.avgPoints, fullPlayer.avgAssists, (fullPlayer.avgDefensiveRebounds + fullPlayer.avgOffensiveRebounds), fullPlayer.avgBlocks, fullPlayer.avgSteals, ("/com/hoopwatcher/headshotsbyname/" + fullPlayer.playerName + ".png")));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Fetching player stats failed error: " + e.getMessage());
        }
    }
    private Player createPlayerFromResultSet(ResultSet rs, int year) throws SQLException {
        return new Player(rs.getString("player_name"), rs.getDouble("avg_points"), rs.getDouble("avg_minutes_played"), rs.getDouble("avg_made_field_goals"), rs.getDouble("avg_attempted_field_goals"), rs.getDouble("avg_made_three_point_field_goals"), rs.getDouble("avg_attempted_three_point_field_goals"), rs.getDouble("avg_made_free_throws"), rs.getDouble("avg_attempted_free_throws"), rs.getDouble("avg_offensive_rebounds"), rs.getDouble("avg_defensive_rebounds"), rs.getDouble("avg_assists"), rs.getDouble("avg_steals"), rs.getDouble("avg_blocks"), rs.getDouble("avg_turnovers"), rs.getDouble("avg_personal_fouls"), rs.getDouble("true_shooting_percentage"), rs.getDouble("three_point_attempt_rate"), rs.getDouble("free_throw_attempt_rate"), rs.getDouble("offensive_rebound_percentage"), rs.getDouble("defensive_rebound_percentage"), rs.getDouble("total_rebound_percentage"), rs.getDouble("assist_percentage"), rs.getDouble("steal_percentage"), rs.getDouble("block_percentage"), rs.getDouble("turnover_percentage"), rs.getDouble("usage_percentage"), rs.getDouble("offensive_box_plus_minus"), rs.getDouble("defensive_box_plus_minus"), rs.getDouble("box_plus_minus"), rs.getDouble("value_over_replacement_player"), rs.getDouble("player_efficiency_rating"), rs.getDouble("win_shares_per_48_minutes"), rs.getDouble("offensive_win_shares"), rs.getDouble("defensive_win_shares"), rs.getDouble("win_shares"), rs.getDouble("magnitude"), rs.getString("team"), rs.getString("position"), year, 0);
    }

    private List<String> fetchPlayerList(String searchString, Integer year) {
        List<String> data = new ArrayList<>();
        String tableName = "player_stats_" + year;
        String query = "SELECT player_name FROM " + tableName + " WHERE player_name LIKE ?";

        try (Connection conn = connect(dburl);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchString + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                data.add(rs.getString("player_name"));
            }
        } catch (SQLException e) {
            System.out.println("Eror collecting player name list:" + e.getMessage());
        }
        return data;
    }

    private void fetchPlayerStats(String searchString, Integer year) {
        String tableName = "player_stats_" + year;
        String query = "SELECT * FROM " + tableName + " WHERE player_name LIKE ?";

        try (Connection conn = connect(dburl);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchString + "%");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                playerobject = createPlayerFromResultSet(rs, year);
                playerNameText.setText(searchString);
                yearText.setText(String.valueOf(year));
                pointsText.setText(String.format("%.2f",playerobject.avgPoints));
                assistsText.setText(String.format("%.2f",playerobject.avgAssists));
                reboundsText.setText(String.format("%.2f", playerobject.avgDefensiveRebounds + playerobject.avgOffensiveRebounds));
                stealsText.setText(String.format("%.2f",playerobject.avgSteals));
                blocksText.setText(String.format("%.2f",playerobject.avgBlocks));
                teamNameText.setText(String.valueOf(playerobject.team));
                positionText.setText(String.valueOf(playerobject.position));
            }
        } catch (SQLException e) {
            System.out.println("Fetching player stats failed error:" + e.getMessage());
        }

        try (Connection conn = connect(normdburl);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchString + "%");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                playerobjectnorm = createPlayerFromResultSet(rs, year);
            }
        } catch (SQLException e) {
            System.out.println("Could not fetch normalized stats message:" + e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        Image defaultimageurl = new Image(getClass().getResource("defaulticon.png").toExternalForm());
        PlayerImage.setImage(defaultimageurl);
        for (Integer year = 2000; year <= 2024; year++) {
            playerYear.getItems().add(year);
        }
        playerList.setOnMouseClicked(event -> {
            selectedItem = playerList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                fetchPlayerStats(selectedItem, year);
                URL selectedplayerimageurl = getClass().getResource(("/com/hoopwatcher/headshotsbyname/" + selectedItem + ".png"));
                if(Objects.nonNull(selectedplayerimageurl)){
                    Image selectedplayerimage = new Image(selectedplayerimageurl.toExternalForm());
                    PlayerImage.setImage(selectedplayerimage);
                }
                else{
                    PlayerImage.setImage(defaultimageurl);
                }
                findSimilarPlayers();
            }
        });
    }

    @FXML
    private void closeApp() throws IOException {
        System.exit(0);
    }

}
