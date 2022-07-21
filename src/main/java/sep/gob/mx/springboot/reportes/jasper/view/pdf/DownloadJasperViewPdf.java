package sep.gob.mx.springboot.reportes.jasper.view.pdf;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("ver/pdf")
public class DownloadJasperViewPdf extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PdfPTable tabla = new PdfPTable(1);
		tabla.addCell("Clientes");
		tabla.addCell("Jose");
		tabla.addCell("Juan");
		tabla.addCell("Antony");
		tabla.setSpacingAfter(20);
		
		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.addCell("Precios");
		tabla2.addCell("20.5");
		tabla2.addCell("30.5");
		tabla2.addCell("50.5");
		
		document.add(tabla);
		document.add(tabla2);
		
	}

}
