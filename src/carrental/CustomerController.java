package carrental;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CustomerController extends SuperClass implements Initializable {
    @FXML
    protected Label labelWarning2,labelPrice;
    @FXML
    protected MenuBar bar;
    @FXML
    protected TextField fieldFName,fieldLName,fieldMName,fieldKebeleID,fieldLicence,fieldAge,fieldPhone,fieldDay;
    @FXML
    protected TextArea areaPurpose;
    @FXML
    ComboBox comboCar;
    @FXML
    Button btnSubmit;
    private static int price=0;
    private static String plate;

    @FXML
    private void writeCustomerIntoDB() {
        try{
            if (checkIfEmpty() == 1) {
//                connect();
                String model = comboCar.getValue().toString();
                ps =con.prepareStatement("select * from car where Model= ?");
                ps.setString(1,model);
                rs = ps.executeQuery();
                rs.next();

                plate=rs.getString("Plate");

                ps=con.prepareStatement("INSERT into customer(FName,MName,LName,Age,Phone,Driver_Licence_Number,Kebele_ID_Number,Renting_Purpose) values(?,?,?,?,?,?,?,?);");
                ps.setString(1,fieldFName.getText());
                ps.setString(2,fieldMName.getText());
                ps.setString(3,fieldLName.getText());
                ps.setInt(4,Integer.parseInt(fieldAge.getText()));
                ps.setInt(5,Integer.parseInt(fieldPhone.getText()));
                ps.setString(6,fieldLicence.getText());
                ps.setString(7,fieldKebeleID.getText());
                ps.setString(8,areaPurpose.getText());
                ps.executeUpdate();

                ps=con.prepareStatement("select Amount from car_status where Model=?");
                ps.setString(1,model);
                rs = ps.executeQuery();
                rs.next();
                int amount = rs.getInt("Amount");

                if(amount > 0){
                    ps=con.prepareStatement("update car_status set Amount=Amount-1, Rent_Times=Rent_Times+1 where Model=?");
                    ps.setString(1,model);
                    ps.executeUpdate();
                }

                try {
                    ps=con.prepareStatement("insert into rented_car_list(Plate,Customer_ID,Is_Rented,Date_Of_Rent) " +
                            "values(?,?,?,?)");
                    ps.setString(1,plate);
                    ps.setString(2,fieldKebeleID.getText());
                    ps.setInt(3,1);
                    ps.setDate(4, Date.valueOf(LocalDate.now()));
                    ps.executeUpdate();

                }catch (SQLIntegrityConstraintViolationException e){
                    ps=con.prepareStatement("update rented_car_list set Customer_ID=?,Is_Rented=?,Date_Of_Rent=? where Plate=?" +
                            "values(?,?,?,?)");
                    ps.setString(1,fieldKebeleID.getText());
                    ps.setInt(2,1);
                    ps.setDate(3, Date.valueOf(LocalDate.now()));
                    ps.setString(4,plate);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Rented a Car!");
                alert.showAndWait();
                clearFieldsCust();
            }
            else if (checkIfEmpty() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText(null);
                alert.setContentText("No field Should Be Empty!");
                alert.showAndWait();
            }
        }
            catch(SQLException ex){
                ex.printStackTrace();
        }
    }

    private int checkIfEmpty() {
        if (fieldFName.getText().length() == 0) {
            return 0;
        }
        if (fieldMName.getText().length() == 0) {
            return 0;
        }
        if (fieldLName.getText().length() == 0) {
            return 0;
        }
        if (fieldKebeleID.getText().length() == 0) {
            return 0;
        }
        if (fieldAge.getText().length() == 0) {
            return 0;
        }
        if (fieldPhone.getText().length() == 0) {
            return 0;
        }
        if (fieldLicence.getText().length() == 0) {
            return 0;
        }
        if (areaPurpose.getText().length() == 0) {
            return 0;
        }
        if (fieldDay.getText().length() == 0) {
            return 0;
        }
        if (comboCar.getValue() == null) {
            return 0;
        }
        return 1;
    }
    
    protected void clearFieldsCust(){
        fieldFName.clear();
        fieldMName.clear();
        fieldLName.clear();
        fieldPhone.clear();
        fieldLicence.clear();
        fieldAge.clear();
        fieldKebeleID.clear();
        areaPurpose.clear();
        fieldDay.clear();
        comboCar.setValue(null);
        populateCombo();
    }

    private void populateCombo(){
        comboCar.getItems().clear();
        try{
//            st=con.createStatement();
//            rs=st.executeQuery("select Model from car_status where Amount > 0");

            ps=con.prepareStatement("select Model from car_status where Amount > 0");
            rs=ps.executeQuery();

            while (rs.next()){
                comboCar.getItems().add(rs.getString("Model"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fieldPhone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,String newValue) {
                if (!newValue.matches("\\d*")) {
                    fieldPhone.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        fieldAge.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,String newValue) {
                if (!newValue.matches("\\d*")) {
                    fieldAge.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        fieldDay.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,String newValue) {
                if (!newValue.matches("\\d*")) {
                    fieldAge.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        populateCombo();
    }

}
