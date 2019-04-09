package com.bowen.basic.dao;

import com.bowen.basic.model.Pager;
import com.bowen.basic.model.User;

import java.util.List;
import java.util.Map;

public interface IUserDao extends IBaseDao<User> {

    /**
     * 不分页对象
     * @param hql  查询对象的hql
     * @param args 查询参数
     * @return  一组不分页的对象 */
    public List<User> list(String hql, Object[] args);
    public List<User> list(String hql, Object args);
    public List<User> list(String hql);

    /**基于别名和查询参数的混合列表对象*/
    public List<User> list(String hql, Object[] args, Map<String,Object> alias);
    public List<User> listByAlias(String hql, Map<String, Object> alias);



    /**
     * 分页对象
     * @param hql  查询对象的hql
     * @param args 查询参数
     * @return  一组不分页的对象 */
    public Pager<User> find(String hql, Object[] args);
    public Pager<User> find(String hql, Object arg);
    public Pager<User> find(String hql);

    /**基于别名和查询参数的混合列表对象*/
    public Pager<User> find(String hql, Object[] args, Map<String,Object> alias);
    public Pager<User> findByAlias(String hql, Map<String, Object> alias);

    /**根据hql查询一组对象*/
    public Object queryObject(String hql, Object[] args);
    public Object queryObject(String hql, Object arg);
    public Object queryObject(String hql);
    public Object queryObject(String hql, Object[] args, Map<String, Object> alias);
    public Object queryObjectByAlias(String hql, Map<String, Object> alias);

    /**根据hql更新一组对象*/
    public Object updateByHql(String hql, Object[] args);
    public Object updateByHql(String hql, Object arg);
    public Object updateByHql(String hql);


    /**
     * 根据SQL查询不分页对象，不包含关联
     * @param sql 查询的sql语句
     * @param args 查询条件
     * @param clz  查询的实体对象
     * @param hasEntity 该对象是否是一个hibernate所管理的实体对象，如果不是，需要使用setResultTransform查询
     * @return 一组对象*/
    public <N extends Object>List<N> listBySql(String sql, Object[] args, Class<?> clz, boolean hasEntity);
    public <N extends Object>List<N> listBySql(String sql, Object arg, Class<?> clz, boolean hasEntity);
    public <N extends Object>List<N> listBySql(String sql,Class<?> clz, boolean hasEntity);
    public <N extends Object>List<N> listBySql(String sql, Object[] args, Map<String,Object> alias, Class<?> clz, boolean hasEntity);
    public <N extends Object>List<N> listByAliasSql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity);


    /**
     * 根据SQL查询分页对象，不包含关联
     * @param sql 查询的sql语句
     * @param args 查询条件
     * @param clz  查询的实体对象
     * @param hasEntity 该对象是否是一个hibernate所管理的实体对象，如果不是，需要使用setResultTransform查询
     * @return 一组对象*/
    public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Class<?> clz, boolean hasEntity);
    public <N extends Object>Pager<N> findBySql(String sql, Object arg, Class<?> clz, boolean hasEntity);
    public <N extends Object>Pager<N> findBySql(String sql,Class<?> clz, boolean hasEntity);
    public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Map<String,Object> alias, Class<?> clz, boolean hasEntity);
    public <N extends Object>Pager<N> findByAliasSql(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity);
}
