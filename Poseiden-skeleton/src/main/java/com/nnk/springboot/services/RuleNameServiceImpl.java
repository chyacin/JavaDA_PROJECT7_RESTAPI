package com.nnk.springboot.services;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameServiceImpl implements RuleNameService{

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * The service method which saves the rule name to the database
     * @param ruleName the ruleName to be saved
     * @return the save rule name object
     */
    @Override
    public RuleName saveRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    /**
     * The service method which finds the respective rule name by their corresponding id
     * @param id the id of the rule name
     * @return the found rule name
     */
    @Override
    public RuleName findRuleNameById(int id) {
        Optional<RuleName> ruleNameOptional = ruleNameRepository.findById(id);
        if(ruleNameOptional.isPresent()){
            RuleName ruleName = ruleNameOptional.get();
            return ruleName;
        }
        return null;
    }

    /**
     * The service method which finds and returns all the rule names in a list
     * @return the list of all the rule names
     */
    @Override
    public List<RuleName> findAllRuleName() {
        List<RuleName> ruleNames = ruleNameRepository.findAll();
        return ruleNames;
    }

    /**
     * The service method which updates the user's rule name
     * @param ruleName the rule name to be updated
     */
    @Override
    public void updateRuleName(RuleName ruleName) {
        RuleName updateRuleName = findRuleNameById(ruleName.getId());
        if(updateRuleName != null){
            updateRuleName.setName(ruleName.getName());
            updateRuleName.setDescription(ruleName.getDescription());
            updateRuleName.setJson(ruleName.getJson());
            updateRuleName.setSqlPart(ruleName.getSqlPart());
            updateRuleName.setSqlStr(ruleName.getSqlStr());
            updateRuleName.setTemplate(ruleName.getTemplate());
            ruleNameRepository.save(updateRuleName);
        }

    }

    /**
     * The service method which deletes the user's rule name
     * @param id the id of the rule name
     */
    @Override
    public void deleteRuleName(int id) {
       ruleNameRepository.deleteById(id);
    }
}
