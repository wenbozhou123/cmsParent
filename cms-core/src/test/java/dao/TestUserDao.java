package dao;

import com.bowen.cms.dao.IUserDao;
import com.bowen.cms.model.*;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import util.AbstractDbUnitTestCase;
import util.EntitiesHelper;


import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserDao extends AbstractDbUnitTestCase {
    @Inject
    private IUserDao userDao;

    @Inject
    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws SQLException, IOException, DatabaseUnitException {
        Session s = sessionFactory.openSession();
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
        this.backupAllTable();
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
    }

    @Test
    public void testListUserRoles() {
        List<Role> actuals = Arrays.asList(new Role(2, "文章发布人员",RoleType.ROLE_PUBLISH), new Role(3, "文章审核人员", RoleType.ROLE_AUDIT));
        List<Role> roles = userDao.listUserRoles(2);
        EntitiesHelper.assertRole(roles, actuals);
    }

    @Test
    public void testListUserRoleIds() {
        List<Role> actuals = Arrays.asList(new Role(2, "文章发布人员",RoleType.ROLE_PUBLISH), new Role(3, "文章审核人员", RoleType.ROLE_AUDIT));
        List<Integer> roleIds = userDao.listUserRoleIds(2);
        EntitiesHelper.assertRoleIds(roleIds, actuals);
    }

    @Test
    public void testListUserGroup() {
        List<Group> actuals = Arrays.asList(new Group(1, "财务科","负责财务部门的网页"), new Group(3, "宣传部", "负责宣传部门的网页"));
        List<Group> expected = userDao.listUserGroup(2);
        EntitiesHelper.assertGroup(expected, actuals);
    }

    @Test
    public void testListUserGroupIds() {
        List<Group> actuals = Arrays.asList(new Group(1, "财务科","负责财务部门的网页"), new Group(3, "宣传部", "负责宣传部门的网页"));
        List<Integer> expected = userDao.listUserGroupIds(2);
        EntitiesHelper.assertGroupIds(expected, actuals);
    }

    @Test
    public void testLoadUserRole() {
        UserRole ur = new UserRole(3, new User(), new Role());
    }

    @After
    public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException {
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
        Session s = sessionHolder.getSession();
        s.flush();
        TransactionSynchronizationManager.unbindResource(sessionFactory);
        this.resumeTable();
    }

}
