package com.tutorials.practice.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorials.practice.dal.TutorialRepository;
import com.tutorials.practice.domain.Tutorial;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TutorialController {
	
	@Autowired
	TutorialRepository tutorialRepository;
	
	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required=false) String title){
		try {
			List<Tutorial> tutorials = new ArrayList<Tutorial>();
			
			if(title==null) {
				tutorialRepository.findAll().forEach(tutorials::add);
			}
			else {
				tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
			}
			
			if(tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<List<Tutorial>>(tutorials,HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id")int id){
		Optional<Tutorial> tutorialData=tutorialRepository.findById(id);
		
		if(tutorialData.isPresent()) {
			return new ResponseEntity<Tutorial>(tutorialData.get(),HttpStatus.OK) ;
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/tutorials")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial){
		try {
			Tutorial saved_tutorial=tutorialRepository.save(new Tutorial(tutorial.getTitle(),tutorial.getDescription(),false));
			return new ResponseEntity<>(saved_tutorial,HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id")int id,@RequestBody Tutorial tutorial){
		Optional<Tutorial> tutorialData =tutorialRepository.findById(id);
		if(tutorialData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			Tutorial updated_tutorial=tutorialData.get();
			updated_tutorial.setTitle(tutorial.getTitle());
			updated_tutorial.setDescription(tutorial.getDescription());
			updated_tutorial.setPublished(tutorial.isPublished());
			
			return new ResponseEntity<>(tutorialRepository.save(updated_tutorial),HttpStatus.OK); 
		}
	}
	
	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id")int id){
		try {
			tutorialRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/tutorials") 
	public ResponseEntity<HttpStatus> deleteAllTutorial(){
		try {
			tutorialRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/publishedtutorials")
	public ResponseEntity<List<Tutorial>> findByPublished(@RequestParam(required=true) boolean published){
		try {
			List<Tutorial> tutorials=tutorialRepository.findByPublished(published);
			
			if(tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			else {
				return new ResponseEntity<>(tutorials,HttpStatus.OK);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
