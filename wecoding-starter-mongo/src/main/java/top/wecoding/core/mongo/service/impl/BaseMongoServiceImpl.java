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
package top.wecoding.core.mongo.service.impl;

import cn.hutool.core.convert.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import top.wecoding.core.factory.PageFactory;
import top.wecoding.core.model.request.PageRequest;
import top.wecoding.core.model.response.PageResult;
import top.wecoding.core.mongo.annotation.QueryField;
import top.wecoding.core.mongo.service.BaseMongoService;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

/**
 * Mongo 基类服务实现
 *
 * @author liuyuhui
 * @date 2022/02/19
 * @qq 1515418211
 */
public class BaseMongoServiceImpl<T> implements BaseMongoService<T> {

    @Autowired
    @Qualifier("mongoTemplate")
    protected MongoTemplate mongoTemplate;

    @Override
    public T save(T entity) {
        return mongoTemplate.save(entity);
    }

    @Override
    public Collection<T> saveBatch(Collection<T> entityList) {
        return mongoTemplate.insertAll(entityList);
    }

    @Override
    public void removeById(Serializable id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, this.getEntityClass());
    }

    @Override
    public void removeById(T entity) {
        mongoTemplate.remove(entity);
    }

    @Override
    public void remove(Query query) {
        mongoTemplate.remove(query, this.getEntityClass());
    }

    @Override
    public void remove(T entity) {
        Query query = buildBaseQuery(entity);
        mongoTemplate.remove(query, getEntityClass());
    }

    @Override
    public void updateById(Serializable id, T entity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = buildBaseUpdate(entity);
        update(query, update);
    }

    @Override
    public void update(Query updateQuery, T entity) {
        Update update = buildBaseUpdate(entity);
        update(updateQuery, update);
    }

    @Override
    public void update(Query updateQuery, Update update) {
        mongoTemplate.updateMulti(updateQuery, update, this.getEntityClass());
    }

    @Override
    public T getById(Serializable id) {
        return mongoTemplate.findById(id, this.getEntityClass());
    }

    @Override
    public List<T> listByIds(Collection<? extends Serializable> idList) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(idList));
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public T getOne(Query query) {
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    @Override
    public long count() {
        return mongoTemplate.count(new Query(), this.getEntityClass());
    }

    @Override
    public long count(Query query) {
        return mongoTemplate.count(query, this.getEntityClass());
    }

    @Override
    public List<T> list(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public List<T> list(T entity) {
        Query query = buildBaseQuery(entity);
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public List<T> list() {
        return mongoTemplate.findAll(this.getEntityClass());
    }

    @Override
    public PageResult page(PageRequest pageRequest, Query query) {
        query = query == null ? new Query(Criteria.where("_id").exists(true)) : query;
        int total = Convert.toInt(this.count(query));
        int pageSize = pageRequest.getPageSize();
        int current = Math.max(pageRequest.getCurrent(), 1) - 1;
        query.with(org.springframework.data.domain.PageRequest.of(current, pageSize));
        // 记录
        List<T> records = mongoTemplate.find(query, this.getEntityClass());
        return PageFactory.buildPageResult(records, total, pageRequest.getPageSize(), pageRequest.getCurrent());
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

    private Query buildBaseQuery(T t) {
        Query query = new Query();

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (value != null) {
                    QueryField queryField = field.getAnnotation(QueryField.class);
                    if (queryField != null) {
                        query.addCriteria(queryField.type().buildCriteria(queryField, field, value));
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return query;
    }

    private Class<T> getEntityClass() {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

}
