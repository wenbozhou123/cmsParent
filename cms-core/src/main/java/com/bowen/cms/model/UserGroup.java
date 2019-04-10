package com.bowen.cms.model;

import org.hibernate.validator.constraints.ISBN;

import javax.persistence.*;

/**
 * 用户组对象，存储用户和组的关联*/
@Entity
@Table(name = "t_user_group")
public class UserGroup {

    private int id;
    private User user;
    private Group group;

    public UserGroup(){

    }

    public UserGroup(int id, User user, Group group) {
        this.id = id;
        this.user = user;
        this.group = group;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "u_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name="g_id")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
