package com.sys.botica.crce.pe.sys_botica.build;

import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;

public class PdfBuilder {
	
	private Document document;

	
	public PdfBuilder () {
		
	}
	public PdfBuilder line() {
		DottedLine separador = new DottedLine();
		separador.setGap(10);
		separador.setLineWidth(10);
		this.document.add(new LineSeparator(separador));
		return this;
	}
	
	public PdfBuilder paragraphEmphasized(String text) {
		this.document.add(new Paragraph(text).setBold().setFontSize(10));
		return this;
	}
	
	public PdfBuilder paragraph(String text) {
		this.document.add(new Paragraph(text));
		return this;
	}
}
