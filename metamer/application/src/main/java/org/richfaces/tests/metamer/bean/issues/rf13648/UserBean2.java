package org.richfaces.tests.metamer.bean.issues.rf13648;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "userBean2")
@RequestScoped
public class UserBean2 {
    private Object name;
    private String email;
    private String password;
    private String address;
    private Integer age;
    private String city;
    private String job;
    private String recordStatus;
    private String zip;

    public UserBean2() {
        super();
    }

    public void store() {
        this.recordStatus = "User " + this.name + " stored successfully";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        System.out.println(email == null ? "null" : email.isEmpty() ? "empty" : name);
        this.email = email;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Object getName() {
        return this.name;
    }

    public void setName(Object name) {
        System.out.println(name == null ? "null" : ((String) name).isEmpty() ? "empty" : name);
        this.name = name;
    }

    public String nameItJohn() {
        setName("John");

        return null;
    }

    public String nameItMark() {
        setName("Mark");

        return null;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}