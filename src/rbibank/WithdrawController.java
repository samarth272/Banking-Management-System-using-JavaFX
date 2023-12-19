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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class WithdrawController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private Button txtf1;
    
     @FXML
    private Button txto1;
     
    @FXML
    private Button txtca1;
    
     @FXML
    private Label txtd1;
    
    @FXML
    private TextField txtacc;
       
    @FXML
    private Label txtfn1;

    @FXML
    private Label txtln1;
    
     @FXML
    private TextField txtamount;
     
    @FXML
    private Label txtb1;

    @FXML
    private Label txtcn1;
    
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

     
     public void date(){
         
         DateTimeFormatter dtd=DateTimeFormatter.ofPattern("yyyy/MM/dd");
         LocalDateTime now= LocalDateTime.now();
         String date=dtd.format(now);
         
         txtd1.setText(date);
     }
     
     
    @FXML
    void handleBackButtonAction1(ActionEvent event) throws SQLException {
        try {
             String accno=txtacc.getText();
             
            Class.forName("com.mysql.cj.jdbc.Driver"); // updated driver class
            con = DriverManager.getConnection("jdbc:mysql://localhost/rbibank", "root", "");
             pst = con.prepareStatement("Select c.cust_id,c.firstname,c.lastname,a.balance from customer c,account a where c.cust_id=a.cust_id and a.acc_id=?");
             
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
            String id = rs.getString(1);
            String firstname = rs.getString(2);
            String lastname = rs.getString(3);
            String balance = rs.getString(4);
           
            txtcn1.setText(id.trim());
            txtfn1.setText(firstname.trim());
            txtln1.setText(lastname.trim());
            txtb1.setText(balance.trim());


            

            // If you want to do something else with the records, you can add the logic here.
        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DepositController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
     @FXML
void handleBackButtonAction2(ActionEvent event) throws ClassNotFoundException {

    try {

        con.setAutoCommit(false);
        String accno = txtacc.getText();
        String cust_id = txtcn1.getText();
        String firstname = txtfn1.getText();
        String lastname = txtln1.getText();
        String date = txtd1.getText();
        String balance = txtb1.getText();
        String amount = txtamount.getText();

        Class.forName("com.mysql.cj.jdbc.Driver"); // updated driver class
        con = DriverManager.getConnection("jdbc:mysql://localhost/rbibank", "root", "");

        pst = con.prepareStatement("INSERT INTO withdraw(acc_id, cust_id,date,balance,withdraw) VALUES (?, ?, ?, ?,?)");

        pst.setString(1, accno);
        pst.setString(2, cust_id);
        pst.setString(3, date);
        pst.setString(4, balance);
        pst.setString(5, amount);

        pst.executeUpdate();

     update = con.prepareStatement("UPDATE account SET balance = balance - ? WHERE acc_id = ?");
     update.setDouble(1, Double.parseDouble(amount)); // assuming amount is a String representation of a numeric value
     update.setString(2, accno);

     update.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Withdraw");
        alert.setContentText("Withdraw Successfully");
        alert.showAndWait();

        con.commit();
        
     
} catch (SQLException ex) {
    try {
        con.rollback();
    } catch (SQLException ex1) {
        Logger.getLogger(TransferController.class.getName()).log(Level.SEVERE, null, ex1);
    }
    ex.printStackTrace();
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
        date();
    }    
    
}
