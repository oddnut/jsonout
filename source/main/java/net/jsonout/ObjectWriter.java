/*
	ObjectWriter.java

	Author: David Fogel
	Copyright 2008 David Fogel
	All rights reserved.
*/

package net.jsonout;

import java.io.IOException;

/**
 * ObjectWriter
 * 
 * Comment here.
 */
public interface ObjectWriter {
	// *** Class Members ***

	// *** Interface Methods ***
	ValueWriter member(String name) throws IOException;
	MemberWriter member() throws IOException;
	
	void space(String whitespace) throws IOException;
	
	void close() throws IOException;
}
