package com.bowen.basic.hibernate;


import com.bowen.basic.model.Pager;

import java.util.List;
import java.util.Map;

/**公共DAO处理对象，这个对象中包含了Hibernate的所有基本操作和对SQL的操作*/
public interface IBaseDao<T> {

    /**添加对象*/
    public T add(T t);
    /**更新对象*/
    public void update(T t);
    /**根据ID删除对象*/
    public void delete(int id);
    /**根据ID加载对象*/
    public T load(int id);

    /**
     * 不分页对象
     * @param hql  查询对象的hql
     * @param args 查询参数
     * @return  一组不分页的对象 */
    public List<T> list(String hql,Object[] args);
    public List<T> list(String hql, Object args);
    public List<T> list(String hql);

    /**基于别名和查询参数的混合列表对象*/
    public List<T> list(String hql, Object[] args, Map<String,Object> alias);
    public List<T> listByAlias(String hql, Map<String, Object> alias);



    /**
     * 分页对象
     * @param hql  查询对象的hql
     * @param args 查询参数
     * @return  一组不分页的对象 */
    public Pager<T> find(String hql, Object[] args);
    public Pager<T> find(String hql, Object arg);
    public Pager<T> find(String hql);

    /**基于别名和查询参数的混合列表对象*/
    public Pager<T> find(String hql, Object[] args, Map<String,Object> alias);
    public Pager  <T> findByAlias(String hql, Map<String, Object> alias);

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
    public List<Object> listBySql(String sql, Object[] args, Class<Object> clz, boolean hasEntity);
    public List<Object> listBySql(String sql, Object arg, Class<Object> clz, boolean hasEntity);
    public List<Object> listBySql(String sql,Class<Object> clz, boolean hasEntity);
    public List<Object> listBySql(String sql, Object[] args, Map<String,Object> alias, Class<Object> clz, boolean hasEntity);
    public List<Object> listByAliasSql(String sql, Map<String, Object> alias, Class<Object> clz, boolean hasEntity);


    /**
     * 根据SQL查询分页对象，不包含关联
     * @param sql 查询的sql语句
     * @param args 查询条件
     * @param clz  查询的实体对象
     * @param hasEntity 该对象是否是一个hibernate所管理的实体对象，如果不是，需要使用setResultTransform查询
     * @return 一组对象*/
    public Pager<Object> findBySql(String sql, Object[] args, Class<Object> clz, boolean hasEntity);
    public Pager<Object> findBySql(String sql, Object arg, Class<Object> clz, boolean hasEntity);
    public Pager<Object> findBySql(String sql,Class<Object> clz, boolean hasEntity);
    public Pager<Object> findBySql(String sql, Object[] args, Map<String,Object> alias, Class<Object> clz, boolean hasEntity);
    public Pager<Object> findByAliasSql(String sql, Map<String, Object> alias, Class<Object> clz, boolean hasEntity);



}
