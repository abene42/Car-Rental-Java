/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;

/**
 *
 * @author Abene
 */
public class HomeController extends SuperClass implements Initializable {

    @FXML
    private MenuBar bar;
    @FXML
    Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        pane.heightProperty().addListener(new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                double height=(double) newValue;
//                pane.setPrefHeight(height);
//            }
//        });
//        pane.widthProperty().addListener(new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                double width=(double) newValue;
//                pane.setPrefWidth(width);
//            }
//        });
    }

}
