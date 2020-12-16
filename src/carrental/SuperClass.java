/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SuperClass {

    Connection con;
    ResultSet rs=null;
    PreparedStatement ps;
    static String username="TestUser";
    @FXML
    protected MenuBar bar;

    public SuperClass() {
        connect();
    } //called wherever superclass is instantiated

    @FXML
    protected Button btnLogin;

    public boolean connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver"); //load driver
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental", "root", "");
            System.out.println("Database Connected!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Database Connection Error!");
            alert.setContentText("Couldn't connect to database. Make sure you are running the MySql database and Apache server. Error Message: "+
                    e.getMessage());
            alert.showAndWait();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    //load homepage
    @FXML
    protected void toHome() throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) btnLogin.getScene().getWindow();

        stage.setScene(scene);    //set the scene of the stage to the earlier defined scene variable(has parent already)
        stage.setTitle("Car Rental Software");
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    protected void toCarRegister() throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Car.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) bar.getScene().getWindow();   //bar=menu bar

        stage.setScene(scene);
        stage.setTitle("Car Form");
        
        stage.show();
    }

    @FXML
    protected void toLogin() throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) bar.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void toCustomerRegister() throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) bar.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Customer Form");
        
        stage.show();
    }

    @FXML
    protected void toEmployeeRegister() throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("Employee.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) bar.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Employee Form");
        
        stage.show();
    }

    @FXML
    protected void toCarList() throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("CarList.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) bar.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Car List");
        
        stage.show();
    }

    @FXML
    protected void toRentedCarList() throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("RentedCarList.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) bar.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Rented Cars List");
        
        stage.show();
    }

    @FXML
    protected void exit() throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you Sure?");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit?");
        alert.showAndWait().ifPresent(response->{
            if (response == ButtonType.OK){
                System.exit(0);
            }
        });
        con.close();
    }
}
