package net.bittx.h2.conf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bittx.h2.anno.Evt;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
@EnableAspectJAutoProxy
public class EvtAspect {
    private static final Logger logger = LoggerFactory.getLogger(EvtAspect.class);

    private static final String expr = "execution(public * net.bittx..h2.controller..*.*(..)) " +
            "&& @annotation(net.bittx.h2.anno.Evt)";

    //@Pointcut("execution(public * net.bittx..h2.controller..*.*(..))")
    //@Pointcut("execution(public * net.bittx..h2.controller..*.*(..))&& @annotation(net.bittx.h2.anno.Evt)")
    @Pointcut(expr)
    public void evt() {}

    // @Before("evt()")
    public void beforeAdvice(JoinPoint joinPoint) {

        // 得到参数列表: joinPoint.getArgs();
        // 得到目标对象: joinPoint.getTarget();
        // 得到方法名称: joinPoint.getSignature().getName();
        // 得到方法名称:
        //              Signature signature = joinPoint.getSignature();
        //              MethodSignature methodSignature = (MethodSignature)signature;
        //              Method targetMethod = methodSignature.getMethod();

        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ip = request.getRemoteAddr();
        int port = request.getRemotePort();


        Class targetClass = joinPoint.getTarget().getClass();

        // 当前访问的类路径(包含包名称)
        String targetName = targetClass.getName();
        // 当前访问的方法名称
        String methodName = joinPoint.getSignature().getName();

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();

        System.out.println(targetName);
        System.out.println(methodName);


        // 获取类中的公共方法
        //Class targetClass = Class.forName(targetName);

        Method[] methods = targetClass.getMethods();

        String params = "";
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            params = Arrays.toString(args);
        }

        String logName = "";
        String modelName = "";

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazz = method.getParameterTypes();
                if (clazz.length == args.length) {  // TODO: 可能需要使用Objects.deepEquals(method,args)
                    Evt anno = method.getAnnotation(Evt.class);
                    if (anno != null) {
                        logName = anno.value();
                        modelName = anno.code();
                    }
                    // 此处只是比较了参数的个数相同，可能需要使用Objects.deepEquals(method,args)
                    break;
                }
            }
        }

        // 通过类路径取得所有信息
        logger.info("name: {}", logName);
        logger.info("module: {}", modelName);
        logger.info("param: {}", params);
        logger.info("class: {}", targetName);
        logger.info("method: {}", methodName);

    }

    @After("evt()")
    public void afterAdvice() {
        System.out.println("after advice");
    }

    @AfterReturning(
            //pointcut = "execution(public * net.bittx..h2.controller..*.*(..))&& @annotation(net.bittx.h2.anno.Evt)",
            pointcut = "evt()",
            returning = "returningValue")
    public void afterReturning(JoinPoint jp, Object returningValue) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();

        Class targetClass = jp.getTarget().getClass();

        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        Object[] args = jp.getArgs();

            /*log.info("Request::  url:{},    method:{},   IP:{}, classMethod:{}.{}  params{}",
                    req.getRequestURL(),req.getMethod(),req.getRemoteHost(),
                    signature.getDeclaringTypeName(),signature.getName(),
                    Arrays.toString(jp.getArgs()));*/
        String src = targetClass.getName() + "." +  signature.getName();

        Evt be = targetMethod.getAnnotation(Evt.class);

        // Annotation
        String code = be.code();
        String comment = be.value();

        code = code.equals("")? args[0].toString(): code;
        comment = comment.equals("") ? args[1].toString():comment;

        Map<String, Object> payload = new HashMap<>();

        payload.put("createDate", LocalDateTime.now());
        payload.put("code", code);
        payload.put("comment", comment);
        payload.put("src",src);

        ObjectMapper om = new ObjectMapper();
        String rtn = null;
        try {
            rtn = om.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(rtn);
    }

    @AfterThrowing(pointcut = "evt()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        System.err.println("异常通知:" + e.getMessage());
    }

    //@Around("execution(public * net.bittx..h2.controller..*.*(..))")
    public void around(ProceedingJoinPoint pjp) {
        // 方法之前前置通知
        try {
            pjp.proceed();
            // 方法之后后置通知
        } catch (Throwable throwable) {
            // 异常通知 @AfterThrowing
            throwable.printStackTrace();
        } finally {
            // 最终通知 @After
        }
    }

}
