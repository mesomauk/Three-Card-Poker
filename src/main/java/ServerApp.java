import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;


public class ServerApp extends Application {

    private Stage primaryStage; // Keep reference to primary stage for switching scenes

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Load the intro FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("serverIntro.fxml"));
        Parent root = loader.load();

        // Get the controller
        ServerIntroController controller = loader.getController();
        controller.setServerApp(this);
        Scene introScene = new Scene(root);

        // Apply CSS to style the intro scene
        introScene.getStylesheets().add(getClass().getResource("/styles/dashboardStyle.css").toExternalForm());

        // Set the stage title and show the intro scene
        primaryStage.setTitle("Three Card Poker Server");
        primaryStage.setScene(introScene);
        primaryStage.show();
    }

    /**
     * Switches the primary stage to the server dashboard scene.
     * Should be called after the server has been started.
     */
    public void showDashboard() throws Exception {
        // Load the dashboard FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("serverDashboard.fxml"));
        Scene dashboardScene = new Scene(loader.load());

        // Get the controller for the dashboard and set it in ServerMain
        ServerDashboardController controller = loader.getController();
        ServerMain.setDashboard(controller);

        // Apply CSS to the dashboard scene
        dashboardScene.getStylesheets().add(getClass().getResource("/styles/dashboardStyle.css").toExternalForm());

        // Replace the current scene with the dashboard
        primaryStage.setScene(dashboardScene);
        primaryStage.setTitle("Server Dashboard"); // Update window title
    }

    public static void main(String[] args) {
        launch(args);
    }
}
