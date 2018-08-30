package com.bowen.basic.hibernate;


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
     * @param sql  查询对象的sql
     * @param args 查询参数
     * @return  一组不分页的对象 */
    public List<T> list(String sql,Object[] args);
    public List<T> list(String sql, Object args);
    public List<T> list(String sql);

    /**基于别名和查询参数的混合列表对象*/
    public List<T> list(String sql, Object[] args, Map<String,Object> alias);
    public List<T> list(String sql, Map<String, Object> alias);

}
