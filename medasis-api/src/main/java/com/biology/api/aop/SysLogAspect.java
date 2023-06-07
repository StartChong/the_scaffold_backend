package com.biology.api.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biology.api.annotation.SysLogAnnotation;
import com.biology.commons.base.Result;
import com.biology.commons.constant.Constants;
import com.biology.service.log.SysLogService;
import com.biology.commons.utils.SecurityUtils;
import com.biology.commons.utils.ip.IpUtils;
import com.biology.dao.mongo.log.SysLogEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-31 10:40
 * @desc: 操作日志切面处理类
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 设置操作日志切入点   在注解的位置切入代码
     */
    @Pointcut("@annotation(com.biology.api.annotation.SysLogAnnotation)")
    public void LogPointCut() {
    }

    /**
     * 记录操作日志
     *
     * @param joinPoint 方法的执行点
     * @param result    方法返回值
     * @throws Throwable
     */
    @AfterReturning(returning = "result", value = "LogPointCut()")
    public void saveLog(JoinPoint joinPoint, Object result) throws Throwable {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        assert requestAttributes != null;
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            SysLogEntity sysLogEntity = new SysLogEntity();
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取操作
            SysLogAnnotation annotation = method.getAnnotation(SysLogAnnotation.class);
            if (annotation != null) {
                sysLogEntity.setModel(annotation.module());
                sysLogEntity.setType(annotation.type());
                sysLogEntity.setDescription(annotation.desc());
            }

            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 类名.请求方法
            sysLogEntity.setMethod(methodName);
            // 操作时间
            sysLogEntity.setCreateDate(new Date());
            // 操作用户
            sysLogEntity.setUserId(SecurityUtils.getUserId());
            sysLogEntity.setUsername(SecurityUtils.getUsername());
            // 操作IP
            sysLogEntity.setIp(IpUtils.getIpAddr(request));
            assert request != null;
            // 请求URI
            sysLogEntity.setUrl(request.getRequestURI());
            // 方法请求的参数
            Map<String, String> rtnMap = convertMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            String params = JSON.toJSONString(rtnMap);
            //获取json的请求参数
            if (rtnMap == null || rtnMap.size() == 0) {
                params = getJsonStrByRequest(request);
            }
            // 请求参数
            sysLogEntity.setParams(params);
            // 返回值信息
            Result dataResult = (Result) result;
            //需要先判断返回值是不是Map <String, Object>，如果不是會拋異常，需要控制层的接口返回数据格式统一
            //如果嫌返回格式统一太麻烦建议日志保存时去掉操作结果
            sysLogEntity.setResult(dataResult.getMessage());

            sysLogEntity.setCreateTime(new Date());
            sysLogEntity.setCreateBy(Constants.SYS);
            sysLogEntity.setUpdateTime(new Date());
            sysLogEntity.setUpdateBy(Constants.SYS);
            //保存日志
            sysLogService.save(sysLogEntity);

        } catch (Exception e) {
            log.error("日志记录异常！" + e.getMessage());
        }
    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> convertMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * 获取json格式 请求参数
     */
    public String getJsonStrByRequest(HttpServletRequest request) {
        String param = null;
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }

            JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
            param = jsonObject.toJSONString();
            System.out.println(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }


    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strBuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strBuff.append(stet + "\n");
        }
        return exceptionName + ":" + exceptionMessage + "\n\t" + strBuff;
    }

}
