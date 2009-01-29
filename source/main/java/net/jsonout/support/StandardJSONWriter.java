/*
	StandardJSONWriter.java

	Author: David Fogel
	Copyright 2008 David Fogel
	All rights reserved.
*/

package net.jsonout.support;

import java.io.IOException;
import java.io.Writer;

import net.jsonout.*;

/**
 * StandardJSONWriter
 * 
 * Comment here.
 */
public class StandardJSONWriter implements JSONWriter {
	// *** Class Members ***
	private enum State {open, object, array}

	// *** Instance Members ***
	private Writer out;
	private StandardObjectWriter objectWriter;
	private StandardArrayWriter arrayWriter;
	private State state;

	// *** Constructors ***
	public StandardJSONWriter(Writer out) {
		this.out = out;
		state = State.open;
	}

	// *** JSONWriter Methods ***
	public ObjectWriter object() throws IOException {
		closeChild();
		
		if (objectWriter == null)
			objectWriter = new StandardObjectWriter();
		
		objectWriter.open(out, 0);
		
		state = State.object;
		
		return objectWriter;
	}
	
	public ArrayWriter array() throws IOException {
		closeChild();
		
		if (arrayWriter == null)
			arrayWriter = new StandardArrayWriter();
		
		arrayWriter.open(out, 0);
		
		state = State.array;
		
		return arrayWriter;
	}
	
	public void space(String whitespace) throws IOException {
		closeChild();
		checkWhitespace(whitespace);
		out.write(whitespace);
	}
	
	public void close() throws IOException {
		closeChild();
		out.flush();
	}

	// *** Public Methods ***

	// *** Protected Methods ***

	// *** Package Methods ***
	static void checkWhitespace(String whitespace) {
		int length = whitespace.length();
		for (int i = 0 ; i < length ; i++) {
			char c = whitespace.charAt(i);
			switch(c) {
			case ' ':
			case '\t':
			case '\n':
			case '\r':
				continue;
			default:
				throw new IllegalArgumentException("Illegal whitespace character");
			}
		}
	}

	// *** Private Methods ***
	private void closeChild() throws IOException {
		switch(state) {
		case array:
			arrayWriter.close();
			break;
		case object:
			objectWriter.close();
			break;
		}
		state = State.open;
	}

	// *** Private Classes ***
}
