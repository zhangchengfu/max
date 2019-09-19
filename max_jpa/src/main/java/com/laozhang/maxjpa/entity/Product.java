package com.laozhang.maxjpa.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 9207396565690780545L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
