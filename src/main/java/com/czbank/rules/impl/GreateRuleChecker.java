package com.czbank.rules.impl;

import com.czbank.rules.Rule;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Foy Lian
 * Date: 4/29/2021
 * Time: 2:03 PM
 */
public class GreateRuleChecker extends AbstractRuleChecker {
    @Override
    public boolean validateFailue(BigDecimal firstItemVal, BigDecimal sumOrItemValue) {
        if (firstItemVal.compareTo(sumOrItemValue) < 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean support(Rule rule) {
        if ("大于".equals(rule.getValidateMethod())) {
            return true;
        }
        return false;
    }
}
