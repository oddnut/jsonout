/*
	MemberWriter.java

	Author: David Fogel
	Copyright 2008 David Fogel
	All rights reserved.
*/

package net.jsonout;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * MemberWriter
 * 
 * Comment here.
 */
public interface MemberWriter {
	// *** Class Members ***

	// *** Interface Methods ***
	void name(String name) throws IOException;
	Writer name() throws IOException;
	OutputStream base64Name() throws IOException;
	
	ValueWriter value() throws IOException;
	
	void space(String whitespace) throws IOException;
}
