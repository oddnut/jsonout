/*
	JSONValue.java

	Author: David Fogel
	Copyright 2009 David Fogel
	All rights reserved.
*/

package net.jsonout;

import java.io.IOException;

/**
 * JSONValue
 * 
 * Comment here.
 */
public interface JSONValue {
	// *** Class Members ***

	// *** Interface Methods ***
	public void writeTo(ValueWriter out) throws IOException;
}
