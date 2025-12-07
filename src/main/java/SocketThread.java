import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class SocketThread extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean running = true;

    private ClientMain mainApp;

    public void setMainApp(ClientMain app) {
        this.mainApp = app;
    }

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            mainApp.onConnected();

            // Continuously read server messages until socket closes
            while (running) {
                try {
                    Object obj = in.readObject();
                    // Forward incoming PokerInfo objects to ClientMain
                    if (obj instanceof PokerInfo) {
                        PokerInfo info = (PokerInfo) obj;
                        mainApp.handleIncomingPokerInfo(info);
                    }
                } catch (EOFException | SocketException e) {
                    break;
                }
            }
        } catch (Exception e) {
            // Any unexpected setup or read error
            mainApp.onConnectionError(e);
        } finally {
            closeAll();  // Cleanup after disconnect
            mainApp.onDisconnected();
        }
    }

    //Sends a PokerInfo object to the server
    public void send(PokerInfo info) throws IOException {
        out.writeObject(info);
        out.flush();
    }

    //Safely closes all streams and the socket.
    private void closeAll() {
        try { if (in != null) in.close(); } catch (Exception ignored) {}
        try { if (out != null) out.close(); } catch (Exception ignored) {}
        try { if (socket != null) socket.close(); } catch (Exception ignored) {}
    }
}
