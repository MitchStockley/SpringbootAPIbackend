package com.testREST.RegisterApp.repository;

import com.testREST.RegisterApp.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Courses, Long> {
//    Courses findByCourse_name(String course_name);
//
//    Courses findByCourse_description(String course_description);
//
//
//    Optional<List<Courses>> findByTitleContaining(String title);
//
//    Optional<List<Courses>> findByDescriptionContaining(String description);
//
//    Optional<List<Courses>> findByCourse_nameContaining(String name);
}
