package com.bowen.basic.dao;


import com.bowen.basic.model.User;
import com.bowen.basic.util.AbstractDbUnitTestCase;
import com.bowen.basic.util.EntitiesHelper;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
@Transactional
public class TestUserDao extends AbstractDbUnitTestCase{
    @Inject
    private IUserDao userDao;

    @Inject
    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws SQLException, IOException, DataSetException {
        this.backupAllTable();
    }

  /*  @Test
    public void testLoad() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        User u = userDao.load(2);
        EntitiesHelper.assertUser(u);
    }*/

    @Test
    public void testDelete() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
        userDao.delete(1);
        User tu= userDao.load(1);
        System.out.println(tu.getUsername());
    }

  /*  @Test
    public void testListByArgs() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        User u = userDao.load(1);
        EntitiesHelper.assertUser(u);
    }*/

    @After
    public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException {
        //this.resumeTable();
    }

}
