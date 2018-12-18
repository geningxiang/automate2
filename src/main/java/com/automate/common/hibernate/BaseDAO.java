package com.automate.common.hibernate;
import com.automate.common.Pager;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 基础DAO
 *
 * @author: genx
 * @date: 2017-11-22 12:36
 */
public class BaseDAO<T, PK extends Serializable> extends HibernateDaoSupport {

    private Class<T> entityClass;

    public BaseDAO() {
        this.entityClass = null;
        Class<?> c = getClass();
        //反射获取超类
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parametrizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parametrizedType[0];
        }
    }

    public T getModel(PK id) {
        Assert.notNull(id, "id is required");
        return getHibernateTemplate().get(entityClass, id);
    }

    public T save(T entity) {
        Assert.notNull(entity, "entity is required");
        getHibernateTemplate().save(entityClass.getName(), entity);
        return entity;
    }

    public T update(T entity) {
        Assert.notNull(entity, "entity is required");
        getHibernateTemplate().update(entityClass.getName(), entity);
        return entity;
    }

    public void deleteById(PK id) {
        Assert.notNull(id, "id is required");
        T t = getHibernateTemplate().load(entityClass, id);
        getHibernateTemplate().delete(t);
    }

    public List<T> findAll() {
        return getHibernateTemplate().execute(session -> {
            return session.createQuery("from " + entityClass.getName()).list();
        });
    }

    public Pager getListPage(String hql, List<Object> pars, String orderBy, int pageNo, int pageSize) {
        Number count = this.getNumberResult("select count(*) " + hql, pars);
        Pager page = Pager.empty();
        if (count != null && 0 != count.intValue()) {
           page = Pager.of(pageNo, pageSize, count.intValue());
            if (pageNo > page.getPageCount()) {
                page.setResultList(new ArrayList());
                return page;
            } else {
                String fromSql = hql;
                if (StringUtils.isNotEmpty(orderBy)) {
                    fromSql = hql + " order by " + orderBy;
                }

                List<?> lst = this.getListForPage(fromSql, pars, page.getIndexOf(), page.getPageSize());
                page.setResultList(lst);
                return page;
            }
        } else {
            return page;
        }
    }


    public List<?> getListForPage(final String hql, final List<Object> params, final int offset, final int length) {
        List list = getHibernateTemplate().execute(session -> {
            Query query = session.createQuery(hql);
            if (params != null && params.size() > 0) {
                for (int i = 0; i < params.size(); i++) {
                    query.setParameter(i, params.get(i));
                }
            }
            query.setFirstResult(offset);
            query.setMaxResults(length);
            List list1 = query.list();
            return list1;
        });
        return list;
    }

    public List getList(String hql, List params){
        return getHibernateTemplate().execute(session -> {
            Query query = session.createQuery(hql);
            if (params != null && params.size() > 0) {
                for (int i = 0; i < params.size(); i++) {
                    query.setParameter(i, params.get(i));
                }
            }
            return query.list();
        });
    }

    public List getList(String hql){
        return getList(hql, null);
    }

    public Number getNumberResult(String hql, List args) {
        List<?> list = this.getListForPage(hql, args, 0, 1);
        if (list != null && list.size() != 0) {
            Object obj = list.get(0);
            if (obj == null) {
                return 0;
            } else if (!(obj instanceof Number) && !(obj instanceof Double) && !(obj instanceof Integer) && !(obj instanceof Long)) {
                throw new RuntimeException("未知的数据返回类型:" + obj.getClass().getName());
            } else {
                return (Number)obj;
            }
        } else {
            return 0;
        }
    }

    public int executeUpdate(final String hql, final List values) {
        return this.getHibernateTemplate().execute(session -> {
            Query query = session.createQuery(hql);
            if (values != null && values.size() > 0) {
                for(int i = 0; i < values.size(); ++i) {
                    query.setParameter(i, values.get(i));
                }
            }
            return query.executeUpdate();
        });
    }

    public int executeUpdateBySql(final String sql, final List values) {
        return this.getHibernateTemplate().execute(session -> {
            Query query = session.createSQLQuery(sql);
            if (values != null && values.size() > 0) {
                for(int i = 0; i < values.size(); ++i) {
                    query.setParameter(i, values.get(i));
                }
            }
            return query.executeUpdate();
        });
    }

    public Object getFirstObject(String hql, List values) {
        List list = this.getListForPage(hql, values, 0, 1);
        return list != null && list.size() != 0 ? list.get(0) : null;
    }

    public Object getFirstObjectLockMode(final String hql, final List values, final LockMode lockMode) {
        if (lockMode == null) {
            return this.getFirstObject(hql, values);
        } else {
            List list = this.getHibernateTemplate().execute(session -> {
                Query query = session.createQuery(hql);
                if (values != null && values.size() > 0) {
                    for(int i = 0; i < values.size(); ++i) {
                        query.setParameter(i, values.get(i));
                    }
                }

                query.setFirstResult(0);
                query.setMaxResults(1);
                query.setLockMode((String)null, lockMode);
                return query.list();
            });
            return list != null && list.size() != 0 ? list.get(0) : null;
        }
    }
}