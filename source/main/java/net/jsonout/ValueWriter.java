/*
	ValueWriter.java

	Author: David Fogel
	Copyright 2008 David Fogel
	All rights reserved.
*/

package net.jsonout;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * ValueWriter
 * 
 * Comment here.
 */
public interface ValueWriter {
	// *** Class Members ***

	// *** Interface Methods ***
	ObjectWriter object() throws IOException;
	
	ArrayWriter array() throws IOException;
	ArrayWriter array(Object... vals) throws IOException;
	
	void string(CharSequence s) throws IOException;
	Writer string() throws IOException;
	OutputStream base64() throws IOException;
	
	void number(int n) throws IOException;
	void number(long n) throws IOException;
	void number(double n) throws IOException;
	void number(float n) throws IOException;
	void number(Number n) throws IOException;
	
	void bool(boolean b) throws IOException;
	
	void nul() throws IOException;
}
