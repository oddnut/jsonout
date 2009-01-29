/*
	JSON.java

	Author: David Fogel
	Copyright 2008 David Fogel
	All rights reserved.
*/

package net.jsonout;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import net.jsonout.support.StandardJSONWriter;

/**
 * JSON
 * 
 * Comment here.
 */
public class JSON {
	// *** Class Members ***

	// *** Instance Members ***

	// *** Constructors ***

	// *** Interface Methods ***

	// *** Public Methods ***
	public static JSONWriter jsonWriter(Writer out) {
		return new StandardJSONWriter(out);
	}
	
	public static JSONWriter jsonWriter(OutputStream out, String charsetName) throws UnsupportedEncodingException {
		return jsonWriter(new OutputStreamWriter(out, charsetName));
	}

	// *** Protected Methods ***

	// *** Package Methods ***

	// *** Private Methods ***

	// *** Private Classes ***
}
