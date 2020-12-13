package carrental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CarListController extends SuperClass implements Initializable {

    @FXML
    private ListView listCar;
    @FXML
    private ImageView imageView,imageRate;
    @FXML
    protected Label labelPrice,labelType,labelModel,labelColor,labelYear,labelAmount,labelRating;

    @FXML
    private void displayCar(){
        connect();
        listCar.getItems().clear();
        try {
    //          To add cars that are not rented to the list view
            ps = con.prepareStatement("select Model from car_status where Amount > 0");
            rs = ps.executeQuery();

            while (rs.next()) {
                listCar.getItems().add(rs.getString("Model"));
            }
    //          Display the car selected from the list view
            listCar.setOnMouseClicked(e->{
                String selectedItem = listCar.getSelectionModel().getSelectedItem().toString();
                try {
                    ps=con.prepareStatement("select * from car where Model=?");
                    ps.setString(1,selectedItem);
                    rs=ps.executeQuery();
                    rs.next();
                    String model = rs.getString("Model");
                    String plate = rs.getString("Plate");
                    String picturePath = rs.getString("Picture");

                    File file = new File(picturePath);
                    Image image = new Image("file:///"+file.getAbsolutePath().replace("\\","/"));
                    imageView.setImage(image);

                    labelModel.setText(rs.getString("Model"));
                    labelType.setText(rs.getString("Type"));
                    labelYear.setText(String.valueOf(rs.getInt("Production_Year")));
                    labelPrice.setText(rs.getString("Renting_Price"));
                    ps=con.prepareStatement("select count(Model) as amount from car where Model=?");
                    ps.setString(1,selectedItem);
                    rs=ps.executeQuery();
                    rs.next();
                    labelAmount.setText(String.valueOf(rs.getInt("amount")));
                    if(rating(model)<=5){
                        imageRate.setImage(new Image("file:///"+new File("D:\\Documents\\Final_Projects\\AP\\CarRental\\src\\carrental\\Pictures\\Stars\\1star.png").getAbsolutePath().replace("\\","/")));
                    }else if(rating(model)>=5 && rating(model)<15){
                        imageRate.setImage(new Image("file:///"+new File("D:\\Documents\\Final_Projects\\AP\\CarRental\\src\\carrental\\Pictures\\Stars\\2star.png").getAbsolutePath().replace("\\","/")));
                    }else if(rating(model)>=15 && rating(model)<30){
                        imageRate.setImage(new Image("file:///"+new File("D:\\Documents\\Final_Projects\\AP\\CarRental\\src\\carrental\\Pictures\\Stars\\3star.png").getAbsolutePath().replace("\\","/")));
                    }else if(rating(model)>=30 && rating(model)<45){
                        imageRate.setImage(new Image("file:///"+new File("D:\\Documents\\Final_Projects\\AP\\CarRental\\src\\carrental\\Pictures\\Stars\\4star.png").getAbsolutePath().replace("\\","/")));
                    }else if(rating(model)>=45){
                        imageRate.setImage(new Image("file:///"+new File("D:\\Documents\\Final_Projects\\AP\\CarRental\\src\\carrental\\Pictures\\Stars\\5star.png").getAbsolutePath().replace("\\","/")));
                    }

                    ps=con.prepareStatement("select Color from car where Plate=?");
                    ps.setString(1,plate);
                    rs = ps.executeQuery();
                    rs.next();

                    labelColor.setStyle("-fx-background-color:"+rs.getString(1));

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int rating(String model){
        int rate=0;
        try {
            ps = con.prepareStatement("select Rent_Times from car_status where Model=?");
            ps.setString(1, model);
            rs = ps.executeQuery();
            rs.next();
            rate = rs.getInt("Rent_Times");
        }catch (Exception e){
            e.printStackTrace();
        }
        return rate;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayCar();
    }
}
