package com.packtpub.restapp;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.packtpub.aop.TokenRequired;
import com.packtpub.model.User;
import com.packtpub.service.SecurityService;
import com.packtpub.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userSevice;
	
	@Autowired
	SecurityService securityService;

	@ResponseBody
	@RequestMapping("")
	//@UserTokenRequired
	public List<User> getAllUsers() {
		return userSevice.getAllUsers();
	}

	@ResponseBody
	@RequestMapping("/{id}")
	public User getUser(@PathVariable("id") Integer id) {
		return userSevice.getUser(id);
	}

	/*
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Map<String, Object> createUser(
			@RequestParam(value = "userid") Integer userid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "username") Integer usertype) {
		Map<String, Object> map = new LinkedHashMap<>();
		userSevice.createUser(userid, username, usertype);
		map.put("result", "added");
		return map;
	}
	*/
	
	@ResponseBody
	@RequestMapping(value = "/register/admin", method = RequestMethod.POST)
	public Map<String, Object> registerAdmin(			
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
		) {
		Map<String, Object> map = new LinkedHashMap<>();
		userSevice.createUser(username, password, 3);
		map.put("result", "added");
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/login/admin", method = RequestMethod.POST)
	public Map<String, Object> loginAdmin(			
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
		) {
		Map<String, Object> map = new LinkedHashMap<>();
		
		User user = userSevice.getUser(username, password, 3);
		
		if(user == null){
			map.put("result_code", 501);
			map.put("result", "User Not Available");			
			return map;
		}
		
		String subject = user.getUserid()+"="+user.getUsertype();
		String token = securityService.createToken(subject, (15 * 1000 * 60)); // 15 mins expiry time
		
		map.put("result_code", 0);
		map.put("result", "success");
		map.put("token", token);
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/login/customer", method = RequestMethod.POST)
	public Map<String, Object> loginCustomer(			
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
		) {
		Map<String, Object> map = new LinkedHashMap<>();
		
		User user = userSevice.getUser(username, password, 1);
		
		if(user == null){
			map.put("result_code", 501);
			map.put("result", "User Not Available");			
			return map;
		}
		
		String subject = user.getUserid()+"="+user.getUsertype();
		
		System.out.println("{loginCSR} subject : "+subject);
		
		String token = securityService.createToken(subject, (15 * 1000 * 60)); // 15 mins expiry time
		
		map.put("result_code", 0);
		map.put("result", "success");
		map.put("token", token);
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/login/csr", method = RequestMethod.POST)
	public Map<String, Object> loginCSR(			
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
		) {
		Map<String, Object> map = new LinkedHashMap<>();
		
		User user = userSevice.getUser(username, password, 2);
		
		if(user == null){
			map.put("result_code", 501);
			map.put("result", "User Not Available");			
			return map;
		}
		
		String subject = user.getUserid()+"="+user.getUsertype();
		
		System.out.println("{loginCSR} subject : "+subject);
		
		String token = securityService.createToken(subject, (15 * 1000 * 60)); // 15 mins expiry time
		
		map.put("result_code", 0);
		map.put("result", "success");
		map.put("token", token);
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/register/customer", method = RequestMethod.POST)
	public Map<String, Object> registerCustomer(			
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
		) {
		Map<String, Object> map = new LinkedHashMap<>();
		userSevice.createUser(username, password, 1);
		
		map.put("result_code", 0);
		map.put("result", "success");
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/register/csr", method = RequestMethod.POST)
	public Map<String, Object> registerCSR(			
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password
		) {
		Map<String, Object> map = new LinkedHashMap<>();
		userSevice.createUser(username, password, 2);
		map.put("result", "added");
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Map<String, Object> updateUser(@RequestParam(value = "userid") Integer userid,
			@RequestParam(value = "username") String username) {
		Map<String, Object> map = new LinkedHashMap<>();
		userSevice.updateUser(userid, username);
		map.put("result", "updated");
		return map;
	}

	@ResponseBody
	@TokenRequired
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteUser(
			@PathVariable("id") Integer userid) {
		Map<String, Object> map = new LinkedHashMap<>();   
	    userSevice.deleteUser(userid);    
	    map.put("result", "deleted");
	    return map;
	}
}