package dao;

import com.bowen.basic.dao.BaseDao;
import com.bowen.cms.model.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

}
