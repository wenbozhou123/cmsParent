package com.bowen.basic.dao;

import com.bowen.basic.model.Pager;
import com.bowen.basic.model.SystemContext;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
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
        return sessionFactory.getCurrentSession();
    }

    /**
     * 添加对象
     *
     * @param t
     */
    @Override
    public T add(T t) {
        getSession().save(t);
        return t;
    }

    /**
     * 更新对象
     *
     * @param t
     */
    @Override
    public void update(T t) {
        getSession().update(t);
    }

    /**
     * 根据ID删除对象
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        T t = this.load(id);
        getSession().delete(t);
    }

    /**
     * 根据ID加载对象
     *
     * @param id
     */
    @Override
    public T load(int id) {
        T t=(T)getSession().load(getClz(),id);
        return t;
    }

    /**
     * 不分页对象
     *
     * @param hql  查询对象的hql
     * @param args 查询参数
     * @return 一组不分页的对象
     */
    @Override
    public List<T> list(String hql, Object[] args) {
        return this.list(hql,args,null);
    }

    @Override
    public List<T> list(String hql, Object arg) {
        return this.list(hql, new Object[]{arg});
    }

    @Override
    public List<T> list(String hql) {
        return this.list(hql,null);
    }


    private String initSort(String hql){
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
        return hql;
    }

    private void setAliasParameter(Query query, Map<String, Object> alias){
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
    }

    private void setParameter(Query query, Object[] args){
        if(args != null && args.length > 0){
            int index = 0;
            for( Object arg : args) {
                query.setParameter(index++,arg);
            }
        }
    }
    /**
     * 基于别名和查询参数的混合列表对象
     *
     * @param hql
     * @param args
     * @param alias
     */
    @Override
    public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
        hql = initSort(hql);
        Query query = getSession().createQuery(hql);
        setAliasParameter(query,alias);
        setParameter(query, args);
        return query.list();
    }

    @Override
    public List<T> listByAlias(String hql, Map<String, Object> alias) {
        return this.list(hql,null,alias);
    }

    /**
     * 分页对象
     *
     * @param hql  查询对象的hql
     * @param args 查询参数
     * @return 一组不分页的对象
     */
    @Override
    public Pager<T> find(String hql, Object[] args) {
        return this.find(hql ,args, null);
    }

    @Override
    public Pager<T> find(String hql, Object arg) {
        return this.find(hql, new Object[]{arg});
    }

    @Override
    public Pager<T> find(String hql) {
        return this.find(hql, null);
    }


    private void setPager(Query query, Pager pages){
        Integer pageSize = SystemContext.getPageSize();
        Integer pageOffset = SystemContext.getPageOffset();
        //pageOff = pageOff == null || pageOff < 0 ? 0 : pageOff;
        if(pageOffset == null || pageOffset < 0 ) { pageOffset = 0;}
        if(pageSize == null || pageSize <0 ) { pageSize = 15; }
        pages.setOffset(pageOffset);
        pages.setSize(pageSize);
        query.setFirstResult(pageOffset).setMaxResults(pageSize);
    }

    private String getCountHql(String hql, boolean isHql){
        String endSub = hql.substring(hql.indexOf("from"));
        String countStr = "select count(*) " + endSub;
        if(isHql){
            countStr.replace("fetch", "");
        }
        return countStr;

    }
    /**
     * 基于别名和查询参数的混合列表对象
     *
     * @param hql
     * @param args
     * @param alias
     */
    @Override
    public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
        hql = initSort(hql);
        String cq = getCountHql(hql, true);
        cq = initSort(cq);
        Query cquery = getSession().createQuery(cq);
        Query query = getSession().createQuery(hql);

        //设置别名参数
        setAliasParameter(query,alias);
        setAliasParameter(cquery, alias);
        //设置量化参数
        setParameter(query, args);
        setParameter(cquery, args);

        Pager<T> pages = new Pager<T>();
        setPager(query, pages);
        List<T> datas = query.list();
        pages.setDatas(datas);
        long total = (long) cquery.uniqueResult();
        pages.setTotal(total);
        return null;
    }

    @Override
    public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
        return this.find(hql, null ,alias);
    }

    /**
     * 根据hql查询一组对象
     *
     * @param hql
     * @param args
     */
    @Override
    public Object queryObject(String hql, Object[] args) {
        return this.queryObject(hql, args, null);
    }

    @Override
    public Object queryObject(String hql, Object arg) {
        return this.queryObject(hql, new Object[]{arg});
    }

    @Override
    public Object queryObject(String hql) {
        return this.queryObject(hql, null);
    }

    @Override
    public Object queryObject(String hql, Object[] args, Map<String, Object> alias){
        Query query = getSession().createQuery(hql);
        setAliasParameter(query, alias);
        setParameter(query ,args);
        return query.uniqueResult();
    }

    @Override
    public Object queryObjectByAlias(String hql, Map<String, Object> alias){
        return this.queryObject(hql,null, alias);
    }

    /**
     * 根据hql更新一组对象
     *
     * @param hql
     * @param args
     */
    @Override
    public Object updateByHql(String hql, Object[] args) {
        Query query = getSession().createQuery(hql);
        setParameter(query, args);
        return query.executeUpdate();
    }

    @Override
    public Object updateByHql(String hql, Object arg) {
        return this.updateByHql(hql, new Object[]{arg});
    }

    @Override
    public Object updateByHql(String hql) {
        return this.updateByHql(hql, null);
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
    @Override
    public List<Object> listBySql(String sql, Object[] args, Class<Object> clz, boolean hasEntity) {
        return this.listBySql(sql, args, null, clz, hasEntity);
    }

    @Override
    public List<Object> listBySql(String sql, Object arg, Class<Object> clz, boolean hasEntity) {
        return this.listBySql(sql, new Object[]{arg}, clz, hasEntity);
    }

    @Override
    public List<Object> listBySql(String sql, Class<Object> clz, boolean hasEntity) {
        return this.listBySql(sql, null,clz, hasEntity);
    }

    @Override
    public List<Object> listBySql(String sql, Object[] args, Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {
        sql = initSort(sql);
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        setAliasParameter(sqlQuery,alias);
        setParameter(sqlQuery, args);
        if(hasEntity){
            sqlQuery.addEntity(clz);
        }else {
            sqlQuery.setResultTransformer(Transformers.aliasToBean(clz));
        }
        return sqlQuery.list();
    }

    @Override
    public List<Object> listByAliasSql(String sql, Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {
        return this.listBySql(sql, null, alias, clz, hasEntity);
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
    @Override
    public Pager<Object> findBySql(String sql, Object[] args, Class<Object> clz, boolean hasEntity) {
        return this.findBySql(sql, args, null, clz, hasEntity);
    }

    @Override
    public Pager<Object> findBySql(String sql, Object arg, Class<Object> clz, boolean hasEntity) {
        return this.findBySql(sql,new Object[]{arg}, clz, hasEntity);
    }

    @Override
    public Pager<Object> findBySql(String sql, Class<Object> clz, boolean hasEntity) {
        return this.findBySql(sql, null, clz, hasEntity);
    }

    @Override
    public Pager<Object> findBySql(String sql, Object[] args, Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {
        String cq = getCountHql(sql ,false);
        cq = initSort(cq);
        sql = initSort(sql);
        SQLQuery cquery = getSession().createSQLQuery(cq);
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        setAliasParameter(cquery , alias);
        setAliasParameter(sqlQuery, alias);
        setParameter(cquery ,args);
        setParameter(sqlQuery ,args);

        Pager<Object> pages = new Pager<Object>();
        setPager(sqlQuery, pages);
        if (hasEntity){
            sqlQuery.addEntity(clz);
        }else{
            sqlQuery.setResultTransformer(Transformers.aliasToBean(clz));
        }
        List<Object> datas = sqlQuery.list();
        pages.setDatas(datas);
        long total = (long) cquery.uniqueResult();
        pages.setTotal(total);
        return pages;
    }

    @Override
    public Pager<Object> findByAliasSql(String sql, Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {
        return this.findBySql(sql,null, alias ,clz, hasEntity);
    }
}
