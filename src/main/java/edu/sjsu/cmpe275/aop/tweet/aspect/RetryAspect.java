package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;

import java.io.IOException;
import java.util.UUID;

@Aspect
@Order(0)
public class RetryAspect {

	@Around("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.*(..))")
	public UUID retryAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		UUID result = null;
		for(int i = 0; i < 4; i++){
			try{
				result = (UUID)joinPoint.proceed();
				break;
			} catch(IOException ioException){
				if(i == 3){
					throw new IOException("Method execution failed after trying 4 times");
				}
			}

		}
		return result;
	}

}
