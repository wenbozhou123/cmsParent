package com.bowen.cms.model;

import javax.persistence.*;

/**
 * @author bowen
 * 角色对象，用来对应可以访问的功能，系统中为了简单只定义管理员，发布人员，审核人员
 * */
@Entity
@Table(name = "t_role")
public class Role {

    /**
     * 角色id*/
    private int id;
    /**角色名称*/
    private String name;
    /**角色编号，枚举类型*/
    private RoleType roleType;

    public Role(){

    }

    public Role(int id, String name, RoleType roleType) {
        this.id = id;
        this.name = name;
        this.roleType = roleType;
    }

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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role_type")
    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}
