package top.wecoding.core.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author liuyuhui
 * @date 2021/09/15
 * @qq 1515418211
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SocialUserRequest extends BaseRequest {

    private String uuid;

    private String source;

    private String openId;

}
