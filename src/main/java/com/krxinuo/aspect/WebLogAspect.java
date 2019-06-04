package com.krxinuo.aspect;

import com.krxinuo.entity.SysLogEntity;
import com.krxinuo.service.SysLogService;
import com.krxinuo.util.DateUtils;
import com.krxinuo.util.response.Code;
import com.krxinuo.util.response.ResultViewModel;
import net.sf.json.JSONObject;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by souyouyou on 2018/2/3.
 * 记录用户操作日志
 */
@Aspect
@Component
public class WebLogAspect {
    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SysLogEntity sysLog;


    @Pointcut("execution(* com.krxinuo.controller..*(..))")
    public void controllerLog(){}

//    @Pointcut("execution(public * com.krxinuo.uiController..*.*(..))")
//    public void uiControllerLog(){}

//    @Before("controllerLog() || uiControllerLog()")
    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint) {
        sysLog.setId(null);
        sysLog.setOptBefore(null);
        sysLog.setOptAfter(null);

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();


        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();

        Map<String,Object> params = new HashMap<>();
        //请求参数
        for (int i = 0; i < parameterNames.length; i++){
            params.put(parameterNames[i], parameterValues[i]);
        }

        Operation annotation = methodSignature.getMethod().getAnnotation(Operation.class);

        if (null != annotation){
            sysLog.setOptype(annotation.operationType());
            sysLog.setOptContent(annotation.operationName());
        }

        sysLog.setIp(request.getRemoteAddr());
        sysLog.setUrl(request.getRequestURL().toString());
        sysLog.setMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        sysLog.setOptime(DateUtils.formatTime(new Date()));
//        sysLog.setApiReq(JSONArray.fromObject(params).toString());



    }


    @AfterReturning(returning = "retObj", pointcut = "controllerLog()")
//    @AfterReturning(returning = "returnOb", pointcut = "controllerLog() || uiControllerLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object retObj) {

        if (retObj instanceof ResultViewModel){
            ResultViewModel resVM = (ResultViewModel)retObj;
            sysLog.setApiRes(JSONObject.fromObject(resVM).toString());
            sysLog.setOptRes(resVM.getCode().equals(Code.SUCCESS.getCode())?"操作成功":"操作失败");
        }else {
            //视图类
//            sysLog.setApiRes(retObj.toString());
            sysLog.setOptRes("操作成功");
        }

        sysLogService.saveLog(sysLog);

//        System.out.println("AfterReturn :"+JSONObject.fromObject(sysLog).toString());
    }


    @AfterThrowing(pointcut = "controllerLog()", throwing = "ex")
//    @AfterThrowing(pointcut = "controllerLog() || uiControllerLog()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());

        sysLog.setOptRes("操作失败");
        sysLog.setException(ex.getMessage());
        sysLogService.saveLog(sysLog);
        System.out.println("连接点方法为：" + methodName + ",参数为：" + args + ",异常为：" + ex);

    }
}
