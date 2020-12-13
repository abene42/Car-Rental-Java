/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EmployeeController extends SuperClass implements Initializable {
    @FXML
    protected Label labelWarning;

    @FXML
    protected TextField fieldFName,fieldLName,fieldMName,
            fieldID,fieldPhone,fieldSalary,
            fieldAge,fieldAddress,fieldDepartment,fieldUsername;

    protected String [] fieldList=new String[9];
    @FXML
    protected ComboBox comboGender,comboDivision;
    
    @FXML
    private void writeEmployeeIntoDB() {
            if (checkIfEmpty() == 1) {
                connect();
                fieldList[0]=fieldFName.getText();
                fieldList[1]=fieldMName.getText();
                fieldList[2]=fieldLName.getText();
                fieldList[3]=fieldID.getText();
                fieldList[4]=fieldPhone.getText();
                fieldList[5]=fieldPhone.getText();
                fieldList[6]=fieldAge.getText();
                fieldList[7]=fieldAddress.getText();
                fieldList[8]=fieldDepartment.getText();
                
//                DBEmployee(fieldList);
                
                clearFieldsEmp();
            } else if (checkIfEmpty() == 0) {
//                labelWarning.setText("No field should be Empty!");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText(null);
                alert.setContentText("No field Should Be Empty!");
                alert.showAndWait();
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
        if (fieldID.getText().length() == 0) {
            return 0;
        }
        if (fieldID.getText().length() == 0) {
            return 0;
        }
        if (fieldPhone.getText().length() == 0) {
            return 0;
        }
        if (fieldSalary.getText().length() == 0) {
            return 0;
        }
        if (fieldAddress.getText().length() == 0) {
            return 0;
        }
        if (fieldDepartment.getText().length() == 0) {
            return 0;
        }
        if (fieldUsername.getText().length() == 0) {
            return 0;
        }
        return 1;
    }
    
    protected void clearFieldsEmp(){
        fieldFName.clear();
        fieldMName.clear();
        fieldLName.clear();
        fieldID.clear();
        fieldPhone.clear();
        fieldSalary.clear();
        fieldAge.clear();
        fieldAddress.clear();
        fieldDepartment.clear();
        fieldUsername.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comboGender.getItems().addAll("Male", "Female");
        comboGender.setValue("Male");
        comboDivision.getItems().addAll("Administratior", "Clerk");
        comboDivision.setValue("Clerk");
        
        fieldPhone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,String newValue) {
                if (!newValue.matches("\\d*")) {
                    fieldPhone.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        fieldSalary.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,String newValue) {
                if (!newValue.matches("\\d*")) {
                    fieldSalary.setText(newValue.replaceAll("[^\\d]", ""));
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
    }

}
