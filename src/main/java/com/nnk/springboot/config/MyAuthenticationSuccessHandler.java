//package com.nnk.springboot.config;
//
//import java.io.IOException;
//import java.util.Collection;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import com.nnk.springboot.domain.User;
//import com.nnk.springboot.service.UserService;
//
//@Configuration
//public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
//	
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//			Authentication authentication) throws IOException, ServletException {
//		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		for (GrantedAuthority authority : authorities) {
//			System.out.println(authority.getAuthority());
//            if (authority.getAuthority().equals("ADMIN")) {
//                response.sendRedirect("/");
//                break;
//            } else if (authority.getAuthority().equals("USER")) {
//            	response.sendRedirect("/bidList/list");
//                break;
//            }
//        }
//	}
//
//}
