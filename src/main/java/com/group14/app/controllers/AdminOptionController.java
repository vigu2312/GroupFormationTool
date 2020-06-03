package com.group14.app.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.group14.app.models.CourseRoleMapper;
import com.group14.app.models.Courses;
import com.group14.app.repositories.CourseRepository;
import com.group14.app.repositories.CourseRoleMapperRepository;
import com.group14.app.services.CourseService;

@Controller
public class AdminOptionController {
	
		CourseRepository course = new CourseRepository();
		CourseRoleMapperRepository courseMapper = new CourseRoleMapperRepository();
		@Autowired
		CourseService courseService;
		
		@GetMapping("/createCourse")
		public String createCourses(Model model)
		{
			model.addAttribute("createCourse", new Courses());
			return "createCourse";
			
		}
		
		@PostMapping("/createCourse")
		public String createCourses(@ModelAttribute Courses courses) {
		
		courseService.addCourse(courses);
		return "redirect:/allCourse";	
	}
		
		@GetMapping("/deleteCourse")
		public String deleteCourses(Model model) 
		{ 
			try {
				List<Courses> courseL = course.list();
				
				model.addAttribute("allCourse", courseL);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
			model.addAttribute("deleteCourse", new Courses());
			return "deleteCourse";
			
			
		}
		
		@PostMapping("/deleteCourse")
		public String deleteCourses(@ModelAttribute Courses allCourse) {
		
		courseService.deleteCourse(allCourse);
		return "redirect:/allCourse";	
	}

		@GetMapping("/assignI")
		public String assignInstructor(Model model)
		{
			try {
				List<CourseRoleMapper> courseL = courseMapper.list();
				
				model.addAttribute("viewI", courseL);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			try {
				List<Courses> courseL = course.list();
				
				model.addAttribute("allCourse", courseL);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
			model.addAttribute("assignI", new CourseRoleMapper());
			return "assignI";
			
		}
		
		@PostMapping("/assignI")
		public String assignInstructor(@ModelAttribute CourseRoleMapper assignI) {
		
		courseService.addCourseInstructor(assignI.getUser_id(), assignI.getCourse_id());;
		return "redirect:/viewI";	
	}
		
		@GetMapping("/viewI")
		public String viewI(Model model)
		{
			try {
				List<CourseRoleMapper> courseL = courseMapper.list();
				
				model.addAttribute("viewI", courseL);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return "viewI";
			
		}
		
		@PostMapping("/viewI")
		public String viewI(@ModelAttribute CourseRoleMapper viewI) {
		
		return "viewI";	
		}
		
		
	}

