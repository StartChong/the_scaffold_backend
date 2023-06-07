package com.biology.commons.base.service;

import cn.hutool.db.sql.Order;
import com.biology.commons.interact.vo.PageVo;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-11 13:52
 * @desc:
 */
public interface IBaseMongoService<T> {

    /**
     * @param t
     * @author: lichong
     * @description: 新增
     * @date: 2022/4/11
     */
    void save(T t);

    /**
     * insertCollection
     *
     * @author: lichong
     * @param: list
     * @return:
     * @description: 批量插入
     * @date: 2022/4/28
     */
    void insertCollection(List<T> list);

    /**
     * @param id
     * @author: lichong
     * @description: 根据id删除对象
     * @date: 2022/4/11
     */
    void deleteById(String id) throws Exception;

    /**
     * @param t
     * @author: lichong
     * @description: 根据对象的属性删除
     * @date: 2022/4/11
     */
    void deleteByCondition(T t);

    /**
     * @param id
     * @param t
     * @author: lichong
     * @description: 根据id进行更新
     * @date: 2022/4/11
     */
    void updateById(String id, T t);

    /**
     * @param t
     * @author: lichong
     * @description: 根据对象的属性查询
     * @date: 2022/4/11
     */
    List<T> findByCondition(T t);

    /**
     * @param query
     * @author: lichong
     * @description: 根据对象的属性查询
     * @date: 2022/4/11
     */
    List<T> find(Query query);

    /**
     * findByIds
     *
     * @param ids
     * @author: lichong
     * @description 根据对象Id包含查询
     * @date 2022/4/28
     */
    List<T> findByIds(List<String> ids);

    /**
     * @param query
     * @author: lichong
     * @description: 通过一定的条件查询一个实体
     * @date: 2022/4/11
     */
    T findOne(Query query);

    /**
     * @param t
     * @author: lichong
     * @description: 通过一定的条件查询一个实体
     * @date: 2022/4/11
     */
    T findOne(T t);

    /**
     * @param query
     * @param update
     * @author: lichong
     * @description: 通过条件查询更新数据
     * @date: 2022/4/11
     */
    void update(Query query, Update update);

    /**
     * @param id
     * @author: lichong
     * @description: 通过id查询数据
     * @date: 2022/4/11
     */
    T findById(String id);

    /**
     * @param id
     * @param collectionName 集合名
     * @author: lichong
     * @description: 通过ID获取记录, 并且指定了集合名(表的意思)
     * @date: 2022/4/11
     */
    T findById(String id, String collectionName);

    /**
     * @param pageVo
     * @param query
     * @author: lichong
     * @description: 分页查询
     * @date: 2022/4/11
     */
    PageVo<T> findPageVo(PageVo<T> pageVo, Query query);

    /**
     * @param pageVo
     * @param t         入参
     * @param like      是否模糊查询
     * @param orderList 排序字段
     * @author: lichong
     * @description: 分页查询
     * @date: 2022/4/11
     */
    PageVo<T> findPageVoByCondition(PageVo<T> pageVo, T t, Boolean like, List<Sort.Order> orderList);

    /**
     * @param query
     * @author: lichong
     * @description: 求数据总和
     * @date: 2022/4/11
     */
    long count(Query query);


    /**
     * @return MongoTemplate
     * @author: lichong
     * @description: 获取MongoDB模板操作
     * @date: 2022/4/11
     */
    MongoTemplate getMongoTemplate();

}
