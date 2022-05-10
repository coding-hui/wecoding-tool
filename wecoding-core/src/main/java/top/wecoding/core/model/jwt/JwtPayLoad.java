package top.wecoding.core.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JwtPayLoad
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtPayLoad {

    /**
     * 信息类型
     */
    private String tokenType;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 令牌过期秒数
     */
    private Long expireMillis;

}