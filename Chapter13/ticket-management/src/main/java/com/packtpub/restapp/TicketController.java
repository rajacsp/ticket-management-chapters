package com.packtpub.restapp;

import java.util.Arrays;
import java.util.Collections;
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

import com.packtpub.model.Ticket;
import com.packtpub.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	TicketService ticketSevice;

	@ResponseBody
	@RequestMapping("")
	public List<Ticket> getAllTicket() {
		return ticketSevice.getAllTickets();
	}
	
	@ResponseBody
	@RequestMapping("/by/user/{creatorid}")
	public List<Ticket> getMyTickets(@PathVariable("creatorid") final Integer creatorid) {
		return ticketSevice.getMyTickets(creatorid);
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	public <T> T addTicket(
			@RequestParam(value="creatorid") Integer creatorid,
			@RequestParam(value="content") String content,
			@RequestParam(value="severity") Integer severity,
			@RequestParam(value="status") Integer status
			) {
		ticketSevice.addTicket(creatorid, content, severity, status);
		
		Map<String, String> result = new LinkedHashMap<>();
		result.put("result", "added");
		
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