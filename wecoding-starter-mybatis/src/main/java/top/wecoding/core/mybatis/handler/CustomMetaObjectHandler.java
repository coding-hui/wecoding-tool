package top.wecoding.core.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import top.wecoding.core.constant.StrPool;
import top.wecoding.core.context.login.LoginContextHolder;

import java.util.Date;

/**
 * 自定义sql字段填充器，自动填充创建修改相关字段
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
public class CustomMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_USER = "createUser";

    private static final String CREATE_TIME = "createTime";

    private static final String UPDATE_USER = "updateUser";

    private static final String UPDATE_TIME = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            // 设置createUser(BaseEntity)
            this.strictInsertFill(metaObject, CREATE_USER, this::getLoginUserName, String.class);

            // 设置createTime(BaseEntity)
            this.strictInsertFill(metaObject, CREATE_TIME, Date::new, Date.class);
        } catch (ReflectionException e) {
            log.warn(">>> CustomMetaObjectHandler 处理过程中无相关字段，不做处理");
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            // 设置createUser(BaseEntity)
            this.strictUpdateFill(metaObject, UPDATE_USER, this::getLoginUserName, String.class);

            // 设置createTime(BaseEntity)
            this.strictUpdateFill(metaObject, UPDATE_TIME, Date::new, Date.class);
        } catch (ReflectionException e) {
            log.warn(">>> CustomMetaObjectHandler 处理过程中无相关字段，不做处理");
        }
    }

    private String getLoginUserName() {
        try {
            return LoginContextHolder.me().getAccount();
        } catch (Exception e) {
            log.error(">>> 获取登录用户失败，具体信息为：{}", e.getLocalizedMessage());
        }
        return StrPool.HYPHEN;
    }

}