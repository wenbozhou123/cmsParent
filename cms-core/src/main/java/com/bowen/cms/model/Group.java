package com.bowen.cms.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户组对象， 使用该对象来获取可以发布文章栏目信息
 */

@Entity
@Table(name="t_group")
public class Group {

    /**组id*/
    private int id;
    /**组名称*/
    private String name;
    /**组描述*/
    private String des;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
