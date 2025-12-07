import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {

    private SocketThread socketThread;
    private Stage primaryStage;
    private boolean darkMode = false;

    private GamePlaySceneController gameplayController;
    private GameEndSceneController gameEndController;
    private String savedLog; // Saved log between scene transitions


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/WelcomeScene.fxml"));
        Parent root = loader.load();

        WelcomeSceneController controller = loader.getController();
        controller.setMainApp(this);

        primaryStage.setTitle("Three Card Poker Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    //Switch to gameplay scene after connection succeeds.
    public void goToGameScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/GamePlayScene.fxml"));
            Parent root = loader.load();
            GamePlaySceneController controller = loader.getController();
            controller.setMainApp(this);
            this.gameplayController = controller;
            primaryStage.getScene().setRoot(root);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Switch to game end screen after PLAY or FOLD results.
    public void goToGameEndScene(PokerInfo info) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/GameEndScene.fxml"));
            Parent root = loader.load();
            GameEndSceneController controller = loader.getController();
            controller.setMainApp(this);
            controller.setFinalInfo(info);
            controller.setLog(savedLog);
            this.gameEndController = controller;
            primaryStage.getScene().setRoot(root);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Send a PokerInfo object to the server.
    public void sendToServer(PokerInfo info) {
        try {
            socketThread.send(info);
        } catch (Exception e) {
            System.out.println("Send error: " + e.getMessage());
        }
    }


    //Reloads the GamePlayScene (used on reconnect or Play Again).
    public void showGameScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/GamePlayScene.fxml"));
        Scene scene = new Scene(loader.load());

        GamePlaySceneController controller = loader.getController();
        controller.setMainApp(this);
        this.gameplayController = controller;
        controller.restoreLog(savedLog);
        applyTheme(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onConnected() {
        System.out.println("Connected to server.");

        Platform.runLater(() -> {
            try {
                showGameScene();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void onDisconnected() {
        System.out.println("Disconnected from server.");
    }

    public void onConnectionError(Exception e) {
        System.out.println("Connection error: " + e.getMessage());
    }

    //Central dispatcher for all server messages.
    public void handleIncomingPokerInfo(PokerInfo info) {
        if ("DEAL_RESULT".equals(info.getCommand())) {
            gameplayController.handleDealCards(info);
        }
        else if ("PLAY_RESULT".equals(info.getCommand())) {
            gameplayController.handlePlayResult(info);
        }
        else if ("FOLD_RESULT".equals(info.getCommand())) {
            gameplayController.handleFoldResult(info);
        }
    }

    // Called from WelcomeSceneController after creating socket
    public void startSocketThread(SocketThread thread) {
        this.socketThread = thread;
        socketThread.setMainApp(this);
        socketThread.start();
    }


    private void applyTheme(Scene scene) {
        scene.getStylesheets().clear();
        
    }

    public void setSavedLog(String log) {
        savedLog = log;
    }

    public static void main(String[] args) {
launch(args);
    }
}

