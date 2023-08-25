package com.aspiremanagement.serviceadmin;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PDFService {
	
	public ByteArrayInputStream createPdf()
	{
	String title="Welcome to my pdf";
	String content="We will start to create my 1st pdf API by using java spring boot...";
	
	ByteArrayOutputStream out=new ByteArrayOutputStream();
	Document document=new Document();
	PdfWriter .getInstance(document, out);
	document.open();
	
	Font titleFont=FontFactory.getFont(FontFactory.HELVETICA_BOLD,20,Color.RED);
	
	Paragraph titlePara=new Paragraph(title, titleFont);
	titlePara.setAlignment(Element.ALIGN_CENTER);
	document.add(titlePara);
	
	Font contentFont=FontFactory.getFont(FontFactory.HELVETICA,18);
	Paragraph contentPara=new Paragraph(content, contentFont);
	document.add(contentPara);
	
	document.close();
	return new ByteArrayInputStream(out.toByteArray());
	}

}
