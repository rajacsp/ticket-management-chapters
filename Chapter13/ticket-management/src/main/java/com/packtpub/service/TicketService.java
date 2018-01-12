package com.packtpub.service;

import java.util.List;

import com.packtpub.model.Ticket;

public interface TicketService {

	List<Ticket> getAllTickets();
	
	List<Ticket> getMyTickets(Integer creatorid);
	
	void deleteMyTicket(Integer userid, Integer ticketid) throws Exception;

	Ticket getTicket(Integer creatorid, Integer ticketid);
	
	Ticket getTicket(Integer ticketid);
	
	void addTicket(Integer creatorid, String content, Integer severity, Integer status);
	
	void updateTicket(Integer ticketid, String content, Integer severity, Integer status);
	
	void deleteTickets(Integer userid, String ticketids) throws Exception;
}
