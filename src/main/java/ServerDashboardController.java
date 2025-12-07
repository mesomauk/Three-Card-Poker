import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ServerDashboardController {

    @FXML
    private ListView<String> logListView; // Displays logs of server events and client actions

    @FXML
    private Label connectedClientsLabel; // Displays the number of connected clients
    private int connectedClients = 0; // Keeps track of the number of currently connected clients

    /**
     * Adds a message to the server log ListView and scrolls to the latest entry.
     * parameter:  msg - The message to display in the log
     */
    public void addLog(String msg) {
        logListView.getItems().add(msg);
        logListView.scrollTo(logListView.getItems().size() - 1); // Auto-scroll to newest log
    }

    /**
     * Updates the connected clients count and adds a log entry when a client joins or leaves.
     * parameter: name -The name of the client
     * parameter:  connected - True if the client joined, false if disconnected
     */
    public void updateClientList(String name, boolean connected) {
        if (connected) {
            this.connectedClients++;
            addLog(name + " joined. Total clients: " + this.connectedClients);
        } else {
            this.connectedClients--;
            addLog(name + " disconnected. Total clients: " + this.connectedClients);
        }
        connectedClientsLabel.setText("Connected clients: " + this.connectedClients);
    }

    /**
     * Logs the result of a game round for a specific client.
     * parameter: clientName - The client who played the hand
     * parameter: bet - The total bet amount
     * parameter: winnings - The result of the hand
     */
    public void logGameResult(String clientName, int bet, int winnings) {
        String msg = clientName + " played a hand. Bet: " + bet + ", Result: " + winnings;
        addLog(msg);
    }

    /**
     * Logs specific actions performed by a client (e.g., DEAL, PLAY, FOLD).
     * parameter: clientName - The client performing the action
     * parameter: action - Description of the action
     */
    public void logPlayerAction(String clientName, String action) {
        addLog(clientName + " " + action);
    }

    /**
     * Handles the "Stop Server" button click.
     * Stops the server gracefully and logs the action.
     */
    @FXML
    private void handleStopServer() {
        ServerMain.stopServer(); // Stop server thread and disconnect clients
        addLog("Server has been stopped manually.");
        System.exit(0); // Close the application after stopping the server
    }
}
