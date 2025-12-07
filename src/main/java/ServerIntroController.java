import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class ServerIntroController {

    @FXML
    private TextField portField; // user to enter server port
    @FXML
    private Label errorLabel; // Label to show error messages
    @FXML
    private Button startButton; // Button to start the server

    private ServerApp serverApp;

    public void setServerApp(ServerApp app) {
        this.serverApp = app;
    }

    @FXML
    public void initialize() {
        startButton.setOnAction(e -> startServer());
    }

    /*
     * Starts the server using the port number entered by the user.
     * Loads the server dashboard scene after successful startup.
     */
    private void startServer() {
        String portText = portField.getText(); // Get port entered by user
        try {
            int port = Integer.parseInt(portText);
            ServerMain.startServer(port); // Start the server thread

            serverApp.showDashboard();

        } catch (NumberFormatException ex) {
            // Handle invalid port input
            errorLabel.setText("Invalid port number!");
        } catch (Exception ex) {
            // Handle any other exceptions (e.g., FXML load errors)
            errorLabel.setText("Error loading dashboard!");
            ex.printStackTrace();
        }
    }
}
