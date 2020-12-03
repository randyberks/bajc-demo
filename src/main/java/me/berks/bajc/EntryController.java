package me.berks.bajc;

import java.io.IOException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EntryController {
	
	private DocxWriter docx = new DocxWriter();
	
	@GetMapping("/certificate")
	public String greetingForm(Model model) {
		model.addAttribute("certificate", new Certificate());
		return "form";
	}

	@PostMapping("/certificate")
	public String greetingSubmit(@ModelAttribute Certificate certificate, Model model) throws Docx4JException, IOException {
		byte[] file = docx.createDocx(certificate.getName());
	    model.addAttribute("certificate", certificate);
	    model.addAttribute("file", file);
	    return "certificate";
	}

}
