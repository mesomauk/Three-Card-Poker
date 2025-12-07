import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private int port; // Port number the server will listen on
    private boolean running = true; // Flag to control the server loop
    private ServerSocket serverSocket; // Server socket to accept client connections

    private static final int MAX_CLIENTS = 8; // Maximum number of clients allowed

    // Constructor to set the port
    public ServerThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket ss = new ServerSocket(port)) {
            this.serverSocket = ss; // Save reference to server socket

            ServerMain.log("Server started on port " + port);

            // Main server loop to accept clients
            while (running) {
                Socket client = ss.accept(); // Wait for a client to connect
                String name = "Client-" + client.getInetAddress().getHostAddress();

                // Check if server has room for more clients
                if (!ServerMain.canAcceptClient()) {
                    ServerMain.log("Connection rejected: " + name + " (server full)");
                    client.close(); // Reject the client if full
                    continue;
                }

                // Log client connection and start client handler
                ServerMain.log(name + " connected");
                ClientHandler handler = new ClientHandler(client);
                handler.start(); // Start a new thread for the client
            }

        } catch (IOException e) {
            if (running) {
                ServerMain.log("Server error: " + e.getMessage());
            }
        }
    }

    // Method to stop the server 
    public void shutdown() {
        running = false; // Stop the main loop
        try {
            if (serverSocket != null) serverSocket.close(); // Close the server socket
        } catch (IOException ignored) {}
        ServerMain.log("Server stopped");
    }
}
