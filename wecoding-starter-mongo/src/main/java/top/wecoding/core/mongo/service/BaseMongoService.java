/*
 * Copyright (c) 2022. WeCoding (wecoding@yeah.net).
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.wecoding.core.mongo.service;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import top.wecoding.core.model.request.PageRequest;
import top.wecoding.core.model.response.PageResult;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * MongoDB 通用服务接口
 *
 * @author liuyuhui
 * @date 2022/02/19
 * @qq 1515418211
 */
public interface BaseMongoService<T> {

    /**
     * 插入一条记录 MongoDB
     *
     * @param entity 实体对象
     */
    T save(T entity);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    Collection<T> saveBatch(Collection<T> entityList);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    void removeById(Serializable id);

    /**
     * 根据实体(ID)删除
     *
     * @param entity 实体
     */
    void removeById(T entity);

    /**
     * 根据 Query 条件，删除记录
     *
     * @param query 查询包装类 {@link Query}
     */
    void remove(Query query);

    /**
     * 根据 entity 属性，删除记录
     *
     * @param entity 实体
     */
    void remove(T entity);

    /**
     * 根据 ID 选择修改
     *
     * @param id     id
     * @param entity 实体对象
     */
    void updateById(Serializable id, T entity);

    /**
     * 根据 updateQuery 条件，更新记录
     *
     * @param updateQuery 查询包装类 {@link Query}
     * @param entity      实体对象
     */
    void update(Query updateQuery, T entity);

    /**
     * 通过条件查询更新数据
     *
     * @param updateQuery 查询包装类 {@link Query}
     * @param update      更新包装类 {@link Update}
     */
    void update(Query updateQuery, Update update);

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    T getById(Serializable id);

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     */
    List<T> listByIds(Collection<? extends Serializable> idList);

    /**
     * 根据 Query 条件，查询一个实体
     *
     * @param query 查询包装类 {@link Query}
     */
    T getOne(Query query);

    /**
     * 查询总记录数
     */
    long count();

    /**
     * 根据 Query 条件，查询总记录数
     *
     * @param query 查询包装类 {@link Query}
     */
    long count(Query query);

    /**
     * 查询列表
     *
     * @param query 查询包装类 {@link Query}
     */
    List<T> list(Query query);

    /**
     * 根据 entity 属性，查询列表
     *
     * @param entity 实体对象
     */
    List<T> list(T entity);

    /**
     * 查询所有
     */
    List<T> list();

    /**
     * 翻页查询
     *
     * @param pageRequest 翻页查询对象
     * @param query       查询包装类 {@link Query}
     * @return pageResult
     */
    PageResult page(PageRequest pageRequest, Query query);

}
