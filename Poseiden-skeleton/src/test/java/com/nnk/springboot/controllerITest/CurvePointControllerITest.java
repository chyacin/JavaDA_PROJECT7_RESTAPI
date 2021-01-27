package com.nnk.springboot.controllerITest;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveServiceImpl;
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
public class CurvePointControllerITest {

    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private CurveServiceImpl curveService;

    @Autowired
    WebApplicationContext webContext;

    @Before
    public void setupMockMvc() {


        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }


    @Test
    @WithUserDetails("adminOne")
    public void home()throws Exception{

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("adminOne")
    public void addCurveForm()throws Exception {

        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("adminOne")
    public void validate()throws Exception {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setTerm(5.00);
        curvePoint.setValue(1000.00);

        when(curveService.saveCurvePoint(curvePoint)).thenReturn(curvePoint);

        mockMvc.perform(post("/curvePoint/validate")
                .param("term", curvePoint.getCurveId().toString())
                .param("curveId", curvePoint.getCurveId().toString())
                .param("value",curvePoint.getValue().toString()))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().size(1));

    }

    @Test
    @WithUserDetails("adminOne")
    public void showUpdateForm()throws Exception {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(10);
        curvePoint.setCurveId(1);
        curvePoint.setTerm(5.00);
        curvePoint.setValue(1000.00);

        when(curveService.findCurvePointById(10)).thenReturn(curvePoint);

        mockMvc.perform(get("/curvePoint/update/{id}", "10"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(status().is2xxSuccessful());


    }

    @Test
    @WithUserDetails("adminOne")
    public void updateCurve()throws Exception {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(10);
        curvePoint.setCurveId(5);
        curvePoint.setTerm(4.00);
        curvePoint.setValue(5000.00);

        when(curveService.findCurvePointById(10)).thenReturn(curvePoint);


        mockMvc.perform(post("/curvePoint/update/{id}", "10")
                .param("term", curvePoint.getCurveId().toString())
                .param("curveId", curvePoint.getCurveId().toString())
                .param("value",curvePoint.getValue().toString()))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().size(1));

    }

    @Test
    @WithUserDetails("adminOne")
    public void deleteCurve()throws Exception {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(2);
        curvePoint.setTerm(4.00);
        curvePoint.setValue(5000.00);

        when(curveService.findCurvePointById(curvePoint.getId())).thenReturn(curvePoint);

        mockMvc.perform(get("/curvePoint/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection());
    }
}
