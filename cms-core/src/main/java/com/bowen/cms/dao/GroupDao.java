package com.bowen.cms.dao;

import com.bowen.basic.dao.BaseDao;
import com.bowen.cms.model.Group;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class GroupDao extends BaseDao<Group> implements IGroupDao {

}
