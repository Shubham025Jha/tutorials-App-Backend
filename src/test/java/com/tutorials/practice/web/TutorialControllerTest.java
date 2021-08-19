package com.tutorials.practice.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorials.practice.dal.TutorialRepository;
import com.tutorials.practice.domain.Tutorial;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers= {TutorialController.class})
class TutorialControllerTest {
   
	@MockBean
	TutorialRepository testRepo;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void TestgetAllTutorialsWhenTitleNotProvided() throws Exception{
		
		Tutorial t1=new Tutorial("xyz","XYZ",true);
		t1.setId(1);
		Tutorial t2=new Tutorial("abc","ABC",false);
		t2.setId(2);
		
		List<Tutorial>list=new ArrayList<Tutorial>();
		list.add(t1);
		list.add(t2);
		
		Mockito.when(testRepo.findAll()).thenReturn(list);
		
		mockMvc.
		      perform(MockMvcRequestBuilders.get("/api/tutorials").contentType(MediaType.APPLICATION_JSON)
		    		  .accept(MediaType.APPLICATION_JSON)).
		    andExpect(MockMvcResultMatchers.status().isOk());
	}
	 
	@Test
	void TestgetAllTutorialsWhenTitleProvided() throws Exception{
		
		Tutorial t1=new Tutorial("xyz","XYZ",true);
		t1.setId(1);
		
		List<Tutorial>list=new ArrayList<Tutorial>();
		list.add(t1);
		
		
		Mockito.when(testRepo.findByTitleContaining("xyz")).thenReturn(list);
		
		mockMvc.
		      perform(MockMvcRequestBuilders.get("/api/tutorials?title=xyz")
		    		  .contentType(MediaType.APPLICATION_JSON)
		    		  .accept(MediaType.APPLICATION_JSON)).
		    andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void TestgetAllTutorialsWithWrongTitle()throws Exception{
	
		Mockito.when(testRepo.findByTitleContaining("xyz")).thenReturn(null);
		
		mockMvc.
		      perform(MockMvcRequestBuilders.get("/api/tutorials?title=xyz")
		    		  .contentType(MediaType.APPLICATION_JSON)
		    		  .accept(MediaType.APPLICATION_JSON)).
		    andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}
	
	@Test
	void TestCreateTutorial() throws Exception{
		Tutorial t1=new Tutorial("xyz","XYZ",true);
		t1.setId(1);
		
		Mockito.when(testRepo.save(t1)).thenReturn(t1);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/tutorials").content(asJsonString(t1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Test
	void TestgetTutorialByIdIfIdPresent() throws Exception{
		int id=5;
		Tutorial t2=new Tutorial("xyz","XYZ",true);
		t2.setId(id);
		
		java.util.Optional<Tutorial> t1= Optional.of(t2);
		
		
		Mockito.when(testRepo.findById(id)).thenReturn(t1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials/5")).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void TestgetTutorialByIdIfIdNotPresent() throws Exception{
		int id=5;
		
		
		java.util.Optional<Tutorial> t1= Optional.empty();
		
		
		Mockito.when(testRepo.findById(id)).thenReturn(t1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials/5")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void testDeleteByIdForValidId() throws Exception{
		int id=1;
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/tutorials/{id}",id))
		              .andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	

	
	@Test
	void testDeleteAll()throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/tutorials"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void testFindByPublished() throws Exception {
		Tutorial t1=new Tutorial("xyz","XYZ",true);
		t1.setId(1);
		
		List<Tutorial>list=new ArrayList<Tutorial>();
		list.add(t1);
		
		Mockito.when(testRepo.findByPublished(true)).thenReturn(list);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/publishedtutorials?published=true"))
        .andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
}
