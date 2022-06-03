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
package top.wecoding.core.auth.model;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.tree.Tree;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author liuyuhui
 * @date 2021/08/15
 * @qq 1515418211
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户 uuid，用于缓存登录用户的唯一凭证
     */
    private String uuid;

    /**
     * 主键
     */
    private Long userId;

    /**
     * 部门
     */
    private Long deptId;

    /**
     * 客户端
     */
    private String clientId;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别(字典 1男 2女 3未知)
     */
    private String gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 最后登陆IP
     */
    private String lastLoginIp;

    /**
     * 最后登陆时间
     */
    private Date lastLoginTime;

    /**
     * 用户类型（1超级管理员 6非管理员）
     */
    private String userType;

    /**
     * 状态（字典 0正常 1锁定）
     */
    private String status;

    /**
     * 最后登陆地址
     */
    private String loginLocation;

    /**
     * 最后登陆所用浏览器
     */
    private String lastLoginBrowser;

    /**
     * 最后登陆所用系统
     */
    private String lastLoginOs;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色信息
     */
    private Set<Dict> roles;

    /**
     * 角色编码集合
     */
    private Set<String> roleKeys;

    /**
     * 数据范围信息
     */
    private List<Long> dataScopes;

    /**
     * 登录菜单信息
     */
    private List<Tree<Long>> menus;

}
