package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;

import java.util.List;

public interface RuleNameService {

    public RuleName saveRuleName(RuleName ruleName);
    public RuleName findRuleNameById(int id);
    List<RuleName> findAllRuleName();
    public void updateRuleName(RuleName ruleName);
    public void deleteRuleName(int id);
}
