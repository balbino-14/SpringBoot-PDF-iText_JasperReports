package sep.gob.mx.springboot.reportes.jasper.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Controller
public class IndexController {
	
	@Value("${texto.descarga.pdf.itext}")
	public String descargaItext;
	
	@Value("${texto.descarga.pdf.jasper}")
	public String descargaJasper;
	
	@GetMapping({"", "/index", "/", "/home"})
	public String index(Model model) {
		model.addAttribute("titulo","Spring Boot con Jasper");
		model.addAttribute("descargaItext",descargaItext);
		model.addAttribute("descargaJasper",descargaJasper);
		return "index";
	}
	
	@GetMapping("/descargaPdfIText")
	public String descargaJasper() {
		System.out.println("JBGC=>Si llega al controller correcto ");
		return "ver/pdf";
	}
	
	@GetMapping("/descargaPdfJasper")
	public ResponseEntity<byte[]> descargaJasper2(){
		
		try {
		JasperReport reportCompiler= JasperCompileManager.compileReport(ResourceUtils.getFile("classpath:static/jasperReports/plantillaHorizontal.jrxml").getAbsolutePath());
        
        File imgFondo = ResourceUtils.getFile("classpath:static/images/imagenFondo.png");
        File logoUsicamm = ResourceUtils.getFile("classpath:static/images/logo.png");
        File imgMaestro = ResourceUtils.getFile("classpath:static/images/imagenMaestraMaestro.png");
        File imgCita = ResourceUtils.getFile("classpath:static/images/imagenDatosCita.png");
        File imgVineta = ResourceUtils.getFile("classpath:static/images/imagenVineta.png");
        File imgPersonaParticipante = ResourceUtils.getFile("classpath:static/images/imagenRequisitos.png");
        File imgCheck = ResourceUtils.getFile("classpath:static/images/imagenCheck.png");
        File imgComputadora = ResourceUtils.getFile("classpath:static/images/imagenComputadora.png");
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("IMG_FONDO", new FileInputStream(imgFondo));
		parameters.put("LOGO", new FileInputStream(logoUsicamm));
		parameters.put("IMG_MAESTRO", new FileInputStream(imgMaestro));
		parameters.put("IMG_CITA", new FileInputStream(imgCita));
		parameters.put("IMG_VINETA", new FileInputStream(imgVineta));
		parameters.put("IMG_PERSONA_PARTICIPANTE", new FileInputStream(imgPersonaParticipante));
		parameters.put("IMG_CHECK", new FileInputStream(imgCheck));
		parameters.put("IMG_COMPUTADORA", new FileInputStream(imgComputadora));
		
		JasperPrint empReport = JasperFillManager.fillReport(reportCompiler,parameters,new JREmptyDataSource());
        
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("filename","reporteJasper.pdf");
		
		return new ResponseEntity<byte[]>(JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);
		
		} catch(JRException jex) {
			System.out.println("\nOcurrio un error:");
			jex.printStackTrace();
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
