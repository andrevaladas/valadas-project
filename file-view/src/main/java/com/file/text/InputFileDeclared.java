package com.file.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe que carregas os dados do arquivo selecionado
 * 
 * @author andrevaladas
 */
public class InputFileDeclared {
	private FileInputStream in = null;

	public static void main(final String[] args) throws Exception {
		final URL resource = ClassLoader.getSystemResource("valadas.txt");
		final File file = new File(resource.toURI());

		final InputFileDeclared inputFileDeclared = new InputFileDeclared(file);
		final List<String> loadStrings = inputFileDeclared.loadStrings();
		System.out.println(loadStrings.toString());
		for (final String string : loadStrings) {
			System.out.println(string);
		}
	}

	public InputFileDeclared(final File file) {
		try {
			in = new FileInputStream(file);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<String> loadStrings() {
		final List<String> list = new LinkedList<String>();
		try {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = reader.readLine()) != null) {
				list.add(line);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
