package com.jbureau.railroad.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jbureau.railroad.exception.NotChildNodeException;
import com.jbureau.railroad.model.Node;

/**
 * Service for trips.
 * 
 * @author jbureau
 *
 */
public class TripService {

	/**
	 * Gets the distance between the stops given in the same order.
	 * 
	 * @param nodes stops of the trip
	 * @return distance of the trip
	 * @throws NotChildNodeException if a road between 2 stops have not been found
	 */
	public int getDistance(Node... nodes) throws NotChildNodeException {
		return computeDistance(nodes);
	}

	/**
	 * Gets the distance between the stops given in the same order (recursive method).
	 * 
	 * @param nodes stops of the trip
	 * @return distance of the trip
	 * @throws NotChildNodeException if a road between 2 stops have not been found
	 */
	private int computeDistance(Node... nodes) throws NotChildNodeException {
		int distance = 0;
		if (nodes.length > 1) { // We need at least 2 nodes
			if (!nodes[0].getChildren().containsKey(nodes[1])) { // If no road exists
				throw new NotChildNodeException(
						MessageFormat.format("The node {0} is not a child of {1}", nodes[1], nodes[0]));
			}
			// Gets the distance between the 2 first nodes +
			// Gets the distance of all the next nodes
			distance = nodes[0].getChildren().get(nodes[1])
					+ computeDistance(Arrays.stream(nodes)
							.filter(n -> !n.equals(nodes[0])) // nodes minus the first node (ABC -> BC)
							.toArray(Node[]::new));
		}
		return distance;
	}

	/**
	 * Gets the number of trip available.
	 * 
	 * @param start start node
	 * @param end end node
	 * @param minStops minimum of stops to do
	 * @param maxStops maximum of stops to do
	 * @return number of trip
	 */
	public int getNumberTripWithMinMaxStops(Node start, Node end, int minStops, int maxStops) {
		int number = 0;
		for (Node n : start.getChildren().keySet()) {
			number += computeNumberTripWithMinMaxStops(n, end, minStops, maxStops, 0);
		}
		return number;
	}

	/**
	 * 
	 * Gets the number of trip available (recursive method).
	 * 
	 * @param start start stop
	 * @param end end stop
	 * @param minStops minimum of stops to do
	 * @param maxStops maximum of stops to do
	 * @param step number of stops already did
	 * @return number of trip
	 */
	private int computeNumberTripWithMinMaxStops(Node start, Node end, int minStops, int maxStops, int step) {
		int number = 0;
		step++;
		if (step <= maxStops) {
			if (start.equals(end) && step >= minStops) { // If we find a trip
				number = 1;
			}
			// Checks with the children
			for (Node n : start.getChildren().keySet()) {
				number += computeNumberTripWithMinMaxStops(n, end, minStops, maxStops, step);
			}
		}
		return number;
	}

	/**
	 * Gets the number of trip available.
	 * 
	 * @param start start stop
	 * @param end end stop
	 * @param minLength minimum length
	 * @param maxLength maximum length
	 * @return number of trip
	 */
	public int getNumberTripWithLength(Node start, Node end, int minLength, int maxLength) {
		int number = 0;
		for (Node n : start.getChildren().keySet()) {
			number += computeNumberTripWithLength(n, end, minLength, maxLength, start.getChildren().get(n));
		}
		return number;
	}

	/**
	 * Gets the number of trip available (recursive method).
	 * 
	 * @param start start stop
	 * @param end end stop
	 * @param minLength minimum length
	 * @param maxLength maximum length
	 * @param length current length
	 * @return number of trip
	 */
	private int computeNumberTripWithLength(Node start, Node end, int minLength, int maxLength, int length) {
		int number = 0;
		if (length < maxLength) {
			if (start.equals(end) && length >= minLength) { // If a trip is found
				number = 1;
			}
			for (Node n : start.getChildren().keySet()) { // Check with children
				number += computeNumberTripWithLength(n, end, minLength, maxLength, length + start.getChildren().get(n));
			}
		}
		return number;
	}

	/**
	 * Gets the shortest road between 2 stops.
	 * @param start start stop
	 * @param end end stop
	 * @return the size of the shortest road
	 * @throws NotChildNodeException if no road is possible
	 */
	public int getShortestRoute(Node start, Node end) throws NotChildNodeException {
		// Map to keep the road length
		Map<Node, Integer> map = new HashMap<Node, Integer>();
		// List of the nodes already passed
		List<Node> removed = new ArrayList<Node>();
		for (Node n: start.getChildren().keySet()) { // Register the first children
			map.put(n, start.getChildren().get(n));
		}
		removed.add(start);
		// Gets the nearest child
		Optional<Node> current = nextNode(map, removed);
		while (current.isPresent()) {
			updateMap(current.get(), map);
			removed.add(current.get());
			current = nextNode(map, removed);
		}
		if (!map.containsKey(end)) { // If we have not found the end stop
			throw new NotChildNodeException(
					MessageFormat.format("No road between {0} and {1}", start, end));
		}
		return map.get(end);
	}
	
	/**
	 * Gets the next node which have not been passed yet and with the smallest length.
	 * @param map map with the road length
	 * @param removed passed nodes
	 * @return an optional with the selected node
	 */
	private Optional<Node> nextNode(Map<Node, Integer> map, List<Node> removed) {
		return map.keySet().stream()
			.filter(n -> !removed.contains(n))
			.min(Comparator.comparing(n -> map.get(n)));
	}

	/**
	 * Updates the map with the new length.
	 * @param start current node
	 * @param map map with the registered length
	 */
	private void updateMap(Node start, Map<Node, Integer> map) {
		for (Node n: start.getChildren().keySet()) {
			Integer distance = start.getChildren().get(n);
			if (!map.containsKey(n)) { // If we have not yet meet this node
				map.put(n, map.get(start) + distance);
			} else if (map.get(n) > map.get(start) + distance) { // Registers the shortest road
				map.put(n, map.get(start) + distance);
			}
		}
	}

}
