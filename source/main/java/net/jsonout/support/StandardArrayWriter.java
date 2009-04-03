/*
	StandardArrayWriter.java

	Author: David Fogel
	Copyright 2008 David Fogel
	All rights reserved.
*/

package net.jsonout.support;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import net.jsonout.*;

/**
 * StandardArrayWriter
 * 
 * Comment here.
 */
public class StandardArrayWriter implements ArrayWriter {
	// *** Class Members ***
	private enum State {closed, open, value}

	// *** Instance Members ***
	private Writer out;
	private State state;
	private StandardValueWriter valueWriter;
	private boolean hasMembers;
	private int depth;

	// *** Constructors ***
	public StandardArrayWriter() {
		state = State.closed;
		hasMembers = false;
	}

	// *** ArrayWriter Methods ***
	public ValueWriter value() throws IOException {
		throwIfClosed();
		closeMember();
		
		if (hasMembers)
			writeMemberSeparator();
		
		if (valueWriter == null)
			valueWriter = new StandardValueWriter();
		
		valueWriter.open(out, depth + 1);
		
		state = State.value;
		
		return valueWriter;
	}
	
	public void values(Object... vals) throws IOException {
		if (vals == null)
			throw new IllegalArgumentException("vals must not be null- must have at least one argument");
		
		for (Object o : vals) {
			if (o == null)
				nul();
			else if (o instanceof Number)
				number((Number) o);
			else if (o instanceof String)
				string((String) o);
			else if (o instanceof Boolean)
				bool(((Boolean) o).booleanValue());
			else if (o instanceof byte[])
				base64().write((byte[]) o);
			else if (o instanceof Object[])
				array((Object[]) o);
			else
				throw new IllegalArgumentException(
						"values must be instances of String, Number, Boolean, byte[], Object[], or be null" + 
						"the illegal value is of type " + 
						o.getClass().getCanonicalName() + 
						"and it's value is: " + o);
		}
	}
	
	public void space(String whitespace) throws IOException {
		throwIfClosed();
		closeMember();
		
		StandardJSONWriter.checkWhitespace(whitespace);
		
		out.write(whitespace);
	}
	
	public void close() throws IOException {
		if (state == State.closed)
			return;
		
		closeMember();
		
		out.write(']');
		
		state = State.closed;
	}
	
	// *** ValueWriter Methods ***
	public ObjectWriter object() throws IOException {
		return value().object();
	}
	
	public ArrayWriter array() throws IOException {
		return value().array();
	}
	
	public ArrayWriter array(Object... vals) throws IOException {
		return value().array(vals);
	}
	
	public void string(CharSequence s) throws IOException {
		value().string(s);
	}
	
	public Writer string() throws IOException {
		return value().string();
	}
	
	public OutputStream base64() throws IOException {
		return value().base64();
	}
	
	public void number(int n) throws IOException {
		value().number(n);
	}
	
	public void number(long n) throws IOException {
		value().number(n);
	}
	
	public void number(double n) throws IOException {
		value().number(n);
	}
	
	public void number(float n) throws IOException {
		value().number(n);
	}
	
	public void number(Number n) throws IOException {
		value().number(n);
	}
	
	public void bool(boolean b) throws IOException {
		value().bool(b);
	}
	
	public void nul() throws IOException {
		value().nul();
	}

	// *** Public Methods ***
	public void open(Writer out, int depth) throws IOException {
		this.out = out;
		this.depth = depth;
		
		state = State.open;
		hasMembers = false;
		
		out.write('[');
	}

	// *** Protected Methods ***

	// *** Package Methods ***

	// *** Private Methods ***
	private void writeMemberSeparator() throws IOException {
		out.write(", ");
	}
	
	private void closeMember() throws IOException {
		if (state == State.value) {
			if (valueWriter.isOpen())
				valueWriter.close();
			hasMembers = true;
		}
		state = State.open;
	}
	
	private void throwIfClosed() {
		if (state == State.closed)
			throw new IllegalStateException("ArrayWriter is closed");
	}

	// *** Private Classes ***
}
