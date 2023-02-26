package com.shanchui.hanlder;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *     字段自动填充
 *     填充判断:
 *          1、如果是主键，不填充
 *          2、根据字段名找不到字段，不填充
 *          3、字段类型与填充值类型不匹配，不填充
 *          4、字段类型需要在TableField注解里配置fill，@TableField(value = "create_by", fill = FieldFill.INSERT)
 *              没有配置或不匹配则不生效
 */

@Component
public class AutoFillHandler implements MetaObjectHandler {
    /**
     * 新增时填入值
     * @param metaObject
     **/
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = getUserId();
        this.strictInsertFill(metaObject, "lastUpdateTime", Date.class, new Date());
        // 创建人的填充
        this.strictInsertFill(metaObject, "createBy", Long.class, userId);
        this.strictInsertFill(metaObject, "created", Date.class, new Date());
    }

    /**
     * 修改时填入值
     * @param metaObject
     **/
    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = getUserId();
        this.strictUpdateFill(metaObject, "lastUpdateTime", Date.class, new Date());
        // 修改人的填充
        this.strictUpdateFill(metaObject, "modifyBy", Long.class, userId);
    }

    /**
     * <获取安全上下文里的用户对象 --- 主要是在线程里面获取改值
     **/
    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;
        if (authentication != null) {
            String principal = authentication.getPrincipal().toString();
            userId = Long.valueOf(principal);
        }
        return userId;
    }
}
