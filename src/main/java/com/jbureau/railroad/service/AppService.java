package com.jbureau.railroad.service;

import com.jbureau.railroad.exception.NotChildNodeException;
import com.jbureau.railroad.model.Node;

/**
 * Application service.
 * @author jbureau
 *
 */
public class AppService {
	
	/**
	 * Trip service.
	 */
	private TripService tripService;
	
	public AppService() {
		tripService = new TripService();
	}

	/**
	 * Gets the distance between stops.
	 * @param nodes stops
	 * @return the distance
	 */
	public String getDistance(Node... nodes) {
		try {
			return String.valueOf(tripService.getDistance(nodes));
		} catch (NotChildNodeException ex) {
			return "NO SUCH ROUTE";
		}
	}
	
	/**
	 * Gets the number of trips between 2 stops.
	 * @param start start node
	 * @param end end node
	 * @param max maximum of stops to do
	 * @return the number of trips
	 */
	public String getNumberTripWithMaxStops(Node start, Node end, int max) {
		return String.valueOf(tripService.getNumberTripWithMinMaxStops(start, end, 0, max));
	}
	

	/**
	 * Gets the number of trips between 2 stops.
	 * @param start start node
	 * @param end end node
	 * @param nb exactly number of stops to do
	 * @return the number of trips
	 */
	public String getNumberTripWithExactlyStops(Node start, Node end, int nb) {
		return String.valueOf(tripService.getNumberTripWithMinMaxStops(start, end, nb, nb));
	}
	
	/**
	 * Gets the lengths of the shortest route between 2 point.
	 * @param start start node
	 * @param end node
	 * @return the length of the shortest route
	 */
	public String getShortestRoute(Node start, Node end) {
		try {
			return String.valueOf(tripService.getShortestRoute(start, end));
		} catch (NotChildNodeException ex) {
			return "NO SUCH ROUTE";
		}
	}
	
	/**
	 * Gets the number of trip with a maximum length.
	 * @param start start node
	 * @param end end node
	 * @param length maximum length of routes
	 * @return the number of routes
	 */
	public String getNumberTripWithMaxLength(Node start, Node end, int length) {
		return String.valueOf(tripService.getNumberTripWithLength(start, end, 0, length));
	}
	
}
