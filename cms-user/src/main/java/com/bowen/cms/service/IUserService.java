package com.bowen.cms.service;

import com.bowen.basic.model.Pager;
import com.bowen.cms.model.Group;
import com.bowen.cms.model.Role;
import com.bowen.cms.model.User;

import java.util.List;

public interface IUserService {
    /**添加用户，需要判断用户是否存在，如果存在则抛出异常
     * @param user 用户对象
     * @param rids 用户所有角色信息
     * @param gids 用户所有组信息
     * */
    public void add(User user,Integer[] rids, Integer[] gids);

    /**删除用户，注意需要把用户和角色和组的对应关系删除
     * 如果用户存在相应的文章不能删除
     * */
    public void delete(int userId);

    /**
     * 用户的更新，如果rids中的角色在用户中已经存在，就不做操作
     * 如不存在，则添加，如用户角色不存在rids中则需要删除；gids同理*/
    public void update(User user, Integer[] rids, Integer[] gids);

    /**更新用户的状态*/
    public void updateStatus(int userId);

    /**列表用户*/
    public Pager<User> findUser();

    /**获取用户信息*/
    public User load(int userId);

    /**获取用户的所有角色信息*/
    public List<Role> listUserRoles(int userId);

    /**获取用户的所有组信息 */
    public List<Group> listUserGroups(int userId);
}
