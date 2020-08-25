package com.web.oa.pojo;

import java.util.List;

public class TreeMenu {
    private Integer id;
    private String name;
    private List<Permission> children;

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

    public List<Permission> getChildren() {
        return children;
    }

    public void setChildren(List<Permission> children) {
        this.children = children;
    }

}
