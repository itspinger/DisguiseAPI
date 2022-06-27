package net.pinger.disguise.util;

public class HttpUtil {

	private final static String MINESKIN_URL = "https://api.mineskin.org/generate/url?url=%s";

	/**
	 * This method returns a new mineskin url used for requesting new skins.
	 *
	 * @param url the url of the image
	 * @return the url of the request
	 */

	public static String toMineskin(String url) {
		return String.format(MINESKIN_URL, url);
	}

}
