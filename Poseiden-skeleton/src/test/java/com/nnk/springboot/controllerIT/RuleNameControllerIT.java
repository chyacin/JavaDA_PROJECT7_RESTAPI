package com.nnk.springboot.controllerIT;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameServiceImpl;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class RuleNameControllerIT {

    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    RuleNameServiceImpl ruleNameService;

    @Autowired
    WebApplicationContext webContext;

    @Before
    public void setupMockMvc() {


        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }


    @Test
    @WithUserDetails("admin")
    public void home()throws Exception{

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin")
    public void addRuleForm()throws Exception {

        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin")
    public void validate()throws Exception {

        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("name");
        ruleName.setTemplate("template");
        ruleName.setJson("json");
        ruleName.setDescription("description");
        ruleName.setSqlStr("sqlStr");
        ruleName.setSqlPart("sqlPart");

        when(ruleNameService.saveRuleName(ruleName)).thenReturn(ruleName);

        mockMvc.perform(post("/ruleName/validate")
                .param("name", ruleName.getName())
                .param("template", ruleName.getTemplate())
                .param("json", ruleName.getJson())
                .param("description", ruleName.getDescription())
                .param("sqlStr", ruleName.getSqlStr())
                .param("sqlPart",ruleName.getSqlPart()))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(model().size(1));

    }

    @Test
    @WithUserDetails("admin")
    public void showUpdateForm()throws Exception {

        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("name");
        ruleName.setTemplate("template");
        ruleName.setJson("json");
        ruleName.setDescription("description");
        ruleName.setSqlStr("sqlStr");
        ruleName.setSqlPart("sqlPart");

        when(ruleNameService.findRuleNameById(1)).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/update/{id}", "1"))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin")
    public void updateRule()throws Exception{

        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("name");
        ruleName.setTemplate("template");
        ruleName.setJson("json");
        ruleName.setDescription("description");
        ruleName.setSqlStr("sqlStr");
        ruleName.setSqlPart("sqlPart");

        when(ruleNameService.findRuleNameById(1)).thenReturn(ruleName);

        mockMvc.perform(post("/ruleName/update/{id}", "1")
                .param("name", ruleName.getName())
                .param("template", ruleName.getTemplate())
                .param("json", ruleName.getJson())
                .param("description", ruleName.getDescription())
                .param("sqlStr", ruleName.getSqlStr())
                .param("sqlPart",ruleName.getSqlPart()))
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(model().size(1));

    }

    @Test
    @WithUserDetails("admin")
    public void deleteRule()throws Exception {


        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("name");
        ruleName.setTemplate("template");
        ruleName.setJson("json");
        ruleName.setDescription("description");
        ruleName.setSqlStr("sqlStr");
        ruleName.setSqlPart("sqlPart");


        when(ruleNameService.findRuleNameById(1)).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection());

    }

}
