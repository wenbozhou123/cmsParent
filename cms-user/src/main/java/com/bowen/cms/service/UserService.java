package com.bowen.cms.service;

import com.bowen.basic.model.Pager;
import com.bowen.cms.dao.*;
import com.bowen.cms.exception.CmsException;
import com.bowen.cms.model.Group;
import com.bowen.cms.model.Role;
import com.bowen.cms.model.User;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("userService")
public class UserService implements IUserService {


    private IUserDao userDao;
    private IRoleDao roleDao;
    private IGroupDao groupDao;

    public IUserDao getUserDao() {
        return userDao;
    }

    @Inject
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public IRoleDao getRoleDao() {
        return roleDao;
    }

    @Inject
    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public IGroupDao getGroupDao() {
        return groupDao;
    }

    @Inject
    public void setGroupDao(IGroupDao groupDao) {
        this.groupDao = groupDao;
    }

    private void  addUserRole(User user, Integer rid){
        //检查角色对象是否存在，如不存在，就刨出异常
        Role role = roleDao.load(rid);
        if (role == null) throw new CmsException("要添加的用户角色不存在");
        //检查用户角色对象是否已经存在，如果存在不添加，
        userDao.addUserRole(user, role);
    }

    private void addUserGroup(User user, Integer gid){
        Group g = groupDao.load(gid);
        if(g==null) throw new CmsException("添加的组对象不存在");
        userDao.addUserGroup(user, g);
    }
    /**
     * 添加用户，需要判断用户是否存在，如果存在则抛出异常
     *
     * @param user 用户对象
     * @param rids 用户所有角色信息
     * @param gids 用户所有组信息
     */
    @Override
    public void add(User user, Integer[] rids, Integer[] gids) {
        User tu = userDao.loadByUsername(user.getUsername());
        if (tu != null) {
            throw new CmsException("添加的用户对象已经存在");
        }
        userDao.add(user);
        //添加角色对象
        for(Integer rid : rids){
            addUserRole(user, rid);
        }

        //添加组对象
        for(Integer gid : gids){
            addUserGroup(user, gid);
        }
    }

    /**
     * 删除用户，注意需要把用户和角色和组的对应关系删除
     * 如果用户存在相应的文章不能删除
     *
     * @param userId
     */
    @Override
    public void delete(int userId) {
        //TODO 需要进行用户是否有文章的判断

        //1.删除用户管理的组对象
        userDao.deleteUserGroups(userId);
        //2.删除用户角色对象
        userDao.deleteUserRoles(userId);

        userDao.delete(userId);

    }

    /**
     * 用户的更新，如果rids中的角色在用户中已经存在，就不做操作
     * 如不存在，则添加，如用户角色不存在rids中则需要删除；gids同理
     *
     * @param user
     * @param rids
     * @param gids
     */
    @Override
    public void update(User user, Integer[] rids, Integer[] gids) {
        //获取用户已经存在的gid和rid
        List<Integer> egids = userDao.listUserGroupIds(user.getId());
        List<Integer> erids = userDao.listUserRoleIds(user.getId());
        //如果erids不存在rids就要进行添加
        for(Integer rid : rids){
            if (!erids.contains(rid)){
                addUserRole(user, rid);
            }
        }
        for (Integer gid : gids){
            if(!egids.contains(gid)){
                addUserRole(user, gid);
            }
        }

        //进行删除
        for(Integer erid :erids){
            if(!ArrayUtils.contains(rids ,erid)){
                userDao.deleteUserRole(user.getId(), erid);
            }
        }

        for(Integer egid :egids){
            if(!ArrayUtils.contains(gids ,egid)){
                userDao.deleteUserGroup(user.getId(), egid);
            }
        }
    }

    /**
     * 更新用户的状态
     *
     * @param id
     */
    @Override
    public void updateStatus(int id) {
        User u = userDao.load(id);
        if(u==null) throw new CmsException("修改状态的用户不存在");
         u.setStatus(1-u.getStatus());
        userDao.update(u);
    }

    /**
     * 列表用户
     */
    @Override
    public Pager<User> findUser() {
        return userDao.findUser();
    }

    /**
     * 获取用户信息
     *
     * @param id
     */
    @Override
    public User load(int id) {
        return null;
    }

    /**
     * 获取用户的所有角色信息
     *
     * @param id
     */
    @Override
    public List<Role> listUserRoles(int id) {
        return null;
    }

    /**
     * 获取用户的所有组信息
     *
     * @param id
     */
    @Override
    public List<Group> listUserGroups(int id) {
        return null;
    }
}
