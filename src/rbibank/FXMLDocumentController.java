/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rbibank;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private MenuItem cus;

     @FXML
    private MenuItem dep;
     
     @FXML
    private MenuItem dep2;
     
     @FXML
    private MenuItem acc;
     
    @FXML
    private Stage stage;
    
     @FXML
    private MenuItem wi;
     
      @FXML
    private MenuItem tf;
      
       @FXML
    private MenuItem bc;

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {
        // Load the customer.fxml file
        Parent root = FXMLLoader.load(getClass().getResource("customer.fxml"));

        // Create a new scene
        Scene scene = new Scene(root);

        // Get the current stage (window)
        Stage currentStage = (Stage) cus.getParentPopup().getOwnerWindow();

        // Set the scene for the current stage
        currentStage.setScene(scene);
    }
    
    @FXML
    public void handleButtonAction1(ActionEvent event) throws IOException {
        // Load the customer.fxml file
        Parent root = FXMLLoader.load(getClass().getResource("account.fxml"));

        // Create a new scene
        Scene scene = new Scene(root);      

        // Get the current stage (window)
        Stage currentStage = (Stage) acc.getParentPopup().getOwnerWindow();

        // Set the scene for the current stage
        currentStage.setScene(scene);
    }

      @FXML
    public void handleButtonAction2(ActionEvent event) throws IOException {
        // Load the customer.fxml file
        Parent root = FXMLLoader.load(getClass().getResource("Deposit.fxml"));

        // Create a new scene
        Scene scene = new Scene(root);

        // Get the current stage (window)
        Stage currentStage = (Stage) dep.getParentPopup().getOwnerWindow();

        // Set the scene for the current stage
        currentStage.setScene(scene);
    }
    
    public void handleButtonAction23(ActionEvent event) throws IOException {
        // Load the customer.fxml file
        Parent root = FXMLLoader.load(getClass().getResource("withdraw.fxml"));

        // Create a new scene
        Scene scene = new Scene(root);

        // Get the current stage (window)
        Stage currentStage = (Stage) dep2.getParentPopup().getOwnerWindow();

        // Set the scene for the current stage
        currentStage.setScene(scene);
    }

    
    @FXML
    void handleButtonAction5(ActionEvent event) throws IOException{

         Parent root = FXMLLoader.load(getClass().getResource("transfer.fxml"));

        // Create a new scene
        Scene scene = new Scene(root);

        // Get the current stage (window)
        Stage currentStage = (Stage) tf.getParentPopup().getOwnerWindow();

        // Set the scene for the current stage
        currentStage.setScene(scene);
    }
    
     @FXML
    void handleButtonAction6(ActionEvent event) throws IOException{

         Parent root = FXMLLoader.load(getClass().getResource("balance.fxml"));

        // Create a new scene
        Scene scene = new Scene(root);

        // Get the current stage (window)
        Stage currentStage = (Stage) bc.getParentPopup().getOwnerWindow();

        // Set the scene for the current stage
        currentStage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
