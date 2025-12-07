import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;

/**
 * Main server class for the Three Card Poker game.
 * Manages server thread, connected clients, and communicates with the dashboard.
 */
public class ServerMain {

    private static ServerThread serverThread; // The thread running the server socket
    private static ServerDashboardController dashboardController; // Controller for updating the dashboard UI

    private static final int MAX_CLIENTS = 8; // Maximum number of clients allowed
    private static int connectedClients = 0; // Currently connected clients

    /*
     * Checks if the server can accept more clients.
     * returns: true if connected clients are less than MAX_CLIENTS
     */
    public static synchronized boolean canAcceptClient() {
        return connectedClients < MAX_CLIENTS;
    }

    /*
     * Sets the dashboard controller for UI updates.
     * parameter: controller - the ServerDashboardController instance
     */
    public static void setDashboard(ServerDashboardController controller) {
        dashboardController = controller;
    }

    /*
     * Starts the server on the given port if not already running.
     * parameter: port - port number to listen for client connections
     */
    public static void startServer(int port) {
        if (serverThread == null || !serverThread.isAlive()) {
            serverThread = new ServerThread(port);
            serverThread.start();
            log("Server starting on port " + port + "...");
        } else {
            log("Server is already running.");
        }
    }

    /*
     * Stops the server and resets connected clients count.
     */
    public static void stopServer() {
        if (serverThread != null) {
            serverThread.shutdown();
            serverThread = null;
        }
        connectedClients = 0;
    }

    /*
     * Logs a message either to the dashboard UI or console.
     * parameter:  msg - message to log
     */
    public static void log(String msg) {
        if (dashboardController != null) {
            Platform.runLater(() -> dashboardController.addLog(msg)); // Ensure UI updates occur on JavaFX thread
        } else {
            System.out.println(msg);
        }
    }

    /*
     * Handles a client connection. Increments connected clients and updates the dashboard.
     * parameter name - the name of the connected client
     */
    public static void clientConnected(String name) {
        connectedClients++;
        if (dashboardController != null) {
            Platform.runLater(() -> dashboardController.updateClientList(name, true));
        }
    }

    /*
     * Handles a client disconnection. Decrements connected clients and updates the dashboard.
     * parameter: name - the name of the disconnected client
     */
    public static void clientDisconnected(String name) {
        if (connectedClients > 0) {
            connectedClients--;
        }
        if (dashboardController != null) {
            Platform.runLater(() -> dashboardController.updateClientList(name, false));
        }
    }

    /*
     * For testing without JavaFX UI: starts the server in console mode.
     * parameter:  args - command line arguments
     */
    public static void main(String[] args) {
        int port = 8000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Three Card Poker Server running on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");
                ClientHandler handler = new ClientHandler(clientSocket);
                handler.start(); // Each client runs in its own thread
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    /*
     * Returns the current number of connected clients.
     * returns: connected clients count
     */
    public static synchronized int getConnectedClients() {
        return connectedClients;
    }

    /*
     * Logs a game result to the dashboard.
     * parameter: clientName - client who played the hand
     * parameter: bet - total bet amount
     * parameter: winnings - amount won
     */
    public static void logGameResult(String clientName, int bet, int winnings) {
        if (dashboardController != null) {
            Platform.runLater(() -> dashboardController.logGameResult(clientName, bet, winnings));
        }
    }

    /*
     * Logs a player action (e.g., dealt hand, folded) to the dashboard.
     * parameter: clientName - client performing the action
     * parameter: action - description of the action
     */
    public static void logPlayerAction(String clientName, String action) {
        if (dashboardController != null) {
            Platform.runLater(() -> dashboardController.logPlayerAction(clientName, action));
        }
    }

}
