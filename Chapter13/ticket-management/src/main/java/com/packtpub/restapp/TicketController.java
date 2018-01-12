package com.packtpub.restapp;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.packtpub.model.Ticket;
import com.packtpub.model.User;
import com.packtpub.service.TicketService;
import com.packtpub.service.UserService;

@RestController
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	TicketService ticketSevice;
	
	@Autowired
	UserService userSevice;

	@ResponseBody
	@RequestMapping("")
	public List<Ticket> getAllTicket() {
		return ticketSevice.getAllTickets();
	}
	
		
	
	
	/*
	 * Rule:
	 * 		Only user can create a ticket
	 * 
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	public <T> T addTicket(			
			@RequestParam(value="content") String content,
			@RequestParam(value="severity") Integer severity,
			@RequestParam(value="status") Integer status,
			
			HttpServletRequest request,
			HttpServletResponse response
			) {
		
		String token = request.getHeader("token");		
		User user = userSevice.getUserByToken(token);
		
		if(user == null){
			Map<String, Object> map = new LinkedHashMap<>();
			
			map.put("result_code", 501);
			map.put("result", "User Not Available");
			
			return (T) map;
		}
		
		System.out.println("{addTicket} user["+user.getUserid()+"] adding ticket");
		
		ticketSevice.addTicket(user.getUserid(), content, severity, status);
		
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("result_code", 0);
		result.put("result", "added");
		
		return (T) result; 
	}
	
	@ResponseBody
	@RequestMapping("/my/tickets")
	public Map<String, Object> getMyTickets(
			HttpServletRequest request,
			HttpServletResponse response) {
		
		String token = request.getHeader("token");		
		User user = userSevice.getUserByToken(token);
		
		if(user == null){
			Map<String, Object> map = new LinkedHashMap<>();
			
			map.put("result_code", 501);
			map.put("result", "User Not Available");			
			return map;
		}
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		System.out.println("{getMyTickets} user : "+user);
		
		map.put("result_code", 0);
		map.put("result", "success");
		map.put("tickest", ticketSevice.getMyTickets(user.getUserid()));		
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/", method =  RequestMethod.DELETE)
	public <T> T deleteTicketByUser (
			@RequestParam("ticketid") final Integer ticketid,
			
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {
		
		String token = request.getHeader("token");		
		User user = userSevice.getUserByToken(token);
		
		if(user == null){
			Map<String, Object> map = new LinkedHashMap<>();
			
			map.put("result_code", 501);
			map.put("result", "User Not Available");			
			return (T) map;
		}
		
		ticketSevice.deleteMyTicket(user.getUserid(), ticketid);
		
		Map<String, String> result = new LinkedHashMap<>();
		result.put("result", "deleted");
		
		return (T) result; 
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/", method =  RequestMethod.PUT)
	public <T> T updateTicketByUser (
			@RequestParam("ticketid") final Integer ticketid,
			
			@RequestParam(value="content") String content,
			@RequestParam(value="severity") Integer severity,
			@RequestParam(value="status") Integer status,
			
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {
		
		User user = userSevice.getUserByToken(request.getHeader("token"));
		
		if(user == null){
			Map<String, Object> map = new LinkedHashMap<>();
			
			map.put("result_code", 501);
			map.put("result", "User Not Available");			
			return (T) map;
		}
		
		ticketSevice.updateTicket(ticketid, content, severity, status);
		
		Map<String, String> result = new LinkedHashMap<>();
		result.put("result", "updated");
		
		return (T) result; 
	}
	
	@ResponseBody
	@RequestMapping(value = "/by/csr", method =  RequestMethod.PUT)
	public <T> T updateTicketByCSR (
			@RequestParam("ticketid") final Integer ticketid,
			
			@RequestParam(value="content") String content,
			@RequestParam(value="severity") Integer severity,
			@RequestParam(value="status") Integer status,
			
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {
		
		User user = userSevice.getUserByToken(request.getHeader("token"));
		
		if(user == null){
			Map<String, Object> map = new LinkedHashMap<>();
			
			map.put("result_code", 501);
			map.put("result", "User Not Available");			
			return (T) map;
		}
		
		ticketSevice.updateTicket(ticketid, content, severity, status);
		
		Map<String, String> result = new LinkedHashMap<>();
		result.put("result", "updated");
		
		return (T) result; 
	}
	
	@ResponseBody
	@RequestMapping(value = "/by/admin", method =  RequestMethod.PUT)
	public <T> T updateTicketByAdmin (
			@RequestParam("ticketid") final Integer ticketid,
			
			@RequestParam(value="content") String content,
			@RequestParam(value="severity") Integer severity,
			@RequestParam(value="status") Integer status,
			
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {
		
		User user = userSevice.getUserByToken(request.getHeader("token"));
		
		if(user == null){
			Map<String, Object> map = new LinkedHashMap<>();
			
			map.put("result_code", 501);
			map.put("result", "User Not Available");			
			return (T) map;
		}
		
		ticketSevice.updateTicket(ticketid, content, severity, status);
		
		Map<String, String> result = new LinkedHashMap<>();
		result.put("result", "updated");
		
		return (T) result; 
	}
	
	@ResponseBody
	@RequestMapping(value = "/by/csr/{userid}", method =  RequestMethod.DELETE)
	public <T> T deleteTicketsByCSR (
			@PathVariable("userid") final Integer userid,
			@RequestParam("ticketids") final String ticketids
			) throws Exception {
		
		ticketSevice.deleteTickets(userid, ticketids);
		
		Map<String, String> result = new LinkedHashMap<>();
		result.put("result", "deleted");
		
		return (T) result; 
	}
	
	@ResponseBody
	@RequestMapping(value = "/by/admin/{userid}", method =  RequestMethod.DELETE)
	public <T> T deleteTicketsByAdmin (
			@PathVariable("userid") final Integer userid,
			@RequestParam("ticketids") final String ticketids
			) throws Exception {
		
		ticketSevice.deleteTickets(userid, ticketids);
		
		Map<String, String> result = new LinkedHashMap<>();
		result.put("result", "deleted");
		
		return (T) result; 
	}
}