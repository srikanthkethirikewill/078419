package com.milkdistribution.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.milkdistribution.vo.RequestDTO;
import com.milkdistribution.vo.ResponseDTO;
import com.milkdistribution.vo.Result;

@Aspect
public class ControllerAspect {
	
	@Around("execution(public ResponseDTO com.milkdistribution.controller.MilkDistributionController.*(..))")
    public ResponseDTO<?> logAroundAllMethods(ProceedingJoinPoint joinPoint) throws Throwable 
    {
		ResponseDTO<?> responseDTO = null;
		Result result = null;
		try {
			Object[] parameters = joinPoint.getArgs();
			RequestDTO<?> requestDTO = (RequestDTO<?>)parameters[0];			
			result = new Result();			
			responseDTO = (ResponseDTO<?>)joinPoint.proceed();
			responseDTO.setHeader(requestDTO.getHeader());
			result.setMessage("success");
			responseDTO.setResult(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			responseDTO = new ResponseDTO<String>();
			result.setErrorCode("failure");
			responseDTO.setResult(result);
		}
		return responseDTO;
    }
}
