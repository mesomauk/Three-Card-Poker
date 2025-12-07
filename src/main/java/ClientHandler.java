import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles communication between the server and a single client.
 * Each client connection is managed in its own thread.
 */
public class ClientHandler extends Thread {

    private Socket socket;                 // Socket for communication with the client
    private ObjectInputStream in;          // For receiving objects from client
    private ObjectOutputStream out;        // For sending objects to client
    private PokerInfo state;               // Tracks the current game state for this client
    private ThreeCardDeck deck;            // Deck used for dealing cards
    private String clientName;             // Identifier for this client

    /**
     * Constructs a ClientHandler for the given socket.
     * parameter: socket the client socket connection
     */
    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.state = new PokerInfo();
        this.clientName = "Client-" + socket.getInetAddress().getHostAddress(); // Client identified by IP
    }

    /**
     * Main execution loop for the client handler thread.
     * Handles incoming commands from the client and responds accordingly.
     */
    @Override
    public void run() {
        String clientName = "Client-" + socket.getInetAddress().getHostAddress();

        try {
            // Initialize input/output streams
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            // Send initial game state to client
            PokerInfo welcomeInfo = new PokerInfo();
            state.setTotalWinnings(1000); // Starting total for client
            out.writeObject(welcomeInfo);
            out.flush();

            // Log that the client connected
            ServerMain.logPlayerAction(clientName, "connected");
            ServerMain.clientConnected(clientName);

            // Main loop: continuously listen for client commands
            while (true) {
                ThreeCardLogic logic = new ThreeCardLogic();
                Object obj;

                try {
                    obj = in.readObject(); // Receive PokerInfo object from client
                } catch (EOFException e) {
                    // Client disconnected
                    break;
                }

                if (obj instanceof PokerInfo) {
                    PokerInfo pokerInfo = (PokerInfo) obj;

                    // Handle DEAL command: deal cards to player and dealer
                    if ("DEAL".equals(pokerInfo.getCommand())) {
                        deck = new ThreeCardDeck();
                        deck.buildDeck();
                        deck.shuffleDeck();

                        state.setPlayerHand(new ArrayList<>());
                        state.setDealerHand(new ArrayList<>());
                        for (int i = 0; i < 3; i++) {
                            state.getPlayerHand().add(deck.dealCard());
                            state.getDealerHand().add(deck.dealCard());
                        }

                        // Record the bets and initial round state
                        state.setAnteBet(pokerInfo.getAnteBet());
                        state.setPairPlusBet(pokerInfo.getPairPlusBet());
                        state.setPlayBet(pokerInfo.getPlayBet());
                        state.setPlay(false);
                        state.setFold(false);
                        state.setRoundWinnings(0);
                        state.setCommand("DEAL");
                        state.setOutcome(null);
                        state.setPlayerHandString(pokerInfo.getPlayerHandString());
                        state.setDealerHandString(null);
                        state.setDealerQualified(false);

                        // Prepare response for client
                        PokerInfo response = new PokerInfo("DEAL_RESULT");
                        String playerHandString = "Player drew a " + logic.getHandName(state.getPlayerHand(), true) + ".";

                        ServerMain.logPlayerAction(
                                clientName, "dealt a hand with bets: Ante=" + pokerInfo.getAnteBet() +
                                        ", PairPlus=" + pokerInfo.getPairPlusBet() +
                                        ", Play=" + pokerInfo.getPlayBet()
                        );

                        response.setPlayerHand(new ArrayList<>(state.getPlayerHand()));
                        response.setDealerHand(new ArrayList<>(state.getDealerHand()));
                        response.setAnteBet(state.getAnteBet());
                        response.setPairPlusBet(state.getPairPlusBet());
                        response.setPlayBet(state.getPlayBet());
                        response.setPlay(state.isPlay());
                        response.setFold(state.isFold());
                        response.setRoundWinnings(0);
                        response.setCommand("DEAL_RESULT");
                        response.setOutcome(null);
                        response.setPlayerHandString(playerHandString);
                        response.setDealerHandString(null);
                        response.setDealerQualified(false);

                        out.writeObject(response); // Send response back to client
                        out.flush();
                    }
                    // Handle PLAY command: evaluate round and calculate winnings
                    else if ("PLAY".equals(pokerInfo.getCommand())) {

                        ThreeCardRound round = new ThreeCardRound(deck);
                        round.setHands(state.getPlayerHand(), state.getDealerHand());
                        int winnings = round.evaluateRound(state.getAnteBet(), state.getPairPlusBet(), state.getPlayBet(), false);

                        ServerMain.logGameResult(
                                clientName, pokerInfo.getAnteBet() + pokerInfo.getPairPlusBet() + pokerInfo.getPlayBet(),
                                winnings
                        );

                        state.setPlay(true);
                        state.setFold(false);
                        state.setRoundWinnings(winnings);
                        state.setTotalWinnings(state.getTotalWinnings() + winnings);
                        state.setCommand("PLAY");
                        state.setDealerQualified(round.dealerQualifies(state.getDealerHand()));

                        // Prepare result to send to client
                        PokerInfo result = new PokerInfo("PLAY_RESULT");
                        String playerHandString = "Player drew a " + logic.getHandName(state.getPlayerHand(), true) + ".";
                        String dealerHandString = "Dealer drew a " + logic.getHandName(state.getDealerHand(), true) + ".";
                        String outcome = logic.getOutcomeDescription(state.getDealerHand(), state.getPlayerHand());

                        result.setPlayerHand(new ArrayList<>(state.getPlayerHand()));
                        result.setDealerHand(new ArrayList<>(state.getDealerHand()));
                        result.setPlay(state.isPlay());
                        result.setFold(state.isFold());
                        result.setRoundWinnings(winnings);
                        result.setTotalWinnings(state.getTotalWinnings());
                        result.setCommand("PLAY_RESULT");
                        result.setOutcome(outcome);
                        result.setPlayerHandString(playerHandString);
                        result.setDealerHandString(dealerHandString);
                        result.setDealerQualified(state.getDealerQualified());
                        result.setAnteResult(round.getAnteResult());
                        result.setPairPlusResult(round.getPairPlusResult());
                        result.setPlayResult(round.getPlayResult());

                        out.writeObject(result);
                        out.flush();
                    }
                    // Handle FOLD command when player forfeits bets
                    else if ("FOLD".equals(pokerInfo.getCommand())) {

                        int anteLoss = state.getAnteBet();
                        int ppLoss = state.getPairPlusBet();
                        int totalLoss = -(anteLoss + ppLoss);

                        state.setRoundWinnings(totalLoss);
                        state.setTotalWinnings(state.getTotalWinnings() + totalLoss);
                        state.setFold(true);
                        state.setPlay(false);
                        state.setCommand("FOLD");

                        ServerMain.logPlayerAction(clientName, "folded");

                        // Prepare result to send to client
                        PokerInfo result = new PokerInfo("FOLD_RESULT");
                        result.setAnteBet(state.getAnteBet());
                        result.setPairPlusBet(state.getPairPlusBet());
                        result.setRoundWinnings(totalLoss);
                        result.setTotalWinnings(state.getTotalWinnings());
                        result.setOutcome("Player folded, all bets forfeited.");
                        result.setAnteResult(-anteLoss);
                        result.setPairPlusResult(-ppLoss);
                        result.setPlayResult(0);
                        result.setFold(true);

                        out.writeObject(result);
                        out.flush();
                    }
                }
            }

        } catch (Exception e) {
            // Catch-all for any errors during client handling
            System.out.println("handler error: " + e.getMessage());
        } finally {
            // Clean up resources and notify server that client disconnected
            ServerMain.clientDisconnected(clientName);
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }
}
