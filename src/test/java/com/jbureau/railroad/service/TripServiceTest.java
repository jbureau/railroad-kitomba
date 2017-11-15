package com.jbureau.railroad.service;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.jbureau.railroad.exception.NotChildNodeException;
import com.jbureau.railroad.model.Node;

/**
 * Test of TripService
 * @author jbureau
 *
 */
public class TripServiceTest {
	
	private Node a, b, c, d, e;
	private TripService service;
	
	@Before
	public void beforeTest() {
		a = new Node(1, "A");
		b = new Node(2, "B");
		c = new Node(3, "C");
		d = new Node(4, "D");
		e = new Node(5, "E");
		a.addChild(b, 5);
		a.addChild(d, 5);
		a.addChild(e, 7);
		b.addChild(c, 4);
		c.addChild(d, 8);
		c.addChild(e, 2);
		d.addChild(c, 8);
		d.addChild(e, 6);
		e.addChild(b, 3);	
		service = new TripService();
	}

	@Test
	public void testABC() throws NotChildNodeException {
		int result = service.getDistance(a, b, c);
		assertTrue(result == 9);
	}

	@Test
	public void testAD() throws NotChildNodeException {
		int result = service.getDistance(a, d);
		assertTrue(result == 5);
	}

	@Test
	public void testADC() throws NotChildNodeException {
		int result = service.getDistance(a, d, c);
		assertTrue(result == 13);
	}

	@Test
	public void testAEBCD() throws NotChildNodeException {
		int result = service.getDistance(a, e, b, c, d);
		assertTrue(result == 22);
	}

	@Test (expected = NotChildNodeException.class)
	public void testAED() throws NotChildNodeException {
		service.getDistance(a, e, d);
	}


	@Test
	public void testNbTripStopsMax3CC() throws NotChildNodeException {		
		int result = service.getNumberTripWithMinMaxStops(c, c, 0, 3);
		assertTrue(result == 2);
	}

	@Test
	public void testNbTripStops4AC() throws NotChildNodeException {		
		int result = service.getNumberTripWithMinMaxStops(a, c, 4, 4);
		assertTrue(result == 3);
	}


	@Test
	public void testNbTripWithLengthMax30CC() throws NotChildNodeException {		
		int result = service.getNumberTripWithLength(c, c, 0, 30);
		assertTrue(result == 7);
	}

	@Test
	public void testShortestRouteAC() throws NotChildNodeException {		
		int result = service.getShortestRoute(a, c);
		assertTrue(result == 9);
	}

	@Test
	public void testShortestRouteBB() throws NotChildNodeException {		
		int result = service.getShortestRoute(b, b);
		assertTrue(result == 9);
	}

	@Test
	public void testNbTripWithLengthMax10ED() throws NotChildNodeException {		
		int result = service.getNumberTripWithLength(e, d, 0, 10);
		assertTrue(result == 0);
	}

	@Test (expected = NotChildNodeException.class)
	public void testShortestRouteDA() throws NotChildNodeException {		
		service.getShortestRoute(d, a);
	}
	

}
