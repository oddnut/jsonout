/*
	ArrayWriter.java

	Author: David Fogel
	Copyright 2008 David Fogel
	All rights reserved.
*/

package net.jsonout;

import java.io.IOException;

/**
 * ArrayWriter
 * 
 * Comment here.
 */
public interface ArrayWriter extends ValueWriter {
	// *** Class Members ***

	// *** Interface Methods ***
	ValueWriter value() throws IOException;
	
	void value(JSONValue value) throws IOException;
	
	void values(Object... vals) throws IOException;
	
	void space(String whitespace) throws IOException;
	
	void close() throws IOException;
}
