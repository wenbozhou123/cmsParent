package com.bowen.cms.dao;

import com.bowen.basic.dao.BaseDao;
import com.bowen.cms.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {
    /**
     * 获取用户的所有角色信息
     *
     * @param userId
     */
    @Override
    public List<Role> listUserRoles(int userId) {
        String hql = "select ur.role from UserRole ur where ur.user.id = ? ";
        return this.getSession().createQuery(hql).setParameter(0, userId).list();
    }

    /**
     * 获取用户的所有角色id
     *
     * @param userId
     */
    @Override
    public List<Integer> listUserRoleIds(int userId) {
        String hql = "select ur.role.id from UserRole ur where ur.user.id=?";
        return this.getSession().createQuery(hql).setParameter(0, userId).list();
    }

    /**
     * 获取用户的所有组信息
     *
     * @param userId
     */
    @Override
    public List<Group> listUserGroup(int userId) {
        String hql = "select ug.group from UserGroup ug where ug.user.id = ?";
        return this.getSession().createQuery(hql).setParameter(0, userId).list();
    }

    /**
     * 获取用户的所有角色id
     *
     * @param userId
     */
    @Override
    public List<Integer> listUserGroupIds(int userId) {
        String hql = "select ug.group.id from UserGroup ug where ug.user.id=?";
        return this.getSession().createQuery(hql).setParameter(0, userId).list();
    }

    /**
     * 根据用户和角色获取用户角色关联对象
     *
     * @param userId
     * @param roleId
     */
    @Override
    public UserRole loadUserRole(int userId, int roleId) {
        String hql = "select ur from UserRole ur where ur.user.id = ? and ur.role.id = ?";
        return (UserRole)this.getSession().createQuery(hql).setParameter(0, userId).setParameter(1, roleId).uniqueResult();
    }

    /**
     * 根据用户和组获取用户组关联对象
     *
     * @param userId
     * @param groupId
     */
    @Override
    public UserGroup loadUserGroup(int userId, int groupId) {
        String hql = "select ug from UserGroup ug where ug.user.id = ? and ug.group.id = ?";
        return (UserGroup)this.getSession().createQuery(hql).setParameter(0, userId).setParameter(1, groupId).uniqueResult();
    }

    /**
     * 根据用户名获取用户对象
     *
     * @param username
     */
    @Override
    public User loadByUsername(String username) {
        String hql = "from User where username = ?";
        return (User)this.queryObject(hql, username);
    }

    /**
     * 根据角色id获取用户列表
     *
     * @param roleId
     */
    @Override
    public List<User> listRoleUsers(int roleId) {
        String hql = "select ur.user from UserRole ur where ur.role.id = ?";
        return this.list(hql, roleId);
    }

    /**
     * 根据类型获取用户列表
     *
     * @param roleType
     */
    @Override
    public List<User> listRoleUsers(RoleType roleType) {
        String hql = "select ur.user from UserRole ur where ur.role.roleType = ?";
        return this.list(hql,roleType);
    }

    /**
     * 获取某个组的用户对象
     *
     * @param groupId
     */
    @Override
    public List<User> listGroupUsers(int groupId) {
        String hql = "select ug.user from UserGroup ug where ug.group.id = ?";
        return this.list(hql, groupId);
    }
}
