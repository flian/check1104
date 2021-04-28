package com.czbank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @FXML
    private Parent root;

    @FXML
    private TextField rules;

    @FXML
    private TextField sourceDic;


    private DirectoryChooser chooser;

    private FileChooser fileChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(chooser == null){
            chooser = new DirectoryChooser();
        }
        if(fileChooser == null){
            fileChooser=new FileChooser();
        }
    }

    @FXML
    public void chooseRules(ActionEvent event){
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            rules.setText(file.getAbsolutePath());
        }
    }
    @FXML
    public void chooseSourceDic(ActionEvent event){
        File file = chooser.showDialog(root.getScene().getWindow());
        if (file != null) {
            sourceDic.setText(file.getAbsolutePath());
        }
    }

    @FXML
    public void run(ActionEvent event){
        logger.info("start process rules");
    }
}
