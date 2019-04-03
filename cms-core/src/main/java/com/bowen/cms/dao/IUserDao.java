package com.bowen.cms.dao;

import com.bowen.basic.dao.IBaseDao;
import com.bowen.cms.model.*;

import java.util.List;

public interface IUserDao extends IBaseDao<User >{

    /**
     * 获取用户的所有角色信息*/
    public List<Role> listUserRoles(int userId);

    /**
     * 获取用户的所有角色id*/
    public List<Integer> listUserRoleIds(int userId);

    /**
     * 获取用户的所有组信息*/
    public List<Group> listUserGroup(int userId);

    /**
     * 获取用户的所有角色id*/
    public List<Integer> listUserGroupIds(int userId);

    /**根据用户和角色获取用户角色关联对象
     **/
    public UserRole loadUserRole(int userId, int roleId);

    /**根据用户和组获取用户组关联对象
     **/
    public UserGroup loadUserGroup(int userId, int groupId);

    /**根据用户名获取用户对象*/
    public User loadByUsername(String username);

    /**根据角色id获取用户列表*/
    public List<User> listRoleUsers(int roleId);

    /**根据角色id获取用户列表*/
    public List<User> listRoleUsers(RoleType roleType);

    /**获取某个组的用户对象 */
    public List<User> listGroupUsers(int groupId);
}
