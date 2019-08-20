package com.test.handler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.test.dao.TestDao;
import com.test.model.TestModel;

@Component
public class PdfCreator {
	
	private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	@Autowired
	TestModel testModel;
	
	@Autowired
	TestDao testDao;
	
	public Document createPDF(String file, String username, String code, String bldname, String addr, String name) {
		Document document = null;
		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			addTitlePage(document, code, bldname, addr, name);
			// 테이블
			//createTable(document, username);
			document.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	private void addTitlePage(Document document, String code, String bldname, String addr, String name) throws  DocumentException{
		
		try {
			//String[] names = BaseFont.enumerateTTCNames("c:/windows/fonts/GULIM.TTC");
	         
	        BaseFont bfont = BaseFont.createFont(String.format("%s,%s", "c:/windows/fonts/GULIM.TTC", 0), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	        Font f = new Font(bfont, 9);
	        
	        
			Paragraph preface = new Paragraph();
			createEmptyLine(preface, 1);
			preface.add(new Paragraph("Student Test report", TIME_ROMAN));
			createEmptyLine(preface, 1);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
			preface.add(new Paragraph("Date : " + simpleDateFormat.format(new Date()), TIME_ROMAN_SMALL));
			createEmptyLine(preface, 1);
			preface.add(new Paragraph("코드번호 : " + code, f));
			preface.add(new Paragraph("판매점명 : " + bldname, f));
			preface.add(new Paragraph("판매점주소 : " + addr, f));
			preface.add(new Paragraph("계약자명 : " + name, f));
			document.add(preface);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}
	
	private void createEmptyLine(Paragraph paragraph, int number) {
		for(int i=0; i<number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	private void createTable(Document document, String username) throws DocumentException {
		
		Paragraph paragraph = new Paragraph();
		createEmptyLine(paragraph, 2);
		document.add(paragraph);
		PdfPTable table = new PdfPTable(3);
		
		PdfPCell cell1 = new PdfPCell(new Phrase("Username"));
		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell1);
		
		cell1 = new PdfPCell(new Phrase("PassWord"));
		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell1);
		
		cell1 = new PdfPCell(new Phrase("Cnt"));
		cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell1);
		table.setHeaderRows(1);
		
		table.setWidthPercentage(100);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		try {
			testModel = testDao.read(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		table.addCell(testModel.getUsername());
		table.addCell(testModel.getPassword());
		table.addCell(testModel.getCnt()+"");
		
		document.add(table);
	}
}
