import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;

public class WelcomeSceneController {

    @FXML
    private TextField ipField;  // User-entered server IP address

    @FXML
    private TextField portField;  // User-entered server port number


    private Socket socket;  // Socket for the server connection
    private ClientMain mainApp;

    /*Called when the user presses the "Connect" button.
     * Validates input and attempts to connect to the server on a background thread.
     */
  @FXML
    private void handleConnect(ActionEvent event) {

        String ip = ipField.getText().trim();
        String portText = portField.getText().trim();
      // Require both fields to be filled
      if (ip.isEmpty() || portText.isEmpty()) {
            showAlert("Missing Information", "Enter both IP and Port.");
            return;
        }

        int port;
        try {
            port = Integer.parseInt(portText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Port", "Port must be a number.");
            return;
        }

      // Perform socket connection in a background thread
      new Thread(() -> {
            try {
                socket = new Socket(ip, port);

                // Start reader thread for server communication
                SocketThread thread = new SocketThread(socket);
                mainApp.startSocketThread(thread);

                // Switch scenes on success
                Platform.runLater(() -> {
                    showAlert("Connection Successful", "Connected to Server");
                    mainApp.goToGameScene();
                });
            } catch (IOException e) {
                Platform.runLater(() -> {
                    showAlert("Connection Failed", "Cannot connect:\n" + e.getMessage());

                });
            }
        }).start();

    }


    //Shows a simple information popup alert.
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setMainApp(ClientMain app) {
        this.mainApp = app;
    }
}
