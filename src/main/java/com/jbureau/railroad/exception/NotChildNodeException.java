package com.jbureau.railroad.exception;

/**
 * Exception when there is no route between two nodes.
 * @author jbureau
 *
 */
public class NotChildNodeException extends RailroadException {

	private static final long serialVersionUID = -349249598019636612L;

	public NotChildNodeException(String message) {
		super(message);
	}

}
