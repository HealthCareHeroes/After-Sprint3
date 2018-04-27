package edu.uncc.ssdi.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uncc.ssdi.model.Permission;
import edu.uncc.ssdi.service.PermService;

public class PermissionControllerTest {

	private MockMvc mockMvc;

    @InjectMocks
    private PermissionController perm_Controller = new PermissionController();
    

    @Mock
    private PermService permService;
   
    @Before
	public void setup() {
	
		
		MockitoAnnotations.initMocks(this);
	
		this.mockMvc = MockMvcBuilders.standaloneSetup(perm_Controller).build();
	}
	
	  public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	        return mapper.writeValueAsBytes(object);
	    }

		public static String asJsonString(final Object obj) {
			try {
				return new ObjectMapper().writeValueAsString(obj);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		
	@Test
	public void givePermission() throws Exception{
		
		Permission permObject = new Permission();
		permObject.setId("10011");
		permObject.setAccessByUserId((long)10006);
		permObject.setStatus(4);
		
		Mockito.when(permService.grantPermission(permObject)).thenReturn(1);
		
		 mockMvc.perform(put("/givePermission/")
			 .contentType(MediaType.APPLICATION_JSON)
			 .content(asJsonString(permObject)))
	 		.andExpect(status().isOk());
			Mockito.verify(permService, times(1)).grantPermission(anyObject());
	 		verifyNoMoreInteractions(permService);
	}
	
	@Test
	public void  getAllRequestTest() throws Exception{
		
		Long id = (long) 10011;
		List<Permission> expected = new ArrayList<Permission>();
		
		expected.add(new Permission(id.toString(), 10006, 4));
		expected.add(new Permission(id.toString(), 10004, 4));
	
		when(permService.getAllRequests(id)).thenReturn(expected);
		
		mockMvc.perform(get("/permissionRequests/{id}",id))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].id", is(id.toString()))).andExpect(jsonPath("$[0].accessByUserId", is(10006)))
				.andExpect(jsonPath("$[0].status", is(4)))
				.andExpect(jsonPath("$[1].id", is(id.toString()))).andExpect(jsonPath("$[1].accessByUserId", is(10004)))
				.andExpect(jsonPath("$[1].status", is(4)));
		
		verify(permService, times(1)).getAllRequests(id);
		verifyNoMoreInteractions(permService);
	}
}
