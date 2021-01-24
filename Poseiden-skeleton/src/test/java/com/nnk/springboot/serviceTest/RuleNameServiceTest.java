package com.nnk.springboot.serviceTest;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RuleNameServiceTest {

    @InjectMocks
    private RuleNameServiceImpl ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    @Test
    public void saveRule_returnRule(){

        //arrange
        RuleName ruleName = new RuleName("name", "description", "json", "template", "sql", "sqlPart");
        ruleName.setId(1);

        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);

        //act
        RuleName savedRuleName = ruleNameService.saveRuleName(ruleName);

        //assert
        assertNotNull(savedRuleName);
        assertEquals(ruleName.getId(), savedRuleName.getId(),0);
        verify(ruleNameRepository, times(1)).save(any(RuleName.class));
    }

    @Test
    public void findRuleById_returnId(){

        //arrange
        RuleName ruleName = new RuleName("name", "description", "json", "template", "sql", "sqlPart");
        ruleName.setId(1);

        when(ruleNameRepository.findById(1)).thenReturn(java.util.Optional.of(ruleName));

        //act
        RuleName findRuleNameById = ruleNameService.findRuleNameById(1);

        //assert
        assertEquals(ruleName.getId(), findRuleNameById.getId(),0);

    }


    @Test
    public void findAllRuleNames_returnFindAllRuleNames(){

        //arrange
        RuleName ruleName = new RuleName("name", "description", "json", "template", "sql", "sqlPart");
        ruleName.setId(1);

        //arrange
        RuleName ruleName1 = new RuleName("name1", "description1", "json1", "template1", "sql1", "sqlPart1");
        ruleName1.setId(2);

        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(ruleName, ruleName1));

        //act
        List<RuleName> ruleNames = ruleNameService.findAllRuleName();

        //assert
        assertEquals(2, ruleNames.size());
        assertEquals(1, ruleNames.get(0).getId(),0);
        assertEquals(2, ruleNames.get(1).getId(),0);
        Assert.assertTrue(ruleNames.size() > 0);

    }

    @Test
    public void updateRuleName_updateRuleName(){

        //arrange
        RuleName ruleName = new RuleName("name", "description", "json", "template", "sql", "sqlPart");
        ruleName.setId(1);

        when(ruleNameRepository.findById(1)).thenReturn(java.util.Optional.of(ruleName));

        //act
        ruleNameService.updateRuleName(ruleName);

        //assert
        verify(ruleNameRepository, times(1)).save(any(RuleName.class));

    }

    @Test
    public void deleteRuleName_deleteRuleName(){

        //act
        ruleNameService.deleteRuleName(1);

        //assert
        Optional<RuleName> ruleList = ruleNameRepository.findById(1);
        Assert.assertFalse(ruleList.isPresent());
    }
}
