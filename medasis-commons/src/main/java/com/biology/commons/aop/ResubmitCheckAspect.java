package com.biology.commons.aop;

import com.biology.commons.annotation.ResubmitCheck;
import com.biology.commons.constant.Constants;
import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.exception.SysException;
import com.biology.commons.utils.ExpressionUtils;
import com.biology.commons.utils.RedisUtil;
import com.biology.commons.utils.SecurityUtils;
import lombok.NonNull;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-24 18:22
 * @desc: 防重复提交切面
 */
@Component
@Aspect
@Order(-1)
@ConditionalOnProperty(name = "enabled", prefix = "resubmit-check", havingValue = "true", matchIfMissing = true)
public class ResubmitCheckAspect {


    private static final String REDIS_SEPARATOR = "::";

    private static final String RESUBMIT_CHECK_KEY_PREFIX = "resubmitCheckKey" + REDIS_SEPARATOR;

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private HttpServletRequest request;

    @Before("@annotation(annotation)")
    public Object resubmitCheck(JoinPoint joinPoint, ResubmitCheck annotation) throws Throwable {

        final Object[] args = joinPoint.getArgs();
        final String[] conditionExpressions = annotation.conditionExpressions();

        //根据条件判断是否需要进行防重复提交检查
        if (!ExpressionUtils.getConditionValue(args, conditionExpressions) || ArrayUtils.isEmpty(args)) {
            return ((ProceedingJoinPoint) joinPoint).proceed();
        }
        doCheck(annotation, args);
        return ((ProceedingJoinPoint) joinPoint).proceed();
    }

    /**
     * key的组成为: prefix::userInfo::sessionId::uri::method::(根据spring EL表达式对参数进行拼接)
     *
     * @param annotation 注解
     * @param args       方法入参
     */
    private void doCheck(@NonNull ResubmitCheck annotation, Object[] args) {
        final String[] argExpressions = annotation.argExpressions();
        final String message = annotation.message();
        final boolean withUserInfoInKey = annotation.withUserInfoInKey();
        final boolean onlyInCurrentSession = annotation.onlyInCurrentSession();

        String methodDesc = request.getMethod();
        String uri = request.getRequestURI();

        StringBuilder stringBuilder = new StringBuilder(64);
        Object[] argsForKey = ExpressionUtils.getExpressionValue(args, argExpressions);
        for (Object obj : argsForKey) {
            stringBuilder.append(obj.toString());
        }

        StringBuilder keyBuilder = new StringBuilder();
        //userInfo一般从token中获取,可以使用当前登录的用户id作为标识
        keyBuilder.append(RESUBMIT_CHECK_KEY_PREFIX)
                //userInfo一般从token中获取,可以使用当前登录的用户id作为标识
                .append(withUserInfoInKey ? SecurityUtils.getUserId() + REDIS_SEPARATOR : "").append(onlyInCurrentSession ? request.getSession().getId() + REDIS_SEPARATOR : "").append(uri).append(REDIS_SEPARATOR).append(methodDesc).append(REDIS_SEPARATOR).append(stringBuilder);
        SysException.throwException(Objects.nonNull(redisUtil.getCacheObject(keyBuilder.toString())), ResultCodeEnum.SUBMIT_REPEAT.code(), StringUtils.isBlank(message) ? Constants.RESUBMIT_MSG : message);
        //值为空
        redisUtil.setCacheObject(keyBuilder.toString(), annotation.interval());

    }
}