package com.packtpub.restapp;

import java.util.LinkedList;
import java.util.List;

public class Test {

	public static void main1(String[] args){
		Test test = new Test();
		test.start1();
	}
	
	private void start(){
		List<Integer> tickets = new LinkedList<Integer> () {{
			add(1);
			add(2);
			add(3);
		}};
		
		List<Integer> intList = new LinkedList<Integer> () {{
			add(1);
			add(2);
		}};		
		
		tickets.removeIf(x -> intList.contains(x));
		
		System.out.println(tickets);
	}
	
	private void start1(){
		List<Ticket> tickets = new LinkedList<Ticket> () {{
			add(new Ticket(1, "one"));
			add(new Ticket(2, "two"));
			add(new Ticket(3, "three"));
		}};
		
		List<Integer> intList = new LinkedList<Integer> () {{
			add(1);
			add(2);
		}};		
		
		tickets.removeIf(x -> intList.contains(x.id));
		
		System.out.println(tickets.size());
	}
	
	class Ticket{
		int id;
		String name;
		
		public Ticket(int id, String name){
			this.id = id;
			this.name = name;
		}

		@Override
		public String toString() {
			return "Ticket [id=" + id + ", name=" + name + "]";
		}
	}	
}
