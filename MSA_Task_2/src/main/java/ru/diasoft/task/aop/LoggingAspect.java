package ru.diasoft.task.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Around(value = "execution(public ru.diasoft.task.Greeting greeting(..))")
    public Object aroundGreetingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        Object[] arguments = proceedingJoinPoint.getArgs();

        System.out.println("Input params are ... ");

        for(Object obj : arguments) {
            System.out.println("arg = " + obj);
        }

        Object result = proceedingJoinPoint.proceed();

        System.out.println("Output params are " + result);

        return result;

    }

}
