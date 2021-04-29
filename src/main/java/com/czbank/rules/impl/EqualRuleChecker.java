package com.czbank.rules.impl;

import com.czbank.rules.Rule;
import com.czbank.rules.RuleChecker;
import com.czbank.rules.RuleItem;
import javafx.scene.control.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Time: 9:22 AM
 */
public class EqualRuleChecker implements RuleChecker {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean support(Rule rule) {
        if ("相等".equals(rule.getValidateMethod())) {
            return true;
        }
        return false;
    }

    @Override
    public void validate(Rule rule, String excelFolder, TextArea textArea) {
        if (containSumEqual(rule)) {
            // 和相等
            RuleItem firstItem = rule.getRuleItemList().get(0);
            boolean validateSuccess = true;
            BigDecimal firstItemVal = getValue(excelFolder, firstItem.getTableName(), firstItem.getCoordinate());
            Map<Integer, List<RuleItem>> itemGroup = new HashMap<>();
            for (int i = 1; i < rule.getRuleItemList().size(); i++) {
                RuleItem item = rule.getRuleItemList().get(i);
                if (itemGroup.get(item.getGroup()) == null) {
                    itemGroup.put(item.getGroup(), new ArrayList<>());
                }
                itemGroup.get(item.getGroup()).add(item);
            }

            for (Integer group : itemGroup.keySet()) {
                BigDecimal sum = BigDecimal.ZERO;
                for (RuleItem item : itemGroup.get(group)) {
                    sum = sum.add(getValue(excelFolder, item.getTableName(), item.getCoordinate()));
                }
                if (!firstItemVal.equals(sum)) {
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
                if (!itemValue.equals(firstItemVal)) {
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
}
