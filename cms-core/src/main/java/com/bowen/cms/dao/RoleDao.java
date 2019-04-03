package com.bowen.cms.dao;

import com.bowen.basic.dao.BaseDao;
import com.bowen.cms.model.Role;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDao extends BaseDao<Role> implements IRoleDao {
}
