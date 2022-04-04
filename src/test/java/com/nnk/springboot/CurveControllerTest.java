package com.nnk.springboot;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exception.BidNotFoundException;
import com.nnk.springboot.exception.CurvePointNotFoundException;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.service.CurvePointService;

@SpringBootTest
@AutoConfigureMockMvc
public class CurveControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	 @Autowired
    private CurvePointService curvePointService;
    
    @Autowired
    private CurvePointRepository curvePointRepository;

    private CurvePoint curvePoint;
    
    @AfterEach
    void deleteUser() {
    	curvePointRepository.deleteAll();
    }
    
    @BeforeEach
    void setUp() throws BidNotFoundException, CurvePointNotFoundException{
    	curvePoint = new CurvePoint("1", 2.0, 5.0);
    	curvePoint = curvePointService.saveCurvePoint(curvePoint);
    }
    
    @Test
     void testListCurvePointController() throws Exception{
    	
    	// List of CurvePoint
    	mockMvc
    	.perform(get("/curvePoint/list")
   			.with(user("user").roles("USER","ADMIN").authorities(new SimpleGrantedAuthority("USER"))))
   			.andExpect(status().isOk())
   			.andExpect(view().name("curvePoint/list"));
    }
    
    @Test
    void testCurvePointForm() throws Exception {
    	// Form add
    	mockMvc
    			.perform(get("/curvePoint/add")
    			 .with(user("user").roles("USER","ADMIN").authorities(new SimpleGrantedAuthority("USER"))))
    			.andExpect(status().isOk())
    			.andExpect(view().name("curvePoint/add"));
    }
    
    @Test
    void testAddcurvePoint() throws Exception {
    	
    	//Save CurvePoint
    	mockMvc
		    	.perform(post("/curvePoint/validate")
			   		.with(user("user").roles("USER","ADMIN").authorities(new SimpleGrantedAuthority("USER")))
			   		.param("curve","2")
                    .param("term","2.0")
                    .param("value","5.0")
                    .with(csrf()))
		    		.andExpect(redirectedUrl("/curvePoint/list"));
    }
    
    @Test
    void testCurvePointUpdateForm() throws Exception {
    	CurvePoint curve = curvePointService.getCurvePointById(curvePoint.getId());
    	
	    //Form update
    	mockMvc
				.perform(get("/curvePoint/update/{id}", curve.getId())
				.with(user("user").roles("USER","ADMIN").authorities(new SimpleGrantedAuthority("USER"))))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/update"));
				
    }
    
    @Test
    void testUpdateCurvePoint() throws Exception {
    	CurvePoint curve = curvePointService.getCurvePointById(curvePoint.getId());

    	//Update bidList
    	mockMvc
    			.perform(post("/curvePoint/update/{id}",curve.getId())
    			.with(user("user").roles("USER","ADMIN").authorities(new SimpleGrantedAuthority("USER")))
		   		.param("curve","3")
                .param("term","2.0")
                .param("value","5.0")
                .with(csrf()))
	    		.andExpect(redirectedUrl("/curvePoint/list"));
    }
    
    @Test
    void testDeleteCurvePoint() throws Exception{
    	CurvePoint curve = curvePointService.getCurvePointById(curvePoint.getId());
    	
    	//Delete bidList
    	mockMvc
				.perform(get("/curvePoint/delete/{id}",curve.getId())
				.with(user("user").roles("USER","ADMIN").authorities(new SimpleGrantedAuthority("ADMIN")))
		        .with(csrf()))
				.andExpect(redirectedUrl("/curvePoint/list"));
    }

}
