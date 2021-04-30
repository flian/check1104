package com.czbank.rules;

import com.czbank.rules.impl.*;
import javafx.scene.control.TextArea;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Foy Lian
 * Date: 4/28/2021
 * Time: 3:14 PM
 */
public class RuleManager {
    private List<RuleChecker> checkers = new ArrayList<RuleChecker>();

    public RuleManager() {
        checkers.add(new EqualRuleChecker());
        checkers.add(new GreateRuleChecker());
        checkers.add(new LessRuleChecker());
        checkers.add(new GreateThanEqualRuleChecker());
        checkers.add(new LessThanEqualRuleChecker());
    }

    public void validateRule(List<Rule> rules, String excelFodler, TextArea textArea) {
        for (Rule rule : rules) {
            for (RuleChecker ruleChecker : checkers) {
                if (ruleChecker.support(rule)) {
                    ruleChecker.validate(rule, excelFodler,textArea);
                }
            }
        }
    }

    public List<Rule> splitRuleFromExcel(String ruleFile) throws IOException {
        List<Rule> result = new ArrayList<Rule>();
        Workbook workbook = WorkbookFactory.create(new File(ruleFile));
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        int row = 0;
        while (row <= lastRowNum) {
            Rule rule = new Rule();
            rule.setRuleName(sheet.getRow(row).getCell(1).getStringCellValue());
            rule.setValidateMethod(sheet.getRow(row + 1).getCell(1).getStringCellValue());
            for (int i = row + 3; ; i++) {
                Row ruleItemRow = sheet.getRow(i);
                if (ruleItemRow == null) {
                    row = i + 1;
                    result.add(rule);
                    break;
                }
                RuleItem ruleItem = new RuleItem();
                ruleItem.setType(ruleItemRow.getCell(0).getStringCellValue());
                ruleItem.setTableName(ruleItemRow.getCell(1).getStringCellValue());
                ruleItem.setCoordinate(ruleItemRow.getCell(2).getStringCellValue());
                Cell ruleItemGroupCell = ruleItemRow.getCell(3);
                if (ruleItemGroupCell != null) {
                    ruleItem.setGroup(new Integer((int) ruleItemGroupCell.getNumericCellValue()));
                }
                rule.getRuleItemList().add(ruleItem);
            }
        }
        return result;
    }
}
