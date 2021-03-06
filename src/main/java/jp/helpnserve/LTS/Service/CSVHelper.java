package jp.helpnserve.LTS.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jp.helpnserve.LTS.Model.Sentence;

@Service
public class CSVHelper {
	public static String TYPE = "application/octet-stream";
	static String[] HEADERs = { "No.", "English", "Japanese", "Comment", "FileName" };

	/**
	 * 
	 * @param file
	 * @return
	 */
	public boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param is
	 * @return
	 */
	public List<String[]> csvToList(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<String[]> tutorials = new ArrayList<String[]>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				String[] tutorial = new String[] { csvRecord.get(HEADERs[0]), csvRecord.get(HEADERs[1]),
						csvRecord.get(HEADERs[2]), csvRecord.get(HEADERs[3]), csvRecord.get(HEADERs[4]) };

				tutorials.add(tutorial);
			}

			return tutorials;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

	/**
	 * 
	 * @param listSentence
	 * @return
	 */
	public ByteArrayInputStream backupToCSV(List<Sentence> listSentence) {
		final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			// header
			List<String> data = Arrays.asList(HEADERs[0], HEADERs[1], HEADERs[2], HEADERs[3]);
			csvPrinter.printRecord(data);

			// body
			for (Sentence sentence : listSentence) {
				data = Arrays.asList(String.valueOf(sentence.getId()), sentence.getOriginSentence(),
						sentence.getTranslateSentence(), sentence.getExplanation());

				csvPrinter.printRecord(data);
			}

			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
		}
	}
}
