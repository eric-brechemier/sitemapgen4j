package com.redfin.sitemapgenerator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class TestUtil {
	public static String getResourceAsString(Class<?> clazz, String path) {
		InputStream stream = clazz.getResourceAsStream(path);
		if (stream == null) throw new RuntimeException("resource path not found: " + path);
		InputStreamReader reader = new InputStreamReader(stream);
		StringBuilder sb = new StringBuilder();
		try {
			int c;
			while ((c = reader.read()) != -1) {
				sb.append((char)c);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

	public static String slurpFileAndDelete(File file) {
		file.deleteOnExit();
		StringBuilder sb = new StringBuilder();
		try {
			FileReader reader = new FileReader(file);
			int c;
			while ((c = reader.read()) != -1) {
				sb.append((char)c);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		file.delete();
		return sb.toString();
	}

	public static Node slurpXmlFileAndDelete(File file, XPath xpath) throws Exception {
		String xml = slurpFileAndDelete(file);
		InputSource source = new InputSource( new StringReader(xml) );
		// return the root of the document for further XPath evaluations
		return (Node) xpath.evaluate("/",source,XPathConstants.NODE);
	}
}
