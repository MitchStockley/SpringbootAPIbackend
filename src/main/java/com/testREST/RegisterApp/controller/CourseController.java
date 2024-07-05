package com.testREST.RegisterApp.controller;

import com.testREST.RegisterApp.model.Courses;
import com.testREST.RegisterApp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    //get all courses
    @RequestMapping("/courses")
    public ResponseEntity<List<Courses>> getAllCourses() {
        try {
            List<Courses> courses = new ArrayList<Courses>();
            courseRepository.findAll().forEach(courses::add);

            if (courses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //find course by id
    @RequestMapping("/courses/{id}")
    public ResponseEntity<Courses> getCourseById(@PathVariable("id") Long id) {

        try {
            Optional<Courses> coursedata = courseRepository.findById(id);
            if (coursedata.isPresent()) {
                return new ResponseEntity<>(coursedata.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //create a new course
    @PostMapping("/courses")
    public ResponseEntity<Courses> createCourse(@RequestBody Courses course) {
        try {
            Courses _course = courseRepository.save(new Courses(course.getCourse_name(), course.getCourse_description()));
            return new ResponseEntity<>(_course, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    //update a course
    @PutMapping("/courses/{id}")
    public ResponseEntity<Courses> updateCourse(@PathVariable("id") Long id, @RequestBody Courses course) {
        Optional<Courses> courseData = courseRepository.findById(id);
        if (courseData.isPresent()) {
            Courses _course = courseData.get();
            _course.setCourse_name(course.getCourse_name());
            _course.setCourse_description(course.getCourse_description());
            return new ResponseEntity<>(courseRepository.save(_course), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //delete a course
    @DeleteMapping("/courses/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("id") Long id) {
        try {
            courseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
