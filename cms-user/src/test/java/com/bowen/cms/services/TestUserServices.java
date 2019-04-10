package com.bowen.cms.services;

import com.bowen.cms.dao.IGroupDao;
import com.bowen.cms.dao.IRoleDao;
import com.bowen.cms.dao.IUserDao;
import com.bowen.cms.model.User;
import com.bowen.cms.service.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-beans.xml")
public class TestUserServices {

    @Inject
    private IUserService userService;

    @Inject
    private IRoleDao roleDao;

    @Inject
    private IUserDao userDao;

    @Inject
    private IGroupDao groupDao;

    private User baseUser = new User(1,"admin1","123", "admin1", "admin1@admin.com", "110", 1);

    @Test
    public void testDelete(){
        reset(userDao);
        int uid = 2;
        userDao.deleteUserGroups(uid);
        expectLastCall();
        userDao.deleteUserRoles(uid);
        expectLastCall();
        userDao.delete(uid);
        expectLastCall();
        replay(userDao);
        userService.delete(uid);
        verify(userDao);
    }

    @Test
    public void testUpdateStatus(){
        reset(userDao);
        int uid = 2;
        expect(userDao.load(uid)).andReturn(baseUser);
        userDao.update(baseUser);
        expectLastCall();
        replay(userDao);
        userService.updateStatus(uid);
        Assert.assertEquals(baseUser.getStatus(), 0);
        verify(userDao);
    }


}
