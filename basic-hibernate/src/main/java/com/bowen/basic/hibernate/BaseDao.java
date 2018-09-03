package com.bowen.basic.hibernate;

import com.bowen.basic.model.Pager;
import com.bowen.basic.model.SystemContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class BaseDao<T> implements IBaseDao<T> {

    private SessionFactory sessionFactory;

    /**创建一个class的对象来获取泛型的class*/
    private Class<T> clz;

    public Class<T> getClz(){
        if (clz == null){
            //获取泛型对象
            clz= (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return clz;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Inject
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession(){
        return sessionFactory.openSession();
    }

    /**
     * 添加对象
     *
     * @param t
     */
    public T add(T t) {
        getSession().save(t);
        return t;
    }

    /**
     * 更新对象
     *
     * @param t
     */
    public void update(T t) {
        getSession().update(t);
    }

    /**
     * 根据ID删除对象
     *
     * @param id
     */
    public void delete(int id) {
        getSession().delete(this.load(id));
    }

    /**
     * 根据ID加载对象
     *
     * @param id
     */
    public T load(int id) {
        return (T)getSession().load(getClz(),id);
    }

    /**
     * 不分页对象
     *
     * @param hql  查询对象的hql
     * @param args 查询参数
     * @return 一组不分页的对象
     */
    public List<T> list(String hql, Object[] args) {

        return null;
    }

    public List<T> list(String hql, Object args) {
        return null;
    }

    public List<T> list(String hql) {
        return null;
    }

    /**
     * 基于别名和查询参数的混合列表对象
     *
     * @param hql
     * @param args
     * @param alias
     */
    public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
        String order = SystemContext.getOrder();
        String sort = SystemContext.getSort();
        if (sort != null && "".equals(sort.trim()) ){
            hql += " order by " + sort;
            if(!"desc".equals(order)) {
                hql += " asc";
            }else{
                hql += " desc";
            }
        }
        Query query = getSession().createQuery(hql);
        if(alias != null){
            Set<String> keys =  alias.keySet();
            for(String key : keys){
                 Object val = alias.get(key);
                 if(val instanceof Collection){
                     //查询条件是列表
                     query.setParameterList(key, (Collection) val);
                 }else{
                     query.setParameter(key, val);
                 }
            }
        }
        if(args != null && args.length > 0){
            int index = 0;
            for( Object arg : args) {
                query.setParameter(index++,arg);
            }
        }

        return query.list();
    }

    public List<T> list(String hql, Map<String, Object> alias) {
        return null;
    }

    /**
     * 分页对象
     *
     * @param hql  查询对象的hql
     * @param args 查询参数
     * @return 一组不分页的对象
     */
    public Pager<T> find(String hql, Object[] args) {
        return null;
    }

    public Pager<T> find(String hql, Object args) {
        return null;
    }

    public Pager<T> find(String hql) {
        return null;
    }

    /**
     * 基于别名和查询参数的混合列表对象
     *
     * @param hql
     * @param args
     * @param alias
     */
    public Pager<T> findst(String hql, Object[] args, Map<String, Object> alias) {
        return null;
    }

    public Pager<T> find(String hql, Map<String, Object> alias) {
        return null;
    }

    /**
     * 根据hql查询一组对象
     *
     * @param hql
     * @param args
     */
    public Object queryObject(String hql, Object[] args) {
        return null;
    }

    public Object queryObject(String hql, Object args) {
        return null;
    }

    public Object queryObject(String hql) {
        return null;
    }

    /**
     * 根据hql更新一组对象
     *
     * @param hql
     * @param args
     */
    public Object updateByHql(String hql, Object[] args) {
        return null;
    }

    public Object updateByHql(String hql, Object args) {
        return null;
    }

    public Object updateByHql(String hql) {
        return null;
    }

    /**
     * 根据SQL查询不分页对象，不包含关联
     *
     * @param sql       查询的sql语句
     * @param args      查询条件
     * @param clz       查询的实体对象
     * @param hasEntity 该对象是否是一个hibernate所管理的实体对象，如果不是，需要使用setResultTransform查询
     * @return 一组对象
     */
    public List<T> listBySql(String sql, Object[] args, Class<T> clz, boolean hasEntity) {
        return null;
    }

    public List<T> listBySql(String sql, Object args, Class<T> clz, boolean hasEntity) {
        return null;
    }

    public List<T> listBySql(String sql, Class<T> clz, boolean hasEntity) {
        return null;
    }

    public List<T> listBySql(String sql, Object[] args, Map<String, Object> alias, Class<T> clz, boolean hasEntity) {
        return null;
    }

    public List<T> listBySql(String sql, Map<String, Object> alias, Class<T> clz, boolean hasEntity) {
        return null;
    }

    /**
     * 根据SQL查询分页对象，不包含关联
     *
     * @param sql       查询的sql语句
     * @param args      查询条件
     * @param clz       查询的实体对象
     * @param hasEntity 该对象是否是一个hibernate所管理的实体对象，如果不是，需要使用setResultTransform查询
     * @return 一组对象
     */
    public Pager<T> findBySql(String sql, Object[] args, Class<T> clz, boolean hasEntity) {
        return null;
    }

    public Pager<T> findBySql(String sql, Object args, Class<T> clz, boolean hasEntity) {
        return null;
    }

    public Pager<T> findBySql(String sql, Class<T> clz, boolean hasEntity) {
        return null;
    }

    public Pager<T> findBySql(String sql, Object[] args, Map<String, Object> alias, Class<T> clz, boolean hasEntity) {
        return null;
    }

    public Pager<T> findBySql(String sql, Map<String, Object> alias, Class<T> clz, boolean hasEntity) {
        return null;
    }
}
