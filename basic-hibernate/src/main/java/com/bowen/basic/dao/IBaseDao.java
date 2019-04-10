package com.bowen.basic.dao;



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

}
