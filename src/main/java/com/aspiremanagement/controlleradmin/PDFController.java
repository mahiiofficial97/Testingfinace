package com.aspiremanagement.controlleradmin;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aspiremanagement.serviceadmin.PDFService;

@CrossOrigin(origins = "http://localhost:8100", allowCredentials = "true")
@RestController
@RequestMapping("/admin")
public class PDFController {
	@Autowired
	PDFService pdfService;

	@RequestMapping(value = "/create-pdf", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<InputStreamResource> createPdf() {
		System.out.println("Start Api Writing...");
		ByteArrayInputStream file = pdfService.createPdf();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline;=file=abc.pdf");
		return ResponseEntity.ok().headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(file));

	}

}
