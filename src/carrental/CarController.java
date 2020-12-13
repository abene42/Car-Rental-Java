/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CarController extends SuperClass implements Initializable {
    
    @FXML protected TextField fieldModel,fieldPlate,fieldCompany,fieldColor,fieldEngine,fieldPicture,fieldPrice;
    @FXML protected DatePicker pickerDate;
    @FXML protected ColorPicker pickerColor;
    @FXML protected ComboBox comboType;
    public String filePath;

    //Write data into Database
    @FXML
    private void writeCarIntoDB() {
        try {
           connect();
            ps=con.prepareStatement("insert into car(Model,Plate,Type,Engine_Model,Buying_Price,Production_Year,Picture,Renting_Price,Color,Company)" +
                    " values(?,?,?,?,?,?,?,?,?,?);");
            if (checkIfEmpty() == 1) {
//                Adding the car into the car table in the database
                ps.setString(1,fieldModel.getText());
                ps.setString(2,fieldPlate.getText());
                ps.setString(3,comboType.getValue().toString());
                ps.setString(4,fieldEngine.getText());
                ps.setDouble(5,Double.parseDouble(fieldPrice.getText()));
                ps.setInt(6,pickerDate.getValue().getYear());
                ps.setString(7,filePath);
                ps.setInt(8,calculateRentingPrice(Double.parseDouble(fieldPrice.getText())));
                Color color = pickerColor.getValue();
                String col = "RGB("+(int)(color.getRed()*255)+","+(int)(color.getGreen()*255)+","+(int)(color.getBlue()*255)+")";
                ps.setString(9,col);
                ps.setString(10,fieldCompany.getText());
                ps.executeUpdate();

//                To increment the amount of car available
                ps = con.prepareStatement("select * from car_status where Model = ?");
                ps.setString(1,fieldModel.getText());
                rs = ps.executeQuery();


                if(rs.next()){
                    int amount = rs.getInt("Amount") + 1;
                    ps = con.prepareStatement("update car_status set Amount=? where Model=?");
                    ps.setInt(1,amount);
                    ps.setString(2,fieldModel.getText());
                    ps.executeUpdate();
                }else{
                    ps = con.prepareStatement("insert into car_status(Model,Amount,Rent_Times) values (?,?,?)");
                    ps.setString(1,fieldModel.getText());
                    ps.setInt(2,1);
                    ps.setInt(3,0);
                    ps.executeUpdate();
                }

//               Alert for success
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText(null);
                alert.setContentText("Car successfully submitted!");
                alert.showAndWait();
//                clearFieldsReg();
            } else if (checkIfEmpty() == 0) {
//                Alert for empty fields
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed!");
                alert.setHeaderText(null);
                alert.setContentText("No empty entry is allowed!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Check if all the fields are filled with appropriate data
    @FXML
    private int checkIfEmpty() {
        if (fieldModel.getText().length() == 0) {
            return 0;
        }
        if (fieldPlate.getText().length() == 0) {
            return 0;
        }
        if (fieldCompany.getText().length() == 0) {
            return 0;
        }
        if (comboType.getValue() == null) {
            return 0;
        }
        if (pickerColor.getValue() == null) {
            return 0;
        }
        if (fieldEngine.getText().length() == 0) {
            return 0;
        }
        if(pickerDate.getValue() == null){
            return 0;
        }
        if(fieldPicture.getText().length()==0){
            return 0;
        }
        if (fieldPrice.getText().length() == 0) {
            return 0;
        }
        return 1;
    }

    //Clear all the fields after submission
    protected void clearFieldsReg(){
        fieldModel.clear();
        fieldPlate.clear();
        fieldCompany.clear();
        fieldEngine.clear();
//        comboType.setValue(null);
        fieldPicture.clear();
        fieldPrice.clear();
        pickerDate.setValue(null);
//        pickerColor.setValue(null);
    }

    //Algorithm to calculate the renting price
    private int calculateRentingPrice(Double buyingPrice){
        Double floatPrice = (buyingPrice/(10*52));// buying price / (year required to replace the buying price * number of weeks in a year)
        // assuming the car will be rented at least once every week
        return floatPrice.intValue();
    }


    @FXML
    private void carChooser() throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG/jpg","*.jpg"),
                new FileChooser.ExtensionFilter("PNG","*.png"),
                new FileChooser.ExtensionFilter("BITMAP","*.bmp")
        );
//        fileChooser.setInitialDirectory(new File("D:/Documents/Final Projects/AP/FinalProject/CarRental/src/carrental/Pictures"));
        File file = fileChooser.showOpenDialog(comboType.getScene().getWindow());
        filePath = file.getAbsolutePath();
        fieldPicture.setText(filePath);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fieldPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fieldPrice.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        comboType.getItems().addAll("Compact","Mid-size","Full-size","Minivan","Pickup Truck","Sport","Van","Executive","Light Truck");
    }
}
