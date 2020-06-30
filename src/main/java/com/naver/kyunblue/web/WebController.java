package com.naver.kyunblue.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// API서버와 REACT 서버를 같이 쓰면, Spring에서 url 매핑을 찾을 수 없어서 404 에러 발생
// 해당 에러 발생시 그냥 index.html로 매핑해줌으로써 해결

@Controller 
public class WebController implements ErrorController { 
	@GetMapping({"/", "/error"}) 
	public String index() {
		return "index.html"; 
	} 
	
	@Override public String getErrorPath() { 
		return "/error"; 
	} 
}

