//package com.nnk.springboot.service;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
//import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
//import org.springframework.security.oauth2.core.OAuth2Error;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestOperations;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.client.UnknownContentTypeException;
//
//import com.nnk.springboot.domain.Customer;
//import com.nnk.springboot.domain.CustomerOAuth2;
//
//@Service
//public class CustomerOAuth2Service extends DefaultOAuth2UserService{
//	
////	private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {
////	};
////
////	private Converter<OAuth2UserRequest, RequestEntity<?>> requestEntityConverter = new OAuth2UserRequestEntityConverter();
////
////	private RestOperations restOperations = new RestTemplate();
////
////	@Autowired
////	private CustomerService customerService;
//	
//	@Override
//	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//		OAuth2User user = loadUser(userRequest);
//		System.out.println(user.getName());
////		Customer customer = customerService.getCustomerByName(user.getAttribute("login"));
////		System.out.println(customer.getName());
////		Map<String, Object> attributes = new HashMap<>();
////		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
////				.getUserNameAttributeName();
////		RequestEntity<?> request = this.requestEntityConverter.convert(userRequest);
////		ResponseEntity<Map<String, Object>> response = getResponse(userRequest, request);
////		Map<String, Object> userAttributes = response.getBody();
////		Customer customer = customerService.getCustomerByName((String) userAttributes.get(userNameAttributeName));
////		attributes.put("sub", customer.getName());
////		return new DefaultOAuth2AuthenticatedPrincipal(attributes,Collections.singletonList(new SimpleGrantedAuthority(customer.getRole())));
////		return new DefaultOAuth2User(Collections.singletonList(new SimpleGrantedAuthority(customer.getRole())), userAttributes, userNameAttributeName);
//		return new CustomerOAuth2(user);
//	}
//	
////	private ResponseEntity<Map<String, Object>> getResponse(OAuth2UserRequest userRequest, RequestEntity<?> request) {
////			return this.restOperations.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
////	}
//	
//	
//
//}
