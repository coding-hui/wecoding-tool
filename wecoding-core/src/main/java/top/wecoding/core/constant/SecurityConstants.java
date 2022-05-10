package top.wecoding.core.constant;

/**
 * @author liuyuhui
 * @qq 1515418211
 */
public interface SecurityConstants {

    /**
     * 短信登录，传递的参数名称 手机号
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * 短信登录，传递的参数名称 验证码
     */
    String DEFAULT_PARAMETER_NAME_SMD_CODE = "sms-code";

    /**
     * 第三方登录，传递的参数名称
     */
    String DEFAULT_PARAMETER_NAME_SOCIAL = "social";

    /**
     * 项目的license
     */
    String PROJECT_LICENSE = "Power By WeCoding";

    /**
     * 客户端认证请求头前缀
     */
    String BASIC_HEADER_PREFIX = "Basic ";

    /**
     * 认证请求头前缀
     */
    String BASIC_HEADER_PREFIX_EXT = "Basic%20";

    /**
     * 登录用户
     */
    String LOGIN_USER = "login_user";

    /**
     * 用户ID字段
     */
    String DETAILS_USER_ID = "user_id";

    /**
     * 用户账号字段
     */
    String DETAILS_ACCOUNT = "account";

    /**
     * 用户姓名字段
     */
    String DETAILS_USERNAME = "realName";

    /**
     * 用户部门字段
     */
    String DETAILS_DEPT_ID = "dept_id";

    /**
     * 客户端字段
     */
    String DETAILS_CLIENT_ID = "client_id";

    /**
     * 协议字段
     */
    String DETAILS_LICENSE = "license";

    /**
     * 用户标识
     */
    String USER_KEY = "user_key";

    /**
     * 请求来源
     */
    String FROM_SOURCE = "from-source";

    /**
     * 内部请求
     */
    String INNER = "inner";

    /**
     * 客户端表字段
     */
    String CLIENT_FIELDS = "client_id, client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity_seconds, "
            + "refresh_token_validity_seconds, additional_information, autoapprove";

    /**
     * 客户端表查询语句
     */
    String BASE_STATEMENT = "SELECT " + CLIENT_FIELDS + " FROM sys_oauth_client";

    /**
     * 默认排序
     */
    String DEFAULT_FIND_STATEMENT = BASE_STATEMENT + " ORDER BY client_id";

    /**
     * 默认查询 client_id
     */
    String DEFAULT_SELECT_STATEMENT = BASE_STATEMENT + " WHERE client_id = ?";

    /**
     * 默认查询授权码
     */
    String DEFAULT_SELECT_OAUTH_CODE_STATEMENT = "SELECT code, authentication FROM sys_oauth_code WHERE code = ?";

    /**
     * 默认插入授权码
     */
    String DEFAULT_INSERT_OAUTH_CODE_STATEMENT = "INSERT INTO sys_oauth_code (code, authentication) VALUES (?, ?)";

    /**
     * 默认删除授权码
     */
    String DEFAULT_DELETE_OAUTH_CODE_STATEMENT = "DELETE FROM sys_oauth_code WHERE code = ?";

}
