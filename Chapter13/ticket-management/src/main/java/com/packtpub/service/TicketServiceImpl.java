package com.packtpub.service;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packtpub.model.Ticket;
import com.packtpub.model.User;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	UserService userService;

	// Dummy tickets
	public static List<Ticket> tickets;

	public TicketServiceImpl() {
		tickets = new LinkedList<>();
		tickets.add(new Ticket(101, new Date(), "Login is not working", 5, 1));
		tickets.add(new Ticket(101, new Date(), "Submit button is not working", 5, 1));
		tickets.add(new Ticket(102, new Date(), "Registration is not working", 5, 1));
	}

	@Override
	public List<Ticket> getAllTickets() {
		return tickets;
	}

	@Override
	public List<Ticket> getMyTickets(Integer creatorid) {
		
		return tickets.stream()
				.filter(x -> x.getCreatorid() == creatorid)
				.collect(Collectors.toList())
				;
	}

	@Override
	public Ticket getTicket(Integer creatorid, Integer ticketid) {
		
		return tickets.stream()
				.filter(x -> x.getCreatorid()  == creatorid && x.getTicketid() == ticketid)
				.findAny()
				.orElse(null);
	}
	
	@Override
	public Ticket getTicket(Integer ticketid) {
		
		return tickets.stream()
				.filter(x -> x.getTicketid() == ticketid)
				.findAny()
				.orElse(null);
	}

	@Override
	public void addTicket(Integer creatorid, String content, Integer severity, Integer status) {
		Ticket ticket = new Ticket(creatorid, new Date(), content, severity, status);
		
		tickets.add(ticket);
	}

	@Override
	public void deleteTickets(Integer userid, String ticketids) throws Exception {
		
		User user = userService.getUser(userid);
		
		if(user == null){
			throw new Exception("User not available");
		}
		
		if(user.getUsertype() == 1){ //check for general user
			throw new Exception("User is not authorized to delete");
		}
		
		List<String> ticketObjList = Arrays.asList(ticketids.split(","));
		
		if(user.getUsertype() == 2 && ticketObjList.size() > 3){
			throw new Exception("CSR can't delete more than 3 tickets");
		}
		
		List<Integer> intList =
			ticketObjList.stream()
			.map(Integer::valueOf)
			.collect(Collectors.toList())
        ;					
		
		tickets.removeIf(x -> intList.contains(x.getTicketid()));
	}
}