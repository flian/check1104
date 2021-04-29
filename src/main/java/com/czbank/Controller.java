package com.czbank;

import com.czbank.rules.Rule;
import com.czbank.rules.RuleManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @FXML
    private Parent root;

    @FXML
    private TextField rules;

    @FXML
    private TextField sourceDic;

    @FXML
    private TextArea outputLog;

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
        outputLog.setEditable(false);
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
    public void run(ActionEvent event) throws IOException, InvalidFormatException {
        RuleManager ruleManager = new RuleManager();
        List<Rule> excelRules = ruleManager.splitRuleFromExcel(rules.getText());
        ruleManager.validateRule(excelRules,sourceDic.getText(),outputLog);

    }
    @FXML
    public void clean(ActionEvent event){
        outputLog.clear();
    }
}
