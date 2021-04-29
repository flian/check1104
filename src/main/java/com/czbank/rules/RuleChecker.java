package com.czbank.rules;

import javafx.scene.control.TextArea;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;

import java.io.File;
import java.io.IOException;

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

    default double getValue(String excelFolder, String excelFileName, String coordinate) {
        File folder = new File(excelFolder);
        if (!(folder.exists() && folder.isDirectory())) {
            throw new RuntimeException("目录不存在");
        }
        File[] files = folder.listFiles(((dir, name) -> {
            return name.endsWith(excelFileName+".xls") || name.endsWith(excelFileName+".xlsx");
        }));
        if (files.length != 1) {
            throw new RuntimeException("找不到匹配的文件" + excelFileName);
        }
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(files[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheetAt(0);
        CellAddress address = new CellAddress(coordinate);
        return sheet.getRow(address.getRow()).getCell(address.getColumn()).getNumericCellValue();
    }
}
