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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class TransferController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button txtf1;
    
    @FXML
    private Button txtta;
    
     @FXML
    private Button txtca1;
     
       @FXML
    private TextField txtc1;
       
        @FXML
    private TextField txtacc;

    @FXML
    private TextField txtb1;
    
    @FXML
    private TextField txtta1;
       
    Connection con;
     PreparedStatement pst;
      PreparedStatement pst1;
       PreparedStatement pst2;
        PreparedStatement pst3;
     
      ResultSet rs;
     
     public void Connect() {
       try {
        Class.forName("com.mysql.cj.jdbc.Driver"); // updated driver class
        con = DriverManager.getConnection("jdbc:mysql://localhost/rbibank", "root", "");

        if (con != null) {
            System.out.println("Connected to the database");
        }
    } catch (ClassNotFoundException | SQLException ex) {
        ex.printStackTrace();
    }
     }
     
      @FXML
     void handleBackButtonAction4(ActionEvent event) {
       
         try {
        String cust_id = txtc1.getText();

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/rbibank", "root", "");

        pst = con.prepareStatement("Select c.cust_id,c.firstname,c.lastname,a.balance from customer c,account a where c.cust_id=a.cust_id and a.acc_id=?");

        pst.setString(1, cust_id);
        rs = pst.executeQuery();

        if (rs.next() == false) {
            // No records found
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Account ID not found");
            alert.showAndWait();
        } else {
            // Records found
            String bal = rs.getString(4);
             txtb1.setText(bal.trim());

            

            // If you want to do something else with the records, you can add the logic here.
        }

    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
    }

     }
     
      @FXML
    void handleBackButtonAction2(ActionEvent event) throws ClassNotFoundException{
        
        try {
            
            String facc=txtc1.getText();
        String balance=txtb1.getText();
        String toaccount=txtta1.getText();
        String amount=txtacc.getText();
        
            Class.forName("com.mysql.cj.jdbc.Driver"); // updated driver class
            con = DriverManager.getConnection("jdbc:mysql://localhost/rbibank", "root", "");
            
           con.setAutoCommit(false);
            //sender part
            pst1=con.prepareStatement("UPDATE account SET balance = balance - ? WHERE acc_id = ?");
           
             pst1.setDouble(1, Double.parseDouble(amount)); 
            pst1.setString(2,facc);
           pst1.executeUpdate();
           
            //receiver part
            pst2=con.prepareStatement("UPDATE account SET balance = balance + ? WHERE acc_id = ?");
            pst2.setDouble(1, Double.parseDouble(amount)); 
            pst2.setString(2,toaccount);
            pst2.executeUpdate();
            
            
             pst3= con.prepareStatement("INSERT INTO transfer(f_account, to_account,amount) VALUES (?, ?, ?)");

        pst3.setString(1, facc);
        pst3.setString(2, toaccount);
      pst3.setDouble(3, Double.parseDouble(amount));
        pst3.executeUpdate();
      
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Transfer");
        alert.setContentText("Transferred Successfully");
        alert.showAndWait();

        con.commit();

    } catch (SQLException ex) {
        try {
            con.rollback();
        } catch (SQLException ex1) {
            Logger.getLogger(DepositController.class.getName()).log(Level.SEVERE, null, ex1);
        }
        Logger.getLogger(DepositController.class.getName()).log(Level.SEVERE, null, ex);

        // Display an error message
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Transfer Error");
        errorAlert.setContentText("An error occurred during the transfer. Please try again.");
        errorAlert.showAndWait();
    } finally {
        // Close resources (PreparedStatement, Connection) in a finally block.
        try {
            if (pst1 != null) {
                pst1.close();
            }
            if (pst2 != null) {
                pst2.close();
            }
            if (pst3 != null) {
                pst3.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(DepositController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

        
        
        
    }
     
      @FXML
    void handleBackButtonAction3(ActionEvent event) throws IOException {

          Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        // Create a new scene
        Scene scene = new Scene(root);

        // Get the current stage (window)
        Stage currentStage = (Stage)  txtca1.getScene().getWindow();

        // Set the scene for the current stage
        currentStage.setScene(scene);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Connect();
    }    
    
}
