package com.practice.springbootstarter.view;

import java.util.Date;

public class UserDTO {

    private Integer id;
    private String name;
    private Date bdate;

    public UserDTO(Integer id, String name, Date bdate) {
        super();
        this.id = id;
        this.name = name;
        this.bdate = bdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBdate() {
        return bdate;
    }

    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bdate=" + bdate +
                '}';
    }
}
