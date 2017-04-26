package com.raxixor.edi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by raxix on 04/04/2017, 12:08.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class OtherUtil {
	
	/**
	 * Gets an InputStream image from an URL.
	 * 
	 * @param url URL to get image from.
	 * @return InputStream image.
	 */
	public static InputStream ImageFromUrl(String url) {
		if (url == null)
			return null;
		
		try {
			URL u = new URL(url);
			URLConnection urlConnection = u.openConnection();
			urlConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
			return urlConnection.getInputStream();
		} catch (IOException | IllegalArgumentException e) {}
		return null;
	}
}
