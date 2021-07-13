package cn.latiny.aspect;

import cn.latiny.utils.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Aspect
public class ResponseAspect {
    private static final Logger logger = LoggerFactory.getLogger(ResponseAspect.class);
    private static Set<MediaType> formatMediaType = new HashSet();

    static {
        formatMediaType.add(MediaType.APPLICATION_JSON);
    }

    public ResponseAspect() {
    }

    @Pointcut("execution(* cn.latiny.web(..))")
    public void serviceAspect() {
    }

    @Before("serviceAspect()")
    public void doAfter(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        logger.info("api input. {}, method: {}, args: {}", new Object[]{method.getDeclaringClass(), method.getName(), this.getArgs(joinPoint)});
    }

    @AfterReturning(
            pointcut = "serviceAspect() ",
            returning = "returnValue"
    )
    public void doAfter(JoinPoint joinPoint, Object returnValue) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (returnValue instanceof ResponseEntity) {
            ResponseEntity responseEntity = (ResponseEntity) returnValue;
            MediaType mediaType = responseEntity.getHeaders().getContentType();
            if (!formatMediaType.contains(mediaType)) {
                logger.info("api output. ignore mediaType: {}", mediaType);
                return;
            }
        }

        logger.info("api output. {}, method: {}, args: {}", new Object[]{method.getDeclaringClass(), method.getName(), JsonUtils.toJson(returnValue)});
    }

    private String getArgs(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        List<Object> json = new ArrayList();
        Object[] var4 = args;
        int var5 = args.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            Object arg = var4[var6];
            if (!(arg instanceof HttpServletResponse) && !(arg instanceof HttpServletRequest)) {
                json.add(arg);
            }
        }

        return JsonUtils.toJson(json);
    }
}