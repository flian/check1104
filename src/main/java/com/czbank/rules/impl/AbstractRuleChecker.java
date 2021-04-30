package com.czbank.rules.impl;

import com.czbank.rules.Rule;
import com.czbank.rules.RuleChecker;
import com.czbank.rules.RuleItem;
import javafx.scene.control.TextArea;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Foy Lian
 * Date: 4/29/2021
 * Time: 1:49 PM
 */
public abstract  class AbstractRuleChecker implements RuleChecker {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public abstract boolean validateFailue(BigDecimal firstItemVal, BigDecimal sumOrItemValue);

    @Override
    public void validate(Rule rule, String excelFolder, TextArea textArea) {
        if (containSumEqual(rule)) {
            // 和相等
            RuleItem firstItem = rule.getRuleItemList().get(0);
            boolean validateSuccess = true;
            BigDecimal firstItemVal = getValue(excelFolder, firstItem.getTableName(), firstItem.getCoordinate());
            Map<Integer, List<RuleItem>> itemGroup = new HashMap<Integer, List<RuleItem>>();
            for (int i = 1; i < rule.getRuleItemList().size(); i++) {
                RuleItem item = rule.getRuleItemList().get(i);
                if (itemGroup.get(item.getGroup()) == null) {
                    itemGroup.put(item.getGroup(), new ArrayList<RuleItem>());
                }
                itemGroup.get(item.getGroup()).add(item);
            }

            for (Integer group : itemGroup.keySet()) {
                BigDecimal sum = BigDecimal.ZERO;
                for (RuleItem item : itemGroup.get(group)) {
                    sum = sum.add(getValue(excelFolder, item.getTableName(), item.getCoordinate()));
                }
                if (validateFailue(firstItemVal,sum)) {
                    validateSuccess = false;
                    String msg = rule.getRuleName() + "校验失败.分组" + group + "\n";
                    logger.error(msg);
                    textArea.appendText(msg);
                }
            }
            if (validateSuccess) {
                logger.info(rule.getRuleName() + "校验成功.\n");
                textArea.appendText(rule.getRuleName() + "校验成功.\n");
            }

        } else {
            //单纯相等
            RuleItem firstItem = rule.getRuleItemList().get(0);
            boolean validateSuccess = true;
            BigDecimal firstItemVal = getValue(excelFolder, firstItem.getTableName(), firstItem.getCoordinate());
            for (int i = 1; i < rule.getRuleItemList().size(); i++) {
                RuleItem item = rule.getRuleItemList().get(i);
                BigDecimal itemValue = getValue(excelFolder, item.getTableName(), item.getCoordinate());
                if (validateFailue(firstItemVal,itemValue)) {
                    validateSuccess = false;
                    String msg = rule.getRuleName() + "校验失败." + item.getTableName() + "," + item.getCoordinate() + "\n";
                    logger.error(msg);
                    textArea.appendText(msg);
                }
            }
            if (validateSuccess) {
                logger.info(rule.getRuleName() + "校验成功.\n");
                textArea.appendText(rule.getRuleName() + "校验成功.\n");
            }
        }
    }

    private boolean containSumEqual(Rule rule) {
        for (RuleItem ruleItem : rule.getRuleItemList()) {
            if ("合计项".equals(ruleItem.getType())) {
                return true;
            }
        }
        return false;
    }

     BigDecimal getValue(String excelFolder, final String excelFileName, String coordinate) {
        File folder = new File(excelFolder);
        if (!(folder.exists() && folder.isDirectory())) {
            throw new RuntimeException("目录不存在");
        }
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(excelFileName.trim() + ".xls") || name.endsWith(excelFileName.trim() + ".xlsx");
            }
        });

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
        Cell cell = sheet.getRow(address.getRow()).getCell(address.getColumn());
        cell.setCellType(CellType.STRING);
        return new BigDecimal(cell.getStringCellValue());
    }
}
