package com.kk.pojo;

public class Employee {
    private Integer id;
    private Integer gender;
    private String empName;
    private String email;

    public Employee() {
    }

    public Employee(Integer id, Integer gender, String empName, String email) {
        this.id = id;
        this.gender = gender;
        this.empName = empName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", gender=" + gender +
                ", empName='" + empName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
