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

/**
 * Controller for the gameplay scene.
 * Handles UI interaction, betting logic, animations,
 * and communication with the ClientMain networking layer.
 */
public class GamePlaySceneController {

    @FXML
    private StackPane rootStack;

    // CSS themes user can toggle through
    private final String[] themes = {
            "/styles/gamePlaySceneStyle.css",
            "/styles/christmasPlayScene.css",
            "/styles/wickedPlayScene.css"
    };

    private int currentTheme = 0;
    @FXML private Button dealButton;
    @FXML private Button playButton;
    @FXML private Button foldButton;


    @FXML private ImageView dealerCard1;
    @FXML private ImageView dealerCard2;
    @FXML private ImageView dealerCard3;

    @FXML private ImageView playerCard1;
    @FXML private ImageView playerCard2;
    @FXML private ImageView playerCard3;

    private Image cardBack; // Reusable "back of card" image

    @FXML
    private Label totalWinLabel;

    @FXML
    private TextArea logArea;

    private ClientMain mainApp; // Reference to main client app for scene switching + networking

    @FXML
    private Button menuButton;
    private ContextMenu menu; // Used for New Look / Fresh Start / Exit menu

    // Bet circles
    @FXML private Circle anteCircle;
    @FXML private Circle pairPlusCircle;
    @FXML private Circle playCircle;

    // Poker Chip buttons
    @FXML private ImageView chip5Button;
    @FXML private ImageView chipResetButton;

    @FXML private Label anteBetLabel;
    @FXML private Label pairPlusBetLabel;
    @FXML private Label playBetLabel;

    private boolean dealerCardsRevealed = false;
    private boolean playerCardsDealt = false;

    //Represents which bet the user is currently placing.
    private enum BetType { NONE, ANTE, PAIR_PLUS, PLAY }

    private BetType selectedBet = BetType.NONE;

    // Bet values
    private int anteBet = 0;
    private int pairPlusBet = 0;
    private int playBet = 0;

    @FXML
    private TextArea pairPlusPayoutArea;

    private final int MAX_BET = 25;

    /**
     * Called by ClientMain to give the controller access
     * to the networking and scene transition handler.
     */
    public void setMainApp(ClientMain app) {
        this.mainApp = app;
    }

    /**
     * Runs when the FXML is loaded.
     * Sets up default card images, menu options, bet behavior,
     * and initializes UI directions and payout table.
     */
    @FXML
    public void initialize() {

        // Load the card-back image once
        cardBack = new Image(getClass().getResource("/images/back of card.png").toExternalForm());

        // Set all cards to the card-back initially
        dealerCard1.setImage(cardBack);
        dealerCard2.setImage(cardBack);
        dealerCard3.setImage(cardBack);
        playerCard1.setImage(cardBack);
        playerCard2.setImage(cardBack);
        playerCard3.setImage(cardBack);

        menu = new ContextMenu();

        MenuItem freshStart = new MenuItem("Fresh Start");
        freshStart.setOnAction(e -> onFreshStart());

        MenuItem newLook = new MenuItem("New Look");
        newLook.setOnAction(e -> onNewLook());

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> onExit());

        menu.getItems().addAll(freshStart, newLook, exit);

        // You must place Ante first → disable other circles
        pairPlusCircle.setDisable(true);
        playCircle.setDisable(true);

        // Attach bet-circle click handlers
        anteCircle.setOnMouseClicked(e -> selectBet(BetType.ANTE));
        pairPlusCircle.setOnMouseClicked(e -> selectBet(BetType.PAIR_PLUS));
        playCircle.setOnMouseClicked(e -> selectBet(BetType.PLAY));

        // Ante is selected by default
        selectBet(BetType.ANTE);

        // Disable action buttons until dealing phase
        dealButton.setDisable(true);
        playButton.setDisable(true);
        foldButton.setDisable(true);

        // Fill Pair Plus payout info
        pairPlusPayoutArea.setText(
                "PAIR PLUS PAYOUT\n" +
                        "--------------------------\n" +
                        "Straight Flush: 40:1\n" +
                        "Three of a Kind: 30:1\n" +
                        "Straight: 6:1\n" +
                        "Flush: 3:1\n" +
                        "Pair: 1:1\n"
        );
        pairPlusPayoutArea.setEditable(false);

        // Initial instructions in log
        updateLog("This area keeps track of all events handled throughout the game.");
        updateLog("Begin by clicking on the ante bet circle and then the chip to add money to your ante bet.");
        updateLog("You must place an ante bet first in order to place a pair plus bet or deal cards.");
        updateLog("Your starting amount is $1000.\n");
    }

    /**
     * Reinitializes the betting UI with fresh controls
     * but without clearing the game log.
     * Used when a round ends and the player starts over.
     */
    @FXML
    public void reinitialize(PokerInfo info) {

        // Reset card UI to card backs
        cardBack = new Image(getClass().getResource("/images/back of card.png").toExternalForm());
        dealerCard1.setImage(cardBack);
        dealerCard2.setImage(cardBack);
        dealerCard3.setImage(cardBack);
        playerCard1.setImage(cardBack);
        playerCard2.setImage(cardBack);
        playerCard3.setImage(cardBack);

        // Reset bet circle states
        anteCircle.setDisable(false);
        pairPlusCircle.setDisable(true);
        playCircle.setDisable(true);

        // Re-enable chips
        chip5Button.setDisable(false);
        chipResetButton.setDisable(false);

        // Disable action buttons until bets are placed again
        if (anteBet > 0) {
            dealButton.setDisable(false);
        }
        else {
            dealButton.setDisable(true);
        }
        playButton.setDisable(true);
        foldButton.setDisable(true);

        // Re-attach bet-click handlers
        anteCircle.setOnMouseClicked(e -> selectBet(BetType.ANTE));
        pairPlusCircle.setOnMouseClicked(e -> selectBet(BetType.PAIR_PLUS));
        playCircle.setOnMouseClicked(e -> selectBet(BetType.PLAY));

        // Ante is always the starting bet
        selectBet(BetType.ANTE);
    }

    /**
     * Highlights the selected bet circle and updates internal state.
     */
    private void selectBet(BetType bet) {

        // Remove highlight style from all circles
        anteCircle.getStyleClass().remove("bet-circle-selected");
        pairPlusCircle.getStyleClass().remove("bet-circle-selected");
        playCircle.getStyleClass().remove("bet-circle-selected");

        selectedBet = bet;

        // Add selection highlight to the chosen circle
        switch (bet) {
            case ANTE:
                anteCircle.getStyleClass().add("bet-circle-selected");
                break;
            case PAIR_PLUS:
                pairPlusCircle.getStyleClass().add("bet-circle-selected");
                break;
            case PLAY:
                playCircle.getStyleClass().add("bet-circle-selected");
                break;
            default:
                break;
        }
    }

    /*Handles what happens when the $5 chip is clicked,
     * depending on which bet type is currently selected.
     */
    private void handleChipClick() {

        if (selectedBet == BetType.NONE)
            return; // No circle selected → ignore

        switch (selectedBet) {

            // Ante bet logic
            case ANTE:
                if (anteBet < MAX_BET) {
                    anteBet += 5;
                    anteBetLabel.setText("$" + anteBet);
                    playPopAnimation(anteCircle);

                    // Enable pair plus + deal once Ante > 0
                    if (anteBet > 0) {
                        pairPlusCircle.setDisable(false);
                        dealButton.setDisable(false);
                    }
                } else {
                    playShakeAnimation(anteCircle); // Max reached
                }
                break;

            // Pair Plus bet logic
            case PAIR_PLUS:
                if (pairPlusBet < MAX_BET) {
                    pairPlusBet += 5;
                    pairPlusBetLabel.setText("$" + pairPlusBet);
                    playPopAnimation(pairPlusCircle);
                } else {
                    playShakeAnimation(pairPlusCircle);
                }
                break;

            default:
                break;
        }
    }

    //Displays the  menu
    @FXML
    private void openMenu() {
        menu.show(menuButton, javafx.geometry.Side.BOTTOM, 0, 0);
    }

    //Closes the application.
    @FXML
    private void onExit() {
        Platform.exit();
    }

    //Fresh Start resets the game
    @FXML
    private void onFreshStart() {
        logArea.clear();
        initialize();
        updateLog("Winnings reset!\n");
    }

    //Cycles through the available visual themes.
    @FXML
    private void onNewLook() {
        currentTheme = (currentTheme + 1) % 3;
        rootStack.getStylesheets().clear();
        rootStack.getStylesheets().add(getClass().getResource(themes[currentTheme]).toExternalForm());
        updateLog("Player changed themes!\n");
    }

    //Writes a message to the in-game log.
    public void updateLog(String msg) {
        logArea.appendText(msg + "\n");
    }

    //Called when the $5 chip image is clicked.
    @FXML
    private void onChip5() {
        handleChipClick();
    }

    //Resets all bets and disables betting circles/buttons until ante is placed again
    @FXML
    private void onChipReset() {
        anteBet = 0;
        pairPlusBet = 0;
        playBet = 0;

        anteBetLabel.setText("$0");
        pairPlusBetLabel.setText("$0");
        playBetLabel.setText("$0");

        pairPlusCircle.setDisable(true);
        playCircle.setDisable(true);

        dealButton.setDisable(true);
        playButton.setDisable(true);
        foldButton.setDisable(true);
        selectBet(BetType.ANTE);

    }

    @FXML
    private void onDeal() {
        // Set the play bet equal to the ante automatically
        playBet = anteBet;

        PokerInfo request = new PokerInfo("DEAL");
        request.setPlayerHand(null);
        request.setDealerHand(null);
        request.setAnteBet(anteBet);
        request.setPairPlusBet(pairPlusBet);
        request.setPlayBet(playBet);
        request.setPlay(false);
        request.setFold(false);
        request.setRoundWinnings(0);
        request.setTotalWinnings(request.getTotalWinnings());
        request.setCommand("DEAL");
        request.setOutcome(null);
        request.setPlayerHandString(request.getPlayerHandString());
        request.setDealerHandString(request.getDealerHandString());
        request.setDealerQualified(request.getDealerQualified());

        // Send Deal command to server
        mainApp.sendToServer(request);

        updateLog("Player selected deal.");

        // Lock betting and controls until dealer responds

        dealButton.setDisable(true);
        playButton.setDisable(true);
        foldButton.setDisable(true);
        anteCircle.setDisable(true);
        pairPlusCircle.setDisable(true);
        playCircle.setDisable(true);
        chip5Button.setDisable(true);
        chipResetButton.setDisable(true);
    }

    @FXML
    private void onPlay() {
        // Update displayed Play bet amount
        playBetLabel.setText("$" + playBet);

        // Prevent deal/play/fold during server processing
        dealButton.setDisable(true);
        playButton.setDisable(true);
        foldButton.setDisable(true);

        // Build request telling the server the player chose "PLAY"
        PokerInfo request = new PokerInfo("PLAY");
        updateLog("Player selected play.");

        // Attach current round data to the packet
        request.setPlayerHand(request.getPlayerHand());
        request.setDealerHand(request.getDealerHand());
        request.setAnteBet(anteBet);
        request.setPairPlusBet(pairPlusBet);
        request.setPlayBet(playBet);
        request.setPlay(true);
        request.setFold(false);
        request.setRoundWinnings(request.getRoundWinnings());
        request.setTotalWinnings(request.getTotalWinnings());
        request.setCommand("PLAY");
        request.setOutcome(request.getOutcome());
        request.setPlayerHandString(request.getPlayerHandString());
        request.setDealerHandString(request.getDealerHandString());
        request.setDealerQualified(request.getDealerQualified());
        // Send PLAY request to server
        mainApp.sendToServer(request);

        // Lock additional betting inputs after committing to Play
        anteCircle.setDisable(true);
        pairPlusCircle.setDisable(true);
        playCircle.setDisable(true);
        selectBet(BetType.ANTE);
    }

    @FXML
    private void onFold() {
        updateLog("Player selected fold.");

        playBet = 0; // Reset play bet visually
        playBetLabel.setText("$" + playBet);
        PokerInfo request = new PokerInfo("FOLD");
        request.setAnteBet(anteBet);
        request.setPairPlusBet(pairPlusBet);
        request.setPlayBet(0);
        request.setFold(true);
        request.setPlay(false);

        mainApp.sendToServer(request);

        // Disable play-related controls after folding
        dealButton.setDisable(true);
        playButton.setDisable(true);
        foldButton.setDisable(true);
    }

    // Simple scale-in/out "pop" effect for clicks
    private void playPopAnimation(Node node) {
        ScaleTransition st = new ScaleTransition(Duration.millis(120), node);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(1.15);
        st.setToY(1.15);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }

    // Quick left–right shake effect (usually for invalid action)
    private void playShakeAnimation(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(60), node);
        tt.setFromX(0);
        tt.setByX(10);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }

    public void handleDealCards(PokerInfo info) {
        // Start with all cards facedown
        playerCard1.setImage(cardBack);
        playerCard2.setImage(cardBack);
        playerCard3.setImage(cardBack);
        dealerCard1.setImage(cardBack);
        dealerCard2.setImage(cardBack);
        dealerCard3.setImage(cardBack);

        // Extract player cards
        ArrayList<Card> cards = info.getPlayerHand();

        Image c1 = new Image(getClass().getResource("/images/" +
                cards.get(0).toString() + ".png").toExternalForm());
        Image c2 = new Image(getClass().getResource("/images/" +
                cards.get(1).toString() + ".png").toExternalForm());
        Image c3 = new Image(getClass().getResource("/images/" +
                cards.get(2).toString() + ".png").toExternalForm());

        //  timed reveal sequence
        PauseTransition p1 = new PauseTransition(Duration.seconds(0.5));
        p1.setOnFinished(e -> playerCard1.setImage(c1));

        PauseTransition p2 = new PauseTransition(Duration.seconds(0.5));
        p2.setOnFinished(e -> playerCard2.setImage(c2));

        PauseTransition p3 = new PauseTransition(Duration.seconds(0.5));
        p3.setOnFinished(e -> playerCard3.setImage(c3));

        // Once all cards are revealed, enable play/fold
        PauseTransition finish = new PauseTransition(Duration.seconds(0.5));
        finish.setOnFinished(e -> {
            updateLog("Player cards dealt.");
            playButton.setDisable(false);
            foldButton.setDisable(false);
            updateLog(info.getPlayerHandString());
        });

        dealerCardsRevealed = true;
        playerCardsDealt = true;

        SequentialTransition revealSeq = new SequentialTransition(p1, p2, p3, finish);
        revealSeq.play();
    }

    public void handlePlayResult(PokerInfo info) {
        ArrayList<Card> dealer = info.getDealerHand();

        // Convert card names to images
        Image d1 = new Image(getClass().getResource("/images/" +
                dealer.get(0).toString() + ".png").toExternalForm());
        Image d2 = new Image(getClass().getResource("/images/" +
                dealer.get(1).toString() + ".png").toExternalForm());
        Image d3 = new Image(getClass().getResource("/images/" +
                dealer.get(2).toString() + ".png").toExternalForm());

        // Reveal dealer cards one by one
        PauseTransition p1 = new PauseTransition(Duration.seconds(0.5));
        p1.setOnFinished(e -> dealerCard1.setImage(d1));

        PauseTransition p2 = new PauseTransition(Duration.seconds(0.5));
        p2.setOnFinished(e -> dealerCard2.setImage(d2));

        PauseTransition p3 = new PauseTransition(Duration.seconds(0.5));
        p3.setOnFinished(e -> dealerCard3.setImage(d3));

        PauseTransition intermediate = new  PauseTransition(Duration.seconds(0.5));
        intermediate.setOnFinished(e -> {
            updateLog("Dealer cards dealt.");
            boolean dq = info.getDealerQualified();
            if (!dq) {
                updateLog("Dealer does not qualify. Ante bet is pushed to the next hand.");
            }
            else {
                updateLog(info.getDealerHandString());
            }
            updateLog("\nFetching results...");
        });

        PauseTransition finish = new PauseTransition(Duration.seconds(5));
        finish.setOnFinished(e -> {
            boolean dq = info.getDealerQualified();
            int w = info.getRoundWinnings();

            // Case: dealer does NOT qualify → push ante, evaluate pair+ only
            if (!dq) {
                int pairPlusFmt = info.getPairPlusResult();
                updateLog("\nBreakdown");
                updateLog("Pair Plus: " + (pairPlusFmt >= 0 ? "+$" + pairPlusFmt : "-$" + Math.abs(pairPlusFmt)));
                // Display running total
                if (info.getTotalWinnings() < 0) {
                    updateLog("Game total: -$" + Math.abs(info.getTotalWinnings()));
                } else {
                    updateLog("Game total: $" + info.getTotalWinnings());
                }
                // Reset relevant bets
                pairPlusBet = 0;
                pairPlusBetLabel.setText("$0");
                playBet = 0;
                playBetLabel.setText("$0");
                dealButton.setDisable(false);
                playButton.setDisable(true);
                foldButton.setDisable(true);

                pairPlusCircle.setDisable(true);
                playCircle.setDisable(true);
                selectBet(BetType.ANTE);
                chip5Button.setDisable(false);
                chipResetButton.setDisable(false);
                updateLog("\nStarting new round. Place your bets again!\n");
                reinitialize(info);
                return;
            } else {
                // Case: dealer DOES qualify → evaluate full outcome
                updateLog(info.getOutcome());

                updateLog("\nBreakdown");

                int anteFmt = info.getAnteResult();
                int pairPlusFmt = info.getPairPlusResult();
                int playFmt = info.getPlayResult();

                updateLog("Ante: " + (anteFmt >= 0 ? "+$" + anteFmt : "-$" + Math.abs(anteFmt)) +
                        "  |  Pair: " + (pairPlusFmt >= 0 ? "+$" + pairPlusFmt : "-$" + Math.abs(pairPlusFmt)) +
                        "  |  Play: " + (playFmt >= 0 ? "+$" + playFmt : "-$" + Math.abs(playFmt)));
            }

            // Round summary
            if (w > 0) {
                updateLog("Round total: +$" + w);
            } else if (w < 0) {
                updateLog("Round total: -$" + Math.abs(w));
            } else {
                updateLog("Round ends with no net gain/loss.");
            }

            // Game running total
            if (info.getTotalWinnings() < 0) {
                updateLog("Game total: -$" + Math.abs(info.getTotalWinnings()));
            } else {
                updateLog("Game total: $" + info.getTotalWinnings());
            }
//            updateLog("\n");

            // Disable actions before moving on
            dealButton.setDisable(true);
            playButton.setDisable(true);
            foldButton.setDisable(true);

            updateLog("\nExiting play page in 10 seconds...");

            PauseTransition end = new PauseTransition(Duration.seconds(10));
            // Reset all cards to back image
            end.setOnFinished(ev -> {
                Image back = new Image(getClass().getResource("/images/back of card.png").toExternalForm());
                dealerCard1.setImage(back);
                dealerCard2.setImage(back);
                dealerCard3.setImage(back);
                playerCard1.setImage(back);
                playerCard2.setImage(back);
                playerCard3.setImage(back);
                anteBet = 0;
                anteBetLabel.setText("$" + anteBet);
                pairPlusBet = 0;
                pairPlusBetLabel.setText("$" + pairPlusBet);
                playBet = 0;
                playBetLabel.setText("$" + playBet);

                try {
                    mainApp.setSavedLog(logArea.getText());
                    mainApp.goToGameEndScene(info);
                } catch (Exception f) {
                    f.printStackTrace();
                }
            });

            end.play();
        });

        // Run entire reveal sequence
        SequentialTransition revealSeq = new SequentialTransition(p1, p2, p3, intermediate, finish);
        revealSeq.play();
    }

    public void handleFoldResult(PokerInfo info) {
        int w = info.getRoundWinnings();

        updateLog("Player selected fold.");
        updateLog(info.getOutcome());
        updateLog("\nBreakdown");

        int anteLoss = info.getAnteBet();
        int ppLoss = info.getPairPlusBet();

        // Fold always loses ante & pair plus
        updateLog("Ante: -$" + anteLoss);
        updateLog("Pair Plus: -$" + ppLoss);
        updateLog("Round total: -$" + Math.abs(w));

        int total = info.getTotalWinnings();
        if (total < 0) {
            updateLog("Game total: -$" + Math.abs(total));
        } else {
            updateLog("Game total: $" + total);
        }
        updateLog("\n");

        // Move to end screen
        Platform.runLater(() -> {
            try {
                mainApp.setSavedLog(logArea.getText());
                mainApp.goToGameEndScene(info);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Reload previous session's log when returning to this screen
    public void restoreLog(String text) {
        if (text != null && logArea != null) {
            logArea.setText(text);
            updateLog("Player selected play again.\n");
        }
    }
}