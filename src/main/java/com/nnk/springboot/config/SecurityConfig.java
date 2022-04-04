package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//import com.nnk.springboot.service.CustomerOAuth2Service;
//import com.nnk.springboot.service.CustomerOAuth2Service;
import com.nnk.springboot.service.UserDetServ;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	

	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetServ();
	}
	
	@Bean
	public PasswordEncoder  passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable()
	            .authorizeRequests()
	            .antMatchers("/login","/register","/js/**","css/*","css/**",
                        "static/css/*",
                        "/img/**","/error").permitAll()
	            .antMatchers("/user/**").hasAnyAuthority("ADMIN")
	            .anyRequest().authenticated()
	            .and()
	            .formLogin()
	        	.loginPage("/login")
	            .successHandler(myAuthenticationSuccessHandler)
	            .permitAll()
	            .and()
	            .oauth2Login()
	            .loginPage("/login")
	            .and()
	            .logout()
	            .permitAll();
			
			http.logout()
            	.logoutUrl("/logout");
	    }
	
		@Override
	    public void configure(WebSecurity web) throws Exception {
	        //Web resources
	        web.ignoring().antMatchers("/css/**");
	    }

}
