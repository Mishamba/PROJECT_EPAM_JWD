package com.mishamba.project.model;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthday;
    private final ArrayList<String> teacherSkills;
    private String role;

    public User(Integer id, String firstName, String lastName, String email,
                Date birthday, ArrayList<String> teacherSkills, String role) {
        if (role.equals("teacher")) {
            this.teacherSkills = teacherSkills;
        } else {
            this.teacherSkills = null;
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public ArrayList<String> getTeacherSkills() {
        return teacherSkills;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != User.class) {
            return false;
        }

        User user = (User) o;
        return user.getId().equals(this.getId()) &&
                getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getEmail().equals(user.getEmail()) &&
                getBirthday().equals(user.getBirthday()) &&
                getRole().equals(user.getRole()) &&
                getTeacherSkills().equals(user.getTeacherSkills());
    }

    @Override
    public int hashCode() {
        int hash;
        int prime = 53;
        hash = (id == null) ? 0 : prime * id;
        hash *= prime * firstName.hashCode();
        hash *= prime * lastName.hashCode();
        hash *= prime * email.hashCode();
        hash *= prime * birthday.hashCode();
        hash *= prime * role.hashCode();
        hash *= prime * teacherSkills.hashCode();

        return hash;
    }
}
