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
package top.wecoding.core.exception.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import top.wecoding.core.constant.StrPool;
import top.wecoding.core.exception.ArgumentException;
import top.wecoding.core.exception.BizException;
import top.wecoding.core.exception.code.ClientErrorCodeEnum;
import top.wecoding.core.exception.code.SystemErrorCodeEnum;
import top.wecoding.core.exception.user.ForbiddenException;
import top.wecoding.core.exception.user.InnerAuthException;
import top.wecoding.core.exception.user.UnauthorizedException;
import top.wecoding.core.model.response.Response;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理基类
 *
 * @author liuyuhui
 * @qq 1515418211
 */
@Slf4j
public abstract class AbstractGlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response bizException(BizException e) {
        log.warn(" >>> 业务异常:", e);
        return Response.buildFailure(e.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArgumentException.class)
    public Response argumentException(ArgumentException e) {
        log.warn(" >>> 参数异常:", e);
        return Response.buildFailure(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response forbiddenException(ForbiddenException e) {
        log.warn(" >>> 禁止访问异常:", e);
        return Response.buildFailure(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response unauthorizedException(UnauthorizedException e) {
        log.warn(" >>> 认证失败异常:", e);
        return Response.buildFailure(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(InnerAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response handleAuthorizationException(InnerAuthException e) {
        log.warn(" >>> 内部权限异常，具体信息为：{}", e.getMessage());
        return Response.buildFailure(e.getErrorCode(), e.getMessage());
    }

    /**
     * 请求参数缺失异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response missParamException(MissingServletRequestParameterException e) {
        log.warn(">>> 请求参数异常，具体信息为：{}", e.getMessage());
        String parameterType = e.getParameterType();
        String parameterName = e.getParameterName();
        String message = StrUtil.format(">>> 缺少请求的参数{}，类型为{}", parameterName, parameterType);
        return Response.buildFailure(ClientErrorCodeEnum.REQUEST_PARAMETER_ERROR, message);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response missingServletRequestPartException(MissingServletRequestPartException e) {
        log.warn(" >>> MissingServletRequestPartException:", e);
        return Response.buildFailure(ClientErrorCodeEnum.REQUIRED_FILE_PARAM);
    }

    /**
     * 拦截参数格式传递异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response httpMessageNotReadable(HttpMessageNotReadableException e) {
        log.warn(">>> 参数格式传递异常，具体信息为：", e);
        return Response.buildFailure(ClientErrorCodeEnum.REQUEST_PARAMETER_ERROR);
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response multipartException(MultipartException ex) {
        log.warn(" >>> MultipartException:", ex);
        return Response.buildFailure(ClientErrorCodeEnum.REQUIRED_FILE_PARAM);
    }

    /**
     * 拦截请求方法的异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response methodNotSupport(HttpServletRequest request) {
        if (ServletUtil.isPostMethod(request)) {
            log.warn(">>> 请求方法异常，不支持该请求方法，请求方法应为 GET");
            return Response.buildFailure(ClientErrorCodeEnum.REQUEST_METHOD_IS_GET);
        }
        if (ServletUtil.isGetMethod(request)) {
            log.warn(">>> 请求方法异常，不支持该请求方法，请求方法应为 POST");
            return Response.buildFailure(ClientErrorCodeEnum.REQUEST_METHOD_IS_POST);
        }
        if (ServletUtil.METHOD_DELETE.equalsIgnoreCase(request.getMethod())) {
            log.warn(">>> 请求方法异常，不支持该请求方法，请求方法应为 DELETE");
            return Response.buildFailure(ClientErrorCodeEnum.REQUEST_METHOD_IS_DELETE);
        }
        if (ServletUtil.METHOD_PUT.equalsIgnoreCase(request.getMethod())) {
            log.warn(">>> 请求方法异常，不支持该请求方法，请求方法应为 PUT");
            return Response.buildFailure(ClientErrorCodeEnum.REQUEST_METHOD_IS_PUT);
        }
        log.warn(">>> 请求方法异常，不支持该请求方法.");
        return Response.buildFailure(ClientErrorCodeEnum.REQUEST_METHOD_NOT_ALLOWED);
    }

    /**
     * 拦截不支持媒体类型异常
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response httpMediaTypeNotSupport(HttpMediaTypeNotSupportedException e) {
        log.warn(">>> 参数格式传递异常，具体信息为：", e);
        return Response.buildFailure(ClientErrorCodeEnum.REQUEST_TYPE_IS_JSON);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response validatedBindException(MethodArgumentNotValidException e) {
        log.warn(" >>> MethodArgumentNotValidException:", e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return Response.buildFailure(ClientErrorCodeEnum.REQUEST_PARAMETER_ERROR, message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response illegalArgumentException(IllegalArgumentException e) {
        log.warn(" >>> IllegalArgumentException:", e);
        return Response.buildFailure(ClientErrorCodeEnum.REQUEST_PARAMETER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response illegalStateException(IllegalStateException e) {
        log.warn(" >>> IllegalStateException:", e);
        return Response.buildFailure(ClientErrorCodeEnum.REQUEST_PARAMETER_ERROR);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response validatedBindException(BindException e) {
        log.warn(" >>> BindException:", e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return Response.buildFailure(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response constraintViolationException(ConstraintViolationException e) {
        log.warn(" >>> ConstraintViolationException:", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(StrPool.SEMICOLON));
        return Response.buildFailure(message);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response nullPointerException(NullPointerException e) {
        log.warn(" 空指针异常:", e);
        return Response.buildFailure(SystemErrorCodeEnum.REQUEST_NULL_POINT);
    }

    // @ExceptionHandler(PersistenceException.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // public Response persistenceException(PersistenceException e) {
    //     log.warn(" >>> PersistenceException:", e);
    //     if (e.getCause() instanceof BizException) {
    //         BizException cause = (BizException) e.getCause();
    //         return Response.buildFailure(cause.getErrorCode(), cause.getErrorMessage());
    //     }
    //     return Response.buildFailure(SystemErrorCodeEnum.SQL_ERROR);
    // }

    // @ExceptionHandler(MyBatisSystemException.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // public Response myBatisSystemException(MyBatisSystemException e) {
    //     log.warn(" >>> PersistenceException:", e);
    //     if (e.getCause() instanceof PersistenceException) {
    //         return this.persistenceException((PersistenceException) e.getCause());
    //     }
    //     return Response.buildFailure(SystemErrorCodeEnum.SQL_ERROR);
    // }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response sqlException(SQLException e) {
        log.warn(" >>> 运行SQL异常:", e);
        return Response.buildFailure(SystemErrorCodeEnum.SQL_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response dataIntegrityViolationException(DataIntegrityViolationException e) {
        log.warn(" >>> dataIntegrityViolationException:", e);
        return Response.buildFailure(SystemErrorCodeEnum.SQL_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response serverErrorException(Throwable e) {
        log.warn(" >>> 服务器运行异常:", e);
        if (e.getCause() instanceof BizException) {
            return this.bizException((BizException) e.getCause());
        }
        return Response.buildFailure(SystemErrorCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleException(Exception e) {
        log.warn(">>> 服务器运行异常:", e);
        if (e.getCause() instanceof BizException) {
            return this.bizException((BizException) e.getCause());
        }
        return Response.buildFailure(SystemErrorCodeEnum.SYSTEM_ERROR);
    }

}
