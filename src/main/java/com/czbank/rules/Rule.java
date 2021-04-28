package com.czbank.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Foy Lian
 * Date: 4/28/2021
 * Time: 3:09 PM
 */
public class Rule {
    private String ruleName;
    private String validateMethod;
    private List<RuleItem> ruleItemList = new ArrayList<>();

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getValidateMethod() {
        return validateMethod;
    }

    public void setValidateMethod(String validateMethod) {
        this.validateMethod = validateMethod;
    }

    public List<RuleItem> getRuleItemList() {
        return ruleItemList;
    }

    public void setRuleItemList(List<RuleItem> ruleItemList) {
        this.ruleItemList = ruleItemList;
    }
}
