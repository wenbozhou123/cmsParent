package com.bowen.basic.dao;

import com.bowen.basic.model.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

}
