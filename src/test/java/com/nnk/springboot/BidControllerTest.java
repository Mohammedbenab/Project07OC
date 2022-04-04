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
import com.nnk.springboot.exception.BidNotFoundException;
import com.nnk.springboot.repository.BidListRepository;
import com.nnk.springboot.service.BidListService;

@SpringBootTest
@AutoConfigureMockMvc
public class BidControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	 @Autowired
    private BidListService bidListService;
    
    @Autowired
    private BidListRepository bidListRepository;

    private BidList bidList;
    
    @AfterEach
    void deleteUser() {
    	bidListRepository.deleteAll();
    }
    
    @BeforeEach
    void setUp() throws BidNotFoundException{
    	bidList = new BidList("254123654", "type4", 2.0);
    	bidListService.saveBidList(bidList);
    }
    
    @Test
     void testListBidController() throws Exception{
    	
    	// List of BidList
    	mockMvc
    	.perform(get("/bidList/list")
   			.with(user("user").roles("USER","ADMIN").authorities(new SimpleGrantedAuthority("USER"))))
   			.andExpect(status().isOk())
   			.andExpect(view().name("bidList/list"));
    }
    
    @Test
    void testBidForm() throws Exception {
    	// Form add
    	mockMvc
    			.perform(get("/bidList/add")
    			 .with(user("user").roles("USER","ADMIN").authorities(new SimpleGrantedAuthority("USER"))))
    			.andExpect(status().isOk())
    			.andExpect(view().name("bidList/add"));
    }
    
    @Test
    void testAddBid() throws Exception {
    	
    	//Save BidList
    	mockMvc
		    	.perform(post("/bidList/validate")
			   		.with(user("user").roles("USER","ADMIN").authorities(new SimpleGrantedAuthority("USER")))
			   		.param("account","87542132")
                    .param("type","type2")
                    .param("bidQuantity","5.0")
                    .with(csrf()))
		    		.andExpect(redirectedUrl("/bidList/list"));
    }
    
    @Test
    void testBidUpdateForm() throws Exception {
    	BidList bid = bidListService.getBidListByAccount(bidList.getAccount());
    	
	    //Form update
    	mockMvc
				.perform(get("/bidList/update/{id}", bid.getId())
				.with(user("user").roles("USER","ADMIN").authorities(new SimpleGrantedAuthority("USER"))))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/update"));
				
    }
    
    @Test
    void testUpdateBid() throws Exception {
    	BidList bid = bidListService.getBidListByAccount(bidList.getAccount());

    	//Update bidList
    	mockMvc
    			.perform(post("/bidList/update/{id}",bid.getId())
    			.with(user("user").roles("USER","ADMIN").authorities(new SimpleGrantedAuthority("USER")))
		   		.param("account","87542132875465")
                .param("type","type2")
                .param("bidQuantity","6.0")
                .with(csrf()))
	    		.andExpect(redirectedUrl("/bidList/list"));
    }
    
    @Test
    void testDeleteBid() throws Exception{
    	BidList bid = bidListService.getBidListByAccount(bidList.getAccount());
    	
    	//Delete bidList
    	mockMvc
				.perform(get("/bidList/delete/{id}",bid.getId())
				.with(user("user").roles("USER","ADMIN").authorities(new SimpleGrantedAuthority("ADMIN")))
		        .with(csrf()))
				.andExpect(redirectedUrl("/bidList/list"));
    }
}
