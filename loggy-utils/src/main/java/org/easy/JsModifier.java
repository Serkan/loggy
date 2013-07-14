package org.easy;

import java.io.*;

/**
 * Hello world!
 */
public class JsModifier {
	public static void main(String[] args) {
		File jsFile = new File("D:\\serkan-test\\loggy\\loggy-web\\src\\main\\webapp\\js\\jquery.atmosphere.js");
		File mJsFile = new File("D:\\serkan-test\\loggy\\loggy-web\\src\\main\\webapp\\js\\jquery.atmosphere-modified.js");
		File jQueryFile = new File("D:\\serkan-test\\loggy\\loggy-web\\src\\main\\webapp\\js\\jquery-1.9.0.js");
		StringBuffer ext = new StringBuffer();
		BufferedWriter writer = null;
		BufferedReader reader = null;
		try {
			mJsFile.createNewFile();
			writer = new BufferedWriter(new FileWriter(mJsFile));
			reader = new BufferedReader(new FileReader(jsFile));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.contains("jQuery")) {
					String fName = extractFunctionName(line);
					System.out.println(line);
				} else {
					writer.write(line);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				writer.close();
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		mJsFile.delete();
	}

	private static String extractFunctionName(String line) {
		return null;  //To change body of created methods use File | Settings | File Templates.
	}
}
