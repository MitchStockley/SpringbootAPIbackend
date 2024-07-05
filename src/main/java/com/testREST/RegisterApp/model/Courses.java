package com.testREST.RegisterApp.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "course_name", nullable = false, unique = true)
    private String course_name;
    @Column(name = "course_description", nullable = false)
    private String course_description;

//    @ManyToMany(mappedBy = "enrolledCourses")
//    private Set<User> enrolledUsers = new HashSet<>();


    public Courses() {
    }

    public Courses(String course_name, String course_description) {
        this.course_name = course_name;
        this.course_description = course_description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_description() {
        return course_description;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }


    @Override
    public String toString() {
        return "Courses{" + "id=" + id + ", course_name=" + course_name + ", course_description=" + course_description + '}';
    }
}
