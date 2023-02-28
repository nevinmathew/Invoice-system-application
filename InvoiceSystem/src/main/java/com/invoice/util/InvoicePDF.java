package com.invoice.util;


import java.awt.Color;

import org.springframework.stereotype.Component;

import com.invoice.dto.ResponseInvoice;
import com.invoice.entity.Product;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class InvoicePDF/* extends AbstractPdfView */{

//	@Override
	public void buildPdfDocument(ResponseInvoice invoice, HttpServletResponse response) throws Exception {

//		ResponseInvoice invoice = (ResponseInvoice) model.get("invoice");
		Document document = new Document(PageSize.A4);
	    PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		
		PdfPTable table = new PdfPTable(4);
		PdfPCell cell = null;
		Font headFont = FontFactory.getFont("Helvetica", 15, Font.BOLD);
		cell = new PdfPCell(new Phrase("Invoice",headFont));
		cell.setColspan(4);
		table.setSpacingAfter(20);
		cell.setBackgroundColor(new Color(175, 175, 175));
		cell.setPadding(8f);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table.addCell(cell);
		
		Font font = FontFactory.getFont("Helvetica", 13, Font.BOLD);
		table.addCell(new PdfPCell(new Phrase("Invoice Number",font)));
		table.addCell(new PdfPCell(new Phrase("Created Date",font)));
		table.addCell(new PdfPCell(new Phrase("Created Time",font)));
		table.addCell(new PdfPCell(new Phrase("Total Amount",font)));
		table.addCell(invoice.getId());
		table.addCell(invoice.getCreatedDate());
		table.addCell(invoice.getCreatedTime());
		table.addCell(invoice.getAmount().toString());		
		
		PdfPTable table2 = new PdfPTable(4);
		cell = new PdfPCell(new Phrase("Products",headFont));
		cell.setColspan(4);
		table2.setSpacingAfter(20);
		cell.setBackgroundColor(new Color(175, 175, 175));
		cell.setPadding(8f);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		table2.addCell(cell);
		table2.addCell(new PdfPCell(new Phrase("Name",font)));
		table2.addCell(new PdfPCell(new Phrase("Price",font)));
		table2.addCell(new PdfPCell(new Phrase("Quantity",font)));
		table2.addCell(new PdfPCell(new Phrase("Total",font)));
		
		for(Product item : invoice.getProducts()) {
			table2.addCell(item.getName());
			cell = new PdfPCell(new Phrase(item.getPrice().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			table2.addCell(cell);
			cell = new PdfPCell(new Phrase(item.getQuantity().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			table2.addCell(cell);
			cell = new PdfPCell(new Phrase(item.getTotalPrice().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			table2.addCell(cell);
		}
		
		cell = new PdfPCell(new Phrase("Total",font));
		cell.setColspan(3);
		cell.setPadding(8f);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		table2.addCell(cell);
		cell = new PdfPCell(new Phrase(invoice.getAmount().toString()));
		cell.setPadding(8f);
		table2.addCell(cell);
		
		document.add(table);
		document.add(table2);
		
		document.close();
	}

}
