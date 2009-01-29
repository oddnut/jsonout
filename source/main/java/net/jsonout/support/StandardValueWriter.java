/*
	StandardValueWriter.java

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
 * StandardValueWriter
 * 
 * Comment here.
 */
public class StandardValueWriter implements ValueWriter {
	// *** Class Members ***
	private enum State {closed, open, object, array, writer, base64}

	// *** Instance Members ***
	private Writer out;
	private StandardObjectWriter objectWriter;
	private StandardArrayWriter arrayWriter;
	private JSONStringWriter stringWriter;
	//private OutputStream base64;
	private State state;
	private int depth;

	// *** Constructors ***
	public StandardValueWriter() {
		state = State.closed;
	}

	// *** ValueWriter Methods ***
	public ObjectWriter object() throws IOException {
		checkOpen();
		
		if (objectWriter == null)
			objectWriter = new StandardObjectWriter();
		
		objectWriter.open(out, depth);
		
		state = State.object;
		
		return objectWriter;
	}
	
	public ArrayWriter array() throws IOException {
		checkOpen();
		
		if (arrayWriter == null)
			arrayWriter = new StandardArrayWriter();
		
		arrayWriter.open(out, depth);
		
		state = State.array;
		
		return arrayWriter;
	}
	
	public void string(String s) throws IOException {
		checkOpen();
		if (s == null) {
			nul();
			return;
		}
		
		string().write(s);
		
		stringWriter.close();
		
		state = State.closed;
	}
	
	public Writer string() throws IOException {
		checkOpen();
		
		if (stringWriter == null)
			stringWriter = new JSONStringWriter();
		
		if (!stringWriter.isOpen())
			stringWriter.open(out);
		
		state = State.writer;
		
		return stringWriter;
	}
	
	public OutputStream base64() throws IOException {
		checkOpen();
		// TODO - implement this
		return null;
	}
	
	public void number(int n) throws IOException {
		checkOpen();
		out.append(Integer.toString(n));
		state = State.closed;
	}
	
	public void number(long n) throws IOException {
		checkOpen();
		out.append(Long.toString(n));
		state = State.closed;
	}
	
	public void number(double n) throws IOException {
		checkOpen();
		out.append(Double.toString(n));
		state = State.closed;
	}
	
	public void number(float n) throws IOException {
		checkOpen();
		out.append(Float.toString(n));
		state = State.closed;
	}
	
	public void number(Number n) throws IOException {
		checkOpen();
		if (n == null) {
			nul();
			return;
		}
		
		out.append(n.toString());
		state = State.closed;
	}
	
	public void bool(boolean b) throws IOException {
		checkOpen();
		
		if (b)
			out.append("true");
		else
			out.append("false");
		
		state = State.closed;
	}
	
	public void nul() throws IOException {
		checkOpen();
		
		out.append("null");
		
		state = State.closed;
	}

	// *** Public Methods ***
	public void open(Writer out, int depth) throws IOException {
		if (state != State.closed)
			throw new IllegalStateException("attempt to open an already-opened valuewriter");
		this.out = out;
		this.depth = depth;
		state = State.open;
	}
	
	public boolean isOpen() {
		return state != State.closed;
	}
	
	public void close() throws IOException {
		
		switch(state) {
		case closed:
			return;
		case open:
			throw new IllegalStateException("ValueWriter closed without any value written");
		case object:
			objectWriter.close();
			break;
		case array:
			arrayWriter.close();
			break;
		case writer:
			stringWriter.close();
			break;
		case base64:
			// TODO
			// base64.close();
			break;
		}
		state = State.closed;
	}

	// *** Protected Methods ***

	// *** Package Methods ***

	// *** Private Methods ***
	private void checkOpen() {
		if (state != State.open)
			throw new IllegalStateException("ValueWriter is not open, and can't be used now (state=" + state + ")");
	}

	// *** Private Classes ***
}
