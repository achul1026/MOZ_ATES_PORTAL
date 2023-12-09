package com.moz.ates.traffic.portal.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorResponse;
import com.moz.ates.traffic.common.support.exception.NoLoginException;

@ControllerAdvice
@RestControllerAdvice
public class PortalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView exceptionHandler(Exception e){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("views/common/ErrorPage");
		mav.addObject("message", e.getMessage());
		return mav;
	}
	
	// LoginCheckException
	@ExceptionHandler(value = NoLoginException.class)
	public String loginChkExceptionHandler(NoLoginException ne) {
		return "Login";
	}
	
	// customRuntimeException 
	@ExceptionHandler(value = CommonException.class)
	public ResponseEntity<ErrorResponse> portalExceptionHandler(CommonException ce, String message) {
		ErrorResponse response = new ErrorResponse(ce.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ce.getErrorCode().getStatus()));
	}
}
