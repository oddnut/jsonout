/*
	JSONStringWriter.java

	Author: David Fogel
	Copyright 2008 David Fogel
	All rights reserved.
*/

package net.jsonout.support;

import java.io.IOException;
import java.io.Writer;

/**
 * JSONStringWriter
 * 
 * Comment here.
 */
public class JSONStringWriter extends Writer {
	// *** Class Members ***
	private static final int BUF_SIZE = 512; // seems like enough.  not sure
	private static final int BUF_FLUSH_SIZE = BUF_SIZE - 12; // in theory we could use the 12 chars for big unicode char escaping

	// *** Instance Members ***
	private Writer out;
	private boolean isOpen;
	private char[] buf;
	private int next;
	private CharArrayWrapper arrayWrapper = new CharArrayWrapper();
	
	// *** Constructors ***
	public JSONStringWriter() {
		out = null;
		isOpen = false;
		buf = new char[BUF_SIZE];
		next = 0;
	}

	// *** Writer Methods ***
	public void write(int c) throws IOException {
		checkOpen();
		
		if (next >= BUF_FLUSH_SIZE)
			flushBuffer();
		
		if (c >= 32 && c != '"' && c != '\\') {
			
			buf[next] = (char)c;
			next++;
			
		} else {
			
			buf[next] = '\\';
			switch(c) {
			case '"':
				buf[next+1] = '"';
				next += 2;
				break;
			case '\\':
				buf[next+1] = '\\';
				next += 2;
				break;
			case '\n':
				buf[next+1] = 'n';
				next += 2;
				break;
			case '\r':
				buf[next+1] = 'r';
				next += 2;
				break;
			case '\t':
				buf[next+1] = 't';
				next += 2;
				break;
			case '\b':
				buf[next+1] = 'b';
				next += 2;
				break;
			case '\f':
				buf[next+1] = 'f';
				next += 2;
				break;
			default:
				buf[next+1] = 'u';
				buf[next+2] = '0';
				buf[next+3] = '0';
				buf[next+4] = "0123456789abcdef".charAt(c >> 4);
				buf[next+5] = "0123456789abcdef".charAt(c & 0xF);
				next += 6;
				break;
			}
		}
		
	}
	
	public void write(char cbuf[], int off, int len) throws IOException {
		arrayWrapper.setCharArray(cbuf, off, len);
		append(arrayWrapper, 0, len);
	}
	
	public void write(String str, int off, int len) throws IOException {
    	append(str, off, off + len);
	}
	
	public Writer append(CharSequence csq) throws IOException {
		return append(csq, 0, csq.length());
	}
	
	public Writer append(CharSequence csq, int start, int end) throws IOException {
		checkOpen();
		
		for (int i = start ; i < end ; i++) {
			char c = csq.charAt(i);
			
			if (next >= BUF_FLUSH_SIZE)
				flushBuffer();
			
			if (c >= 32 && c != '"' && c != '\\') {
				
				buf[next] = c;
				next++;
				
			} else {
				
				buf[next] = '\\';
				switch(c) {
				case '"':
					buf[next+1] = '"';
					next += 2;
					break;
				case '\\':
					buf[next+1] = '\\';
					next += 2;
					break;
				case '\n':
					buf[next+1] = 'n';
					next += 2;
					break;
				case '\r':
					buf[next+1] = 'r';
					next += 2;
					break;
				case '\t':
					buf[next+1] = 't';
					next += 2;
					break;
				case '\b':
					buf[next+1] = 'b';
					next += 2;
					break;
				case '\f':
					buf[next+1] = 'f';
					next += 2;
					break;
				default:
					buf[next+1] = 'u';
					buf[next+2] = '0';
					buf[next+3] = '0';
					buf[next+4] = "0123456789abcdef".charAt(c >> 4);
					buf[next+5] = "0123456789abcdef".charAt(c & 0xF);
					next += 6;
					break;
				}
			}
		}
		
		return this;
	}
	
	public void flush() throws IOException {
		checkOpen();
		flushBuffer();
	}
	
	public void close() throws IOException {
		checkOpen();
		if (next >= BUF_FLUSH_SIZE)
			flushBuffer();
		buf[next] = '"';
		next++;
		flushBuffer();
		isOpen = false;
	}

	// *** Public Methods ***
	public void open(Writer out) throws IOException {
		if (isOpen)
			throw new IllegalStateException("Can't open a writer that is already open");
		
		this.out = out;
		buf[next] = '"';
		next++;
		isOpen = true;
	}
	
	public boolean isOpen() {
		return isOpen;
	}

	// *** Protected Methods ***

	// *** Package Methods ***

	// *** Private Methods ***
	private void flushBuffer() throws IOException {
		if (next == 0)
			return;
		out.write(buf, 0, next);
		next = 0;
	}
	
	private void checkOpen() {
		if (!isOpen)
			throw new IllegalStateException("Writer is closed.");
	}

	// *** Private Classes ***
	private class CharArrayWrapper implements CharSequence {
		
		private char[] buf;
		private int off;
		private int len;
		
		public void setCharArray(char[] buf, int off, int len) {
			this.buf = buf;
			this.off = off;
			this.len = len;
		}

		public char charAt(int index) {
			return buf[off + index];
		}

		public int length() {
			return len;
		}

		public CharSequence subSequence(int start, int end) {
			return new String(buf, off + start, end - start);
		}
		
	}
}
