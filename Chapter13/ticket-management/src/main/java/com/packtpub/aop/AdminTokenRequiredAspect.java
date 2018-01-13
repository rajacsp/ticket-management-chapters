package com.packtpub.aop;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.packtpub.service.SecurityServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Aspect
@Component
public class AdminTokenRequiredAspect {
	
	@Before("@annotation(adminTokenRequired)")
	public void adminTokenRequiredWithAnnotation(AdminTokenRequired adminTokenRequired) throws Throwable{
		
		ServletRequestAttributes reqAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = reqAttributes.getRequest();
		
		// checks for token in request header
		String tokenInHeader = request.getHeader("token");
		
		if(StringUtils.isEmpty(tokenInHeader)){
			throw new RuntimeException("Empty token");
		}		
		
		Claims claims = Jwts.parser()         
			       .setSigningKey(DatatypeConverter.parseBase64Binary(SecurityServiceImpl.secretKey))
			       .parseClaimsJws(tokenInHeader).getBody();
		
		if(claims == null || claims.getSubject() == null){
			throw new RuntimeException("Token Error : Claim is null");
		}
		
		String subject = claims.getSubject();
		
		if(subject.split("=").length != 2 || new Integer(subject.split("=")[0]) != 3){
			throw new RuntimeException("User is not authorized");
		}		
	}
}
