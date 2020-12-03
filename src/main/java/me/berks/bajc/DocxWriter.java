package me.berks.bajc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger;
import org.docx4j.model.fields.merge.MailMerger.OutputField;
import org.docx4j.model.fields.merge.MailMergerWithNext;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class DocxWriter {
	
	public byte[] createDocx(String name) throws Docx4JException, IOException {
		
		// instead of saving to file system, convert to byte array and send to client
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		prepareDocx(name).save(out);
		
		return out.toByteArray();
		
	}
	
	public WordprocessingMLPackage prepareDocx(String name) throws Docx4JException {
		// create a date object
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/mm/yy", Locale.US);
		LocalDateTime now = LocalDateTime.now();
					
		// load the docx base template
		WordprocessingMLPackage wordPackage = WordprocessingMLPackage.load(
				new File("/home/randy/certificate.docx")
				);

		// create a list that holds all the merge fields
		List<Map<DataFieldName, String>> data = new ArrayList<Map<DataFieldName, String>>();
		
		// map the merge fields
		Map<DataFieldName, String> map = new HashMap<DataFieldName, String>();
		map.put(new DataFieldName("name"), name);
		map.put(new DataFieldName("date"), dtf.format(now));
		data.add(map);
		
		// Replace the merge fields
		MailMerger.setMERGEFIELDInOutput(OutputField.REMOVED);
		MailMergerWithNext.performLabelMerge(wordPackage, data);
		
		return wordPackage;

	}
	
	
}
