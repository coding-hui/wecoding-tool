package top.wecoding.core.security.constant;

/**
 * @author liuyuhui
 * @date 2022
 * @qq 1515418211
 */
public class RoleConstant {

    public static final String ALL_PERMISSION = "*:*:*";

    public static final String ADMIN = "admin";

    public static final String HAS_ROLE_ADMIN = "hasAnyRole('" + ADMIN + "')";

    public static final String USER = "user";

    public static final String HAS_ROLE_USER = "hasAnyRole('" + USER + "')";

    public static final String TEST = "test";

    public static final String HAS_ROLE_TEST = "hasAnyRole('" + TEST + "')";

}
