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
import java.sql.Statement;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AccountController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button backButton2;

    @FXML
    private Button txtad1;

      @FXML
    private Button txtf1;
      
    @FXML
    private Label txtan1;

     @FXML
private ComboBox<String> txtat1;

    @FXML
    private TextField txtba1;

    @FXML
    private TextField txtc1;

    @FXML
    private Button txtca12;

    @FXML
    private TextField txtcn1;

     Connection con;
     PreparedStatement pst;
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
     public void AutoID() {
        try {
            Statement s = con.createStatement();
            rs = s.executeQuery("SELECT MAX(acc_id) FROM account");

            rs.next();
           rs.getString("MAX(acc_id)");

            if ( rs.getString("MAX(acc_id)") == null) {
                txtan1.setText("A0001");
            } else {
                long id = Long.parseLong(rs.getString("MAX(acc_id)").substring(2,rs.getString("MAX(acc_id)").length()));
                id++;
                txtan1.setText("A0" + String.format("%03d", id));
            }

        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    
    public void handleBackButtonAction2(ActionEvent event) throws IOException {
        // Load the FXMLDocument.fxml file (the previous page)
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        // Create a new scene
        Scene scene = new Scene(root);

        // Get the current stage (window)
        Stage currentStage = (Stage)  backButton2.getScene().getWindow();

        // Set the scene for the current stage
        currentStage.setScene(scene);
    }
    
     @FXML
    public void handleBackButtonAction1(ActionEvent event) {
   
    String ano = txtan1.getText();
    String cid = txtc1.getText();
    String cname = txtcn1.getText();
    String acctype = txtat1.getSelectionModel().getSelectedItem();
    String bal = txtba1.getText();

    try {
        pst = con.prepareStatement("INSERT INTO account(acc_id, cust_id, acc_type, balance) VALUES (?, ?, ?, ?)");

        pst.setString(1, ano);
        pst.setString(2, cid);
        pst.setString(3, acctype);
        pst.setString(4, bal);

        int status = pst.executeUpdate();

        if (status == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Customer");
            alert.setContentText("Account Created Successfully");
            alert.showAndWait();

            AutoID();

            // Clear the form fields
            txtc1.setText("");
            txtcn1.setText("");
            txtat1.getSelectionModel().clearSelection();
            txtba1.setText("");
            txtc1.requestFocus();

            // Optional: Add code to update the database or perform any additional actions after insertion
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fail");
            alert.setHeaderText("Customer");
            alert.setContentText("Record Addition Failed");
            alert.showAndWait();
        }

    } catch (SQLException ex) {
        Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

     @FXML
    void handleBackButtonAction3(ActionEvent event) {

        Stage stage = (Stage) txtca12.getScene().getWindow();
    stage.close();
    }
    
     @FXML
     void handleBackButtonAction4(ActionEvent event) {
       try {
        String cust_id = txtc1.getText();

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/rbibank", "root", "");

        pst = con.prepareStatement("Select * from customer where cust_id= ?");

        pst.setString(1, cust_id);
        rs = pst.executeQuery();

        if (rs.next() == false) {
            // No records found
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("No records found for the given customer ID.");
            alert.showAndWait();
        } else {
            // Records found
            String fname = rs.getString("firstname");
            txtcn1.setText(fname.trim());

            

            // If you want to do something else with the records, you can add the logic here.
        }

    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

        
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Connect();
        AutoID() ;
         txtat1.getItems().addAll("Saving", "Fix", "Current");
    }    
    
}
