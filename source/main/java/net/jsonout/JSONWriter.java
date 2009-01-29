/*
	JSONWriter.java

	Author: David Fogel
	Copyright 2008 David Fogel
	All rights reserved.
*/

package net.jsonout;

import java.io.IOException;

/**
 * JSONWriter
 * 
 * Comment here.
 */
public interface JSONWriter {
	// *** Class Members ***

	// *** Interface Methods ***
	ObjectWriter object() throws IOException;
	
	ArrayWriter array() throws IOException;
	
	void space(String whitespace) throws IOException;
	
	void close() throws IOException;
}
