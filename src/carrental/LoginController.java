/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController extends SuperClass{

    @FXML
    protected Label labelWarning;
    @FXML
    protected TextField fieldUname;
    @FXML
    protected PasswordField fieldPass;
    @FXML
    protected Button btnLogin;

    @FXML
    private void checker() {
        String pass;
       if(checkEmpty()==1 && connect()){
           try {
               st=con.createStatement();
               String query = "select Passkey from employee where Username='"+fieldUname.getText()+"';";
               rs = st.executeQuery(query);
               if (rs.next()){
                   pass = rs.getString("Passkey");
                   if (pass.equals(fieldPass.getText())) {
                       username=fieldUname.getText();
//                       rmiLookup();
//                       interfaceA.bla();
//                       interfaceA.toHome(btnLogin);
                       toHome();
                   } else {
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("Error!");
                       alert.setHeaderText(null);
                       alert.setContentText("Username and Password Doesn't Match!");
                       alert.showAndWait();
                   }
               }
               else {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Error!");
                   alert.setHeaderText(null);
                   alert.setContentText("User not found!");
                   alert.showAndWait();
               }
               st.close();
           }
           catch (Exception e) {
               e.printStackTrace();
           }
       }
       else if(checkEmpty()==0)  {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error!");
           alert.setHeaderText(null);
           alert.setContentText("No Field Can Be Empty!");
           alert.showAndWait();
       }
}

    private int checkEmpty(){
        if(fieldUname.getText().length()==0){
            return 0;
        }
        if(fieldPass.getText().length()==0){
            return 0;
        }
        return 1;
    }

    
}
