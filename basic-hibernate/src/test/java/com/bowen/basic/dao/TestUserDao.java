package com.bowen.basic.dao;


import com.bowen.basic.model.User;
import com.bowen.basic.util.AbstractDbUnitTestCase;
import com.bowen.basic.util.EntitiesHelper;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
@Transactional
public class TestUserDao extends AbstractDbUnitTestCase{
    @Inject
    private IUserDao userDao;

    @Before
    public void setUp() throws SQLException, IOException, DataSetException {
        this.backupAllTable();
    }

    @Test
    public void testLoad() throws DatabaseUnitException, SQLException {
        IDataSet ds = createDataSet("t_user");
        DatabaseOperation.CLEAN_INSERT.execute(dbunitCon ,ds);
        User u = userDao.load(1);
        EntitiesHelper.assertUser(u);


    }

    @After
    public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException {
        this.resumeTable();
    }

}
