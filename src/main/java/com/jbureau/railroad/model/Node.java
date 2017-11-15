package com.jbureau.railroad.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Node which represents a town
 * @author jbureau
 *
 */
public class Node {
	
	private int id;
	
	private String label;

	/**
	 * Represents the children of this node: (child, weight);
	 */
	private Map<Node, Integer> children;	
	
	public Node(int id, String label) {
		this.id = id;
		this.label = label;
		this.children = new HashMap<Node, Integer>();
	}
	
	public Map<Node, Integer> getChildren() {
		return children;
	}
	
	/**
	 * Add a child with a weight to the children.
	 * If the child is already present in the children map, it will be replace.
	 * @param child 
	 * @param weight
	 */
	public void addChild(Node child, int weight) {
		this.children.put(child, weight);
	}
	
	public String getLabel() {
		return label;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return "Node " + label;
	}
	
	@Override
	public int hashCode() {
	    return this.id;
	}
}
