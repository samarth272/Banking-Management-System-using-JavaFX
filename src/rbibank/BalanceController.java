/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rbibank;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class BalanceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
      @FXML
    private TextField txtat;

    @FXML
    private Button txtb;

    @FXML
    private Button txtcb;
    
    @FXML
    private Label txtb1;
    
       Connection con;
     PreparedStatement pst;
     PreparedStatement update;
      ResultSet rs;
     
     public void Connect() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/rbibank", "root", "");

        if (con != null) {
            System.out.println("Connected to the database");
        }
    } catch (ClassNotFoundException | SQLException ex) {
        ex.printStackTrace();
    } finally {
        // Close resources in a finally block
        try {
            if (con != null) con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
     
      @FXML
    void handleBackButtonAction4(ActionEvent event) throws SQLException {
        try {
             String accno=txtat.getText();
             
            Class.forName("com.mysql.cj.jdbc.Driver"); // updated driver class
            con = DriverManager.getConnection("jdbc:mysql://localhost/rbibank", "root", "");
             pst = con.prepareStatement("Select balance from account where acc_id=?");
             
             pst.setString(1, accno);
      
             rs = pst.executeQuery();

        if (rs.next() == false) {
            // No records found
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Account not found");
            alert.showAndWait();
        } else {
            // Records found
           
            String balance = rs.getString(1);
           
           
            txtb1.setText(balance.trim());


            

            // If you want to do something else with the records, you can add the logic here.
        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DepositController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
     


    @FXML
    void back(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        // Create a new scene
        Scene scene = new Scene(root);

        // Get the current stage (window)
        Stage currentStage = (Stage)  txtb.getScene().getWindow();

        // Set the scene for the current stage
        currentStage.setScene(scene);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         Connect();
    }    
    
}
