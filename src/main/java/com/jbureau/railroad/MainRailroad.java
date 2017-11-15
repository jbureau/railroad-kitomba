package com.jbureau.railroad;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jbureau.railroad.model.Node;
import com.jbureau.railroad.service.AppService;

/**
 * 
 * @author jbureau
 *
 */
public class MainRailroad {
	
	public static void main(String[] args) {
		
		Logger logger = Logger.getLogger("Railroad App");
		
		logger.info("Application started");
		
		Node a = new Node(1, "A");
		Node b = new Node(2, "B");
		Node c = new Node(3, "C");
		Node d = new Node(4, "D");
		Node e = new Node(5, "E");		

		a.addChild(b, 5);
		a.addChild(d, 5);
		a.addChild(e, 7);
		b.addChild(c, 4);
		c.addChild(d, 8);
		c.addChild(e, 2);
		d.addChild(c, 8);
		d.addChild(e, 6);
		e.addChild(b, 3);	
		
		AppService service = new AppService();

		logger.log(Level.INFO, "Output #1: {0}", service.getDistance(a, b, c));
		logger.log(Level.INFO, "Output #2: {0}", service.getDistance(a, d));
		logger.log(Level.INFO, "Output #3: {0}", service.getDistance(a, d, c));
		logger.log(Level.INFO, "Output #4: {0}", service.getDistance(a, e, b, c, d));
		logger.log(Level.INFO, "Output #5: {0}", service.getDistance(a, e, d));
		logger.log(Level.INFO, "Output #6: {0}", service.getNumberTripWithMaxStops(c, c, 3));
		logger.log(Level.INFO, "Output #7: {0}", service.getNumberTripWithExactlyStops(a, c, 4));
		logger.log(Level.INFO, "Output #8: {0}", service.getShortestRoute(a, c));
		logger.log(Level.INFO, "Output #9: {0}", service.getShortestRoute(b, b));
		logger.log(Level.INFO, "Output #10: {0}", service.getNumberTripWithMaxLength(c, c, 30));
		

		logger.info("Application ended");
		
	} 

}
