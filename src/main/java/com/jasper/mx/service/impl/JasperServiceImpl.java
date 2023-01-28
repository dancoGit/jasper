package com.jasper.mx.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import com.jasper.mx.model.ParametersList;
import com.jasper.mx.model.entity.Main;
import com.jasper.mx.model.entity.report.PaisesDataSource;
import com.jasper.mx.repository.JasperRepository;
import com.jasper.mx.service.JasperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@RequiredArgsConstructor
@Slf4j
@Service
public class JasperServiceImpl implements JasperService {

  private final JasperRepository repository;

  private final ParametersList parameter;
  
  @Override
  public List<Main> getAll() {
    log.info("Entrando al servicio");

    return repository.findAll();
  }

  @Override
  public ResponseEntity<ByteArrayResource> exportInvoice() {

    log.info("Parameter profiles: {}", parameter.getProfiles());
    
    try {

      final File file = ResourceUtils.getFile("classpath:reports/practica_paises.jasper");
      final JasperReport report = (JasperReport) JRLoader.loadObject(file);

      JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, PaisesDataSource.getDataSource(getAll()));
      
      byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
      String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
      StringBuilder stringBuilder = new StringBuilder().append("InvoicePDF:");
      ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
          .filename(stringBuilder.append("test-java-8")
              .append("generateDate:")
              .append(sdf)
              .append(".pdf")
              .toString())
          .build();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentDisposition(contentDisposition);
      
      log.info("Finnito!");
      
      return ResponseEntity.ok().contentLength((long) reporte.length)
          .contentType(MediaType.APPLICATION_PDF)
          .headers(headers).body(new ByteArrayResource(reporte));
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }

}
