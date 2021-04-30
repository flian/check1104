package com.czbank.rules;

import javafx.scene.control.TextArea;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Foy Lian
 * Date: 4/29/2021
 * Time: 9:19 AM
 */
public interface RuleChecker {

    public boolean support(Rule rule);

    public void validate(Rule rule, String excelFolder, TextArea textArea);

}
