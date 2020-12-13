package carrental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class RentedCarList extends SuperClass implements Initializable {

    @FXML private Label labelFname,labelLname,labelPhone,labelLicense,labelPurpose,labelKebele,labelDayLeft;
    @FXML private ListView listCar;
    @FXML private ImageView imageView;
    String customerID;
    String picture;
    String selectedCarPlate;


    @FXML
    private void displayCar(){
//        connect();

        // to clear the car list before refreshing it
        listCar.getItems().clear();
        try {
            // to get all rented cars
            ps=con.prepareStatement("select * from rented_car_list where Is_Rented= true");
            rs = ps.executeQuery();

            // add rented cars into the rented car list
            while (rs.next()) {
                String plate = rs.getString("Plate");
//                customerID = rs.getString("Customer_ID");

                ps=con.prepareStatement("select * from car where Plate=?");
                ps.setString(1,plate);
                rs=ps.executeQuery();
                rs.next();

                listCar.getItems().add(rs.getString("Model")+", ["+rs.getString("Plate")+"]");
            }
            // to be displayed if no rented car is found
            if(listCar.getItems().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information!");
                alert.setHeaderText(null);
                alert.setContentText("No Rented Cars");
                alert.showAndWait();
            }
            // on mouse click on selected item display specs about the person who rented the car
            listCar.setOnMouseClicked(e->{
                String selectedItem = listCar.getSelectionModel().getSelectedItem().toString();// get the selected item
                selectedCarPlate = selectedItem.substring(selectedItem.indexOf("[")+1,selectedItem.indexOf("]"));
                System.out.println(selectedCarPlate);
                try {
                    ps=con.prepareStatement("select * from rented_car_list where Plate=?");
                    ps.setString(1,selectedCarPlate);
                    rs=ps.executeQuery();
                    rs.next();
                    customerID = rs.getString("Customer_ID");
                    Date date_of_rent = rs.getDate("Date_Of_Rent");
                    Calendar cal = Calendar.getInstance();
                    String daysLeft = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

                    ps=con.prepareStatement("select Picture from car where Plate=?");
                    ps.setString(1,selectedCarPlate);
                    rs=ps.executeQuery();
                    rs.next();
                    picture=rs.getString("Picture");

                    ps=con.prepareStatement("select * from customer where Kebele_ID_Number=?");
                    ps.setString(1,customerID);
                    rs=ps.executeQuery();
                    rs.next();

                    File file = new File(picture);
                    Image image = new Image("file:///"+file.getAbsolutePath().replace("\\","/"));
                    imageView.setImage(image);

                    labelFname.setText(rs.getString("FName"));
                    labelLname.setText(rs.getString("LName"));
                    labelPhone.setText(String.valueOf(rs.getInt("Phone")));
                    labelLicense.setText(rs.getString("Driver_licence_Number"));
                    labelKebele.setText(rs.getString("Kebele_ID_Number"));
                    labelPurpose.setText(rs.getString("Renting_Purpose"));

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields(){
        labelFname.setText(null);
        labelLname.setText(null);
        labelPhone.setText(null);
        labelLicense.setText(null);
        labelPurpose.setText(null);
        labelKebele.setText(null);
        imageView.setImage(null);
    }

    private void returnedCar() throws Exception{
//        connect();
        String item = listCar.getSelectionModel().getSelectedItem().toString();

        ps=con.prepareStatement("update rented_car_list set Is_Rented=? where Plate=?");
        ps.setBoolean(1,false);
        ps.setString(2,selectedCarPlate);
        ps.executeUpdate();
        displayCar();
        clearFields();
    }

    @FXML
    private void returned() throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation!");
        alert.setHeaderText(null);
        alert.setContentText("Are you Sure?");
        alert.showAndWait().ifPresent(response->{
            if (response == ButtonType.OK){
                try {
                    returnedCar();
                    clearFields();
                    displayCar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayCar();
    }
}
