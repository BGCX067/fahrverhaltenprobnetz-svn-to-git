package de.dhbwstuttgart.vincon.fahrverhalten;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVReader;

/**
 * The Class Reader.
 */
public class Reader {

	/** The reader. */
	private CSVReader reader;
	
	/** The data. */
	private ArrayList<Measure> data;

	/**
	 * Instantiates a new reader.
	 *
	 * @param file the file
	 */
	public Reader(File file) {
		data = new ArrayList<Measure>();

		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(
					file)), ';', '\'');

			// skip the header
			reader.readNext();

			String[] line;
			while ((line = reader.readNext()) != null) {
				getData().add(new Measure(line));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public ArrayList<Measure> getData() {
		return data;
	}

}
