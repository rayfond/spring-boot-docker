package net.bittx.h2.conf;

import net.bittx.h2.anno.Evt;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;


@Aspect
@Component
@EnableAspectJAutoProxy
public class EvtAspect {
    private static final Logger logger = LoggerFactory.getLogger(EvtAspect.class);

    @Pointcut("execution(public * net.bittx..h2.controller..*.*(..))")
    public void evt() {
    }

    @Before("evt()")
    public void beforeAdvice(JoinPoint joinPoint) {

        // 得到参数列表: joinPoint.getArgs();
        // 得到目标对象: joinPoint.getTarget();
        // 得到方法名称: joinPoint.getSignature().getName();
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ip = request.getRemoteAddr();
        int port = request.getRemotePort();


            Class targetClass = joinPoint.getTarget().getClass();

            // 当前访问的类路径
            String targetName = targetClass.getName();
            // 当前访问的方法名称
            String methodName = joinPoint.getSignature().getName();

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
            logger.info("name: {}",logName);
            logger.info("module: {}",modelName);
            logger.info("param: {}",params);
            logger.info("class: {}",targetName);
            logger.info("method: {}", methodName);

    }

    @After("evt()")
    public void afterAdvice() {
        System.out.println("after advice");
    }

    @AfterReturning(pointcut = "execution(public * net.bittx..h2.controller..*.*(..))",returning = "returningValue")
    public void afterReturning(JoinPoint jp, Object returningValue) {
        //
    }

    @AfterThrowing(pointcut = "evt()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        System.err.println("异常通知:" + e.getMessage());
    }

    //@Around("execution(public * net.bittx..h2.controller..*.*(..))")
    public void around(ProceedingJoinPoint pjp){
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
