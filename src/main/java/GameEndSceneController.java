import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class GameEndSceneController {

// Displays the player's final total balance after the round
    @FXML
    private Label finalTotalLabel;

    // Shows total winnings/losses
    @FXML
    private Label roundTotalLabel;

    // Main win or lose message
    @FXML
    private Label gameEndingLabel;

    // Summary text area
    @FXML
    private TextArea summaryArea;

    @FXML
    private Button menuButton;

    @FXML
    private ContextMenu menu;

    // Persistent log of actions from the previous gameplay
    @FXML
    private TextArea logArea;

    // Reference to the main Client application for scene switching
    private ClientMain mainApp;

    // Called by ClientMain to let this controller interact with the app
    public void setMainApp(ClientMain app) {
        this.mainApp = app;
    }

    /*Populates the UI with final game information from PokerInfo.
     * This includes total winnings, round winnings, and win/lose message.
     */
    public void setFinalInfo(PokerInfo info) {
        int total = info.getTotalWinnings();
        int round = info.getRoundWinnings();

        // Decide win/lose message based on outcome text
        if (info.getOutcome().contains("loses") || info.getOutcome().contains("folded")) {
            gameEndingLabel.setText("Sorry, you lost!");
        }
        else {
            gameEndingLabel.setText("Congratulations! You win!");
        }

        // Format total winnings (keep positive sign, show negative when needed)
        if (total < 0) {
            finalTotalLabel.setText("Final Total: -$" + Math.abs(total));
        }
        else {
            finalTotalLabel.setText("Final Total: $" + Math.abs(total));
        }

        // Format round winnings
        if (round < 0) {
            roundTotalLabel.setText("Round Total: -$" + Math.abs(round));
        }
        else {
            roundTotalLabel.setText("Round Total: $" + Math.abs(round));
        }
    }


    /*Called when the player presses "Play Again".
     * Keeps the previous log, resets the game, and switches to the gameplay scene.
     */
    @FXML
    private void onPlayAgain() throws Exception {
        mainApp.setSavedLog(logArea.getText());  // keep log
        mainApp.showGameScene();
    }


    // Called when the player selects quit. Exits.
    @FXML
    private void onQuit() {

        Platform.exit();

    }


    //Displays the dropdown menu attached to the menu button.
    @FXML
    private void openMenu() {
        menu.show(menuButton,
                javafx.geometry.Side.BOTTOM,
                0, 0);
    }


    //Sets the gameplay log text in the final results screen.
    public void setLog(String text) {
        if (logArea != null) {
            logArea.setText(text);
        }
    }
}