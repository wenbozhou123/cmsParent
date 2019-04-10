package com.bowen.basic.dao;


import com.bowen.basic.model.Pager;
import com.bowen.basic.model.SystemContext;
import com.bowen.basic.model.User;
import com.bowen.basic.util.AbstractDbUnitTestCase;
import com.bowen.basic.util.EntitiesHelper;
import org.apache.commons.collections.map.HashedMap;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserDao extends AbstractDbUnitTestCase{
    @Inject
    private IUserDao userDao;

    @Inject
    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws SQLException, IOException, DataSetException {
        Session s = sessionFactory.openSession();
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
        this.backupAllTable();
    }

    @Test
    public void testAdd() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        User actual = new User(21, "admin21");
        userDao.add(actual);
        User expected = userDao.load(21);
        EntitiesHelper.assertUser(expected, actual);
    }

    @Test
    public void testLoad() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        User u = userDao.load(1);
        EntitiesHelper.assertUser(u);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testDelete() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
        userDao.delete(1);
        User tu= userDao.load(1);
        System.out.println(tu.getUsername());
    }

    @Test
    public void testListByArgs() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        SystemContext.setOrder("desc");
        SystemContext.setSort("id");
        List<User> expected = userDao.list("from User where id>? and id <?", new Object[]{1,4});
        List<User> actual = Arrays.asList(new User(3,"admin3"), new User(2,"admin2"));
        assertNotNull(expected);
        assertTrue(expected.size() == 2);
        EntitiesHelper.assertUsers(expected, actual);
    }

    @Test
    public void testListByArgsAndAlias() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        SystemContext.setOrder("asc");
        SystemContext.setSort("id");
        Map<String, Object> alias = new HashedMap();
        alias.put("ids", Arrays.asList(1,2,3,5,6,7,8,10));
        List<User> expected = userDao.list("from User where id>? and id <? and id in (:ids)", new Object[]{1,4}, alias);
        List<User> actual = Arrays.asList(new User(2,"admin2"), new User(3,"admin3"));
        EntitiesHelper.assertUsers(expected, actual);
    }

    @Test
    public void testFindByArgs() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        SystemContext.setOrder("desc");
        SystemContext.setSort("id");
        SystemContext.setPageSize(3);
        SystemContext.setPageOffset(0);
        Pager<User> expected = userDao.find("from User where id>=? and id <=?", new Object[]{1,10});
        List<User> actual = Arrays.asList(new User(10,"admin10"), new User(9,"admin9"), new User(8,"admin8"));
        assertNotNull(expected);
        assertTrue(expected.getTotal().intValue() == 10);
        assertTrue(expected.getOffset() == 0);
        assertTrue(expected.getSize() == 3);
        EntitiesHelper.assertUsers(expected.getDatas(), actual);
    }

    @Test
    public void testFindByArgsAndAlias() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        SystemContext.setOrder("asc");
        SystemContext.setSort("id");
        SystemContext.setPageSize(3);
        SystemContext.setPageOffset(0);
        Map<String, Object> alias = new HashedMap();
        alias.put("ids", Arrays.asList(1,2,4,5,6,7,8,10));
        Pager<User> expected = userDao.find("from User where id>=? and id <=? and id in (:ids)", new Object[]{1,10}, alias);
        List<User> actual = Arrays.asList(new User(1,"admin1"), new User(2,"admin2"), new User(4,"admin4"));
        assertNotNull(expected);
        assertTrue(expected.getTotal().intValue() == 8);
        assertTrue(expected.getOffset() == 0);
        assertTrue(expected.getSize() == 3);
        EntitiesHelper.assertUsers(expected.getDatas(), actual);
    }

    @Test
    public void testListSQLByArgs() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        SystemContext.setOrder("desc");
        SystemContext.setSort("id");
        List<User> expected = userDao.listBySql("select * from t_user where id>? and id <?", new Object[]{1,4}, User.class, true);
        List<User> actual = Arrays.asList(new User(3,"admin3"), new User(2,"admin2"));
        assertNotNull(expected);
        assertTrue(expected.size() == 2);
        EntitiesHelper.assertUsers(expected, actual);
    }

    @Test
    public void testListSQLByArgsAndAlias() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        SystemContext.setOrder("desc");
        SystemContext.setSort("id");
        Map<String, Object> alias = new HashedMap();
        alias.put("ids", Arrays.asList(1,2,3,5,6,7,8,10));
        List<User> expected = userDao.listBySql("select * from t_user where id>? and id <? and id in (:ids)", new Object[]{1,4},alias, User.class, true);
        List<User> actual = Arrays.asList(new User(3,"admin3"), new User(2,"admin2"));
        assertNotNull(expected);
        assertTrue(expected.size() == 2);
        EntitiesHelper.assertUsers(expected, actual);
    }

    @Test
    public void testFindSQLByArgs() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        SystemContext.setOrder("desc");
        SystemContext.setSort("id");
        SystemContext.setPageSize(3);
        SystemContext.setPageOffset(0);
        Pager<User> expected = userDao.findBySql("select * from t_user where id>=? and id <=? ", new Object[]{1,10}, User.class, true);
        List<User> actual = Arrays.asList(new User(10,"admin10"), new User(9,"admin9"), new User(8,"admin8"));
        assertNotNull(expected);
        assertTrue(expected.getTotal().intValue() == 10);
        assertTrue(expected.getOffset() == 0);
        assertTrue(expected.getSize() == 3);
        EntitiesHelper.assertUsers(expected.getDatas(), actual);
    }

    @Test
    public void testFindSQLByArgsAndAlias() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        SystemContext.setOrder("asc");
        SystemContext.setSort("id");
        SystemContext.setPageSize(3);
        SystemContext.setPageOffset(0);
        Map<String, Object> alias = new HashedMap();
        alias.put("ids", Arrays.asList(1,2,4,5,6,7,8,10));
        Pager<User> expected = userDao.findBySql("select * from t_user where id>=? and id <=? and id in(:ids)", new Object[]{1,10},alias,  User.class, true);
        List<User> actual = Arrays.asList(new User(1,"admin1"), new User(2,"admin2"), new User(4,"admin4"));
        assertNotNull(expected);
        assertTrue(expected.getTotal().intValue() == 8);
        assertTrue(expected.getOffset() == 0);
        assertTrue(expected.getSize() == 3);
        EntitiesHelper.assertUsers(expected.getDatas(), actual);
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
