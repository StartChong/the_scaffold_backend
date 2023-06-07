package com.biology.commons.base.service;

import cn.craccd.mongoHelper.utils.FormatUtils;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.sql.Order;
import cn.hutool.json.JSONUtil;
import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.exception.SysException;
import com.biology.commons.interact.vo.PageVo;
import com.biology.commons.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.data.mongodb.core.convert.UpdateMapper;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;
import org.springframework.data.domain.Sort;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-11 14:11
 * @desc:
 */
@Slf4j
public class BaseMongoServiceImpl<T> implements IBaseMongoService<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    MongoConverter mongoConverter;

    QueryMapper queryMapper;

    UpdateMapper updateMapper;

    @Value("${spring.profiles.active}")
    private String env;

    private Boolean print;

    @PostConstruct
    public void init() {
        queryMapper = new QueryMapper(mongoConverter);
        updateMapper = new UpdateMapper(mongoConverter);
        print = StrUtil.containsAny(env, "local", "dev", "test");
        print = false;
    }


    @Override
    public void save(T bean) {
        logSave(bean);
        mongoTemplate.save(bean);
    }

    @Override
    public void insertCollection(List<T> list) {
        mongoTemplate.insert(list, this.getEntityClass());
    }


    @Override
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        logDelete(query);
        T t = this.findById(id);
        SysException.throwException(Objects.isNull(t), ResultCodeEnum.INTERNAL_ERROR.code(), "id为:" + id + "删除失败！");
        mongoTemplate.remove(t);
    }


    @Override
    public void deleteByCondition(T t) {
        Query query = buildBaseQuery(t, null, null);
        logDelete(query);
        mongoTemplate.remove(query, getEntityClass());
    }

    @Override
    public void updateById(String id, T t) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = buildBaseUpdate(t);
        logUpdate(query, update, false);
        update(query, update);
    }


    @Override
    public List<T> findByCondition(T t) {
        Query query = buildBaseQuery(t, null, null);
        logQuery(query);
        return mongoTemplate.find(query, getEntityClass());
    }


    @Override
    public List<T> find(Query query) {
        logQuery(query);
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public List<T> findByIds(List<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(ids));
        return this.find(query);
    }


    @Override
    public T findOne(Query query) {
        logQuery(query);
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    @Override
    public T findOne(T t) {
        Query query = buildBaseQuery(t, null, null);
        return findOne(query);
    }

    @Override
    public void update(Query query, Update update) {
        logUpdate(query, update, false);
        mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }


    @Override
    public T findById(String id) {
        Class<T> entityClass = this.getEntityClass();
        logQuery(new Query(Criteria.where("id").is(id)));
        return mongoTemplate.findById(id, entityClass);
    }


    @Override
    public T findById(String id, String collectionName) {
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
    }

    @Override
    public PageVo<T> findPageVo(PageVo<T> pageVo, Query query) {
        //如果没有条件 则所有全部
        query = query == null ? new Query(Criteria.where("_id").exists(true)) : query;
        long count = this.count(query);
        // 总数
        pageVo.setTotal(count);
        long currentPage = pageVo.getCurrent();
        long pageSize = pageVo.getSize();
        query.skip((currentPage - 1) * pageSize).limit(Convert.toInt(pageSize));
        logQuery(query);
        List<T> rows = this.find(query);
        pageVo.build(rows);
        return pageVo;
    }

    @Override
    public PageVo<T> findPageVoByCondition(PageVo<T> pageVo, T t, Boolean like, List<Sort.Order> orderList) {
        Query query = buildBaseQuery(t, like, orderList);
        return findPageVo(pageVo, query);
    }


    @Override
    public long count(Query query) {
        return mongoTemplate.count(query, this.getEntityClass());
    }


    private Query buildBaseQuery(T t, Boolean like, List<Sort.Order> orderList) {
        Query query = new Query();

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (value != null) {
                    if (value instanceof String) {
                        if (StringUtils.isNotBlank(value.toString())) {
                            if (Objects.nonNull(like)) {
                                if (like) {
                                    Pattern pattern = Pattern.compile("^.*" + value + ".*$", Pattern.CASE_INSENSITIVE);
                                    query.addCriteria(Criteria.where(field.getName()).regex(pattern));
                                } else {
                                    query.addCriteria(Criteria.where(field.getName()).is(value));
                                }
                            } else {
                                query.addCriteria(Criteria.where(field.getName()).is(value));
                            }
                        }
                    } else {
                        query.addCriteria(Criteria.where(field.getName()).is(value));
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (!CollectionUtils.isEmpty(orderList)) {
            query.with(Sort.by(orderList));
        }
        return query;
    }


    private Update buildBaseUpdate(T t) {
        Update update = new Update();

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (value != null) {
                    update.set(field.getName(), value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return update;
    }


    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        return getSuperClassGenericType(getClass(), 0);
    }


    @Override
    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    private Class getSuperClassGenericType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            log.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            log.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            log.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }


    /**
     * 打印查询语句
     *
     * @param query
     */
    private void logQuery(Query query) {
        if (print) {
            Class<?> clazz = this.getEntityClass();
            MongoPersistentEntity<?> entity = mongoConverter.getMappingContext().getPersistentEntity(clazz);
            Document mappedQuery = queryMapper.getMappedObject(query.getQueryObject(), entity);
            Document mappedField = queryMapper.getMappedObject(query.getFieldsObject(), entity);
            Document mappedSort = queryMapper.getMappedObject(query.getSortObject(), entity);

            String logStr = "\ndb." + StrUtil.lowerFirst(clazz.getSimpleName()) + ".find(";

            logStr += FormatUtils.bson(mappedQuery.toJson()) + ")";

            if (!query.getFieldsObject().isEmpty()) {
                logStr += ".projection(";
                logStr += FormatUtils.bson(mappedField.toJson()) + ")";
            }

            if (query.isSorted()) {
                logStr += ".sort(";
                logStr += FormatUtils.bson(mappedSort.toJson()) + ")";
            }

            if (query.getLimit() != 0L) {
                logStr += ".limit(" + query.getLimit() + ")";
            }

            if (query.getSkip() != 0L) {
                logStr += ".skip(" + query.getSkip() + ")";
            }
            logStr += ";";

            log.info(logStr);
        }
    }

    /**
     * 打印查询语句
     *
     * @param query
     */
    private void logCount(Query query) {
        if (print) {
            Class<?> clazz = this.getEntityClass();
            MongoPersistentEntity<?> entity = mongoConverter.getMappingContext().getPersistentEntity(clazz);
            Document mappedQuery = queryMapper.getMappedObject(query.getQueryObject(), entity);

            String logStr = "\ndb." + StrUtil.lowerFirst(clazz.getSimpleName()) + ".find(";
            logStr += FormatUtils.bson(mappedQuery.toJson()) + ")";
            logStr += ".count();";

            log.info(logStr);
        }
    }

    /**
     * 打印查询语句
     *
     * @param query
     */
    private void logDelete(Query query) {
        if (print) {
            Class<?> clazz = this.getEntityClass();
            MongoPersistentEntity<?> entity = mongoConverter.getMappingContext().getPersistentEntity(clazz);
            Document mappedQuery = queryMapper.getMappedObject(query.getQueryObject(), entity);

            String logStr = "\ndb." + StrUtil.lowerFirst(clazz.getSimpleName()) + ".remove(";
            logStr += FormatUtils.bson(mappedQuery.toJson()) + ")";
            logStr += ";";
            log.info(logStr);
        }
    }

    /**
     * 打印查询语句
     *
     * @param query
     */
    private void logUpdate(Query query, Update update, boolean multi) {
        if (print) {
            Class<?> clazz = this.getEntityClass();
            MongoPersistentEntity<?> entity = mongoConverter.getMappingContext().getPersistentEntity(clazz);
            Document mappedQuery = queryMapper.getMappedObject(query.getQueryObject(), entity);
            Document mappedUpdate = updateMapper.getMappedObject(update.getUpdateObject(), entity);

            String logStr = "\ndb." + StrUtil.lowerFirst(clazz.getSimpleName()) + ".update(";
            logStr += FormatUtils.bson(mappedQuery.toJson()) + ",";
            logStr += FormatUtils.bson(mappedUpdate.toJson()) + ",";
            logStr += FormatUtils.bson("{multi:" + multi + "})");
            logStr += ";";
            log.info(logStr);
        }

    }

    /**
     * 打印查询语句
     *
     * @param object
     */
    private void logSave(Object object) {
        if (print) {
            String logStr = "\ndb." + StrUtil.lowerFirst(object.getClass().getSimpleName()) + ".save(";
            logStr += JSONUtil.toJsonPrettyStr(object);
            logStr += ");";
            log.info(logStr);
        }
    }

    /**
     * 打印查询语句
     */
    private void logSave(List<?> list) {
        if (print && list.size() > 0) {
            Object object = list.get(0);

            String logStr = "\ndb." + StrUtil.lowerFirst(object.getClass().getSimpleName()) + ".save(";
            logStr += JSONUtil.toJsonPrettyStr(list);
            logStr += ");";
            log.info(logStr);
        }
    }

}
