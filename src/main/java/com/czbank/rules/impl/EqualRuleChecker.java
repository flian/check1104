package com.czbank.rules.impl;

import com.czbank.rules.Rule;
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
public class EqualRuleChecker extends AbstractRuleChecker {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean support(Rule rule) {
        if ("相等".equals(rule.getValidateMethod())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateFailue(BigDecimal firstItemVal, BigDecimal sumOrItemValue) {
        if (!firstItemVal.equals(sumOrItemValue)) {
            return true;
        }
        return false;
    }


}
