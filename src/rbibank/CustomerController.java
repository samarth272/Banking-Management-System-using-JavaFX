/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rbibank;

import java.sql.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CustomerController implements Initializable {
    
   
    @FXML
    private Stage stage;
    
    @FXML
    private Button backButton1;
    
       @FXML
    private TextField txtb1;

    @FXML
    private TextField txtc1;

    @FXML
    private TextField txtcn1;

    @FXML
    private TextField txtfn1;

    @FXML
    private TextField txtln1;

    @FXML
    private TextField txtp1;

    @FXML
    private TextField txts1;
  
    @FXML
    private Button txtca1;
    
     @FXML
    private Button txtad1;
     
     @FXML
private ComboBox<String> txtcb1;

      @FXML
    private Label txtcid;
     
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
            rs = s.executeQuery("SELECT MAX(cust_id) FROM customer");

            rs.next();
           rs.getString("MAX(cust_id)");

            if ( rs.getString("MAX(cust_id)") == null) {
                txtcid.setText("CS001");
            } else {
                long id = Long.parseLong(rs.getString("MAX(cust_id)").substring(2,rs.getString("MAX(cust_id)").length()));
                id++;
                txtcid.setText("CS" + String.format("%03d", id));
            }

        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

       @FXML
public void branch() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver"); // updated driver class
        con = DriverManager.getConnection("jdbc:mysql://localhost/rbibank", "root", "");

        pst = con.prepareStatement("select * from branch");
        ResultSet rs = pst.executeQuery();

        ObservableList<String> branchNames = FXCollections.observableArrayList();

        while (rs.next()) {
            branchNames.add(rs.getString(2));
        }

        txtcb1.setItems(branchNames);

    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

       
    @FXML
    public void handleBackButtonAction1(ActionEvent event) throws IOException {
        // Load the FXMLDocument.fxml file (the previous page)
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        // Create a new scene
        Scene scene = new Scene(root);

        // Get the current stage (window)
        Stage currentStage = (Stage)  backButton1.getScene().getWindow();

        // Set the scene for the current stage
        currentStage.setScene(scene);
    }

   @FXML

public void handleBackButtonAction2(ActionEvent event) throws IOException {
   
    String cno= txtcid.getText();
    String fname = txtfn1.getText();
    String lname = txtln1.getText();
    String stret = txts1.getText();
    String city = txtc1.getText();
    String branch = txtcb1.getSelectionModel().getSelectedItem();
    String phn = txtp1.getText();

    try {
        pst = con.prepareStatement("INSERT INTO customer(cust_id,firstname, lastname, street, city, branch, phone) VALUES (?,?, ?, ?, ?, ?, ?)");

        pst.setString(1, cno);
        pst.setString(2, fname);
        pst.setString(3, lname);
        pst.setString(4, stret);
        pst.setString(5, city);
        pst.setString(6, branch);
        pst.setString(7, phn);

        int status = pst.executeUpdate();

        if (status == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Customer");
            alert.setContentText("Record Added Successfully");
            alert.showAndWait();

            AutoID();

           // Clear the form fields
txtfn1.setText("");
txtln1.setText("");
txts1.setText("");
txtc1.setText("");
txtcb1.getSelectionModel().clearSelection();
txtp1.setText("");
txtfn1.requestFocus();

            // Optional: Add code to update the database or perform any additional actions after insertion
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fail");
            alert.setHeaderText("Customer");
            alert.setContentText("Record Added Failed");
            alert.showAndWait();
        }

    } catch (SQLException ex) {
        Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    @FXML
    void handleBackButtonAction3(ActionEvent event) {
    // Assuming that "txtad1" is a reference to the button that triggers this action
    Stage stage = (Stage) txtad1.getScene().getWindow();
    stage.close();
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Connect();  // Call Connect() to establish the database connection
        AutoID();
        branch();
    }
}

