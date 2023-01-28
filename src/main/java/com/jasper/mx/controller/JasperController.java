package com.jasper.mx.controller;

import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jasper.mx.model.entity.Main;
import com.jasper.mx.service.JasperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class JasperController {

  private final JasperService jasperService;
  
  @GetMapping("/db")
  private List<Main> getAll(){
    
    log.info("Entrando al controlador");
    return jasperService.getAll();  
  }
  
  @GetMapping("/export-invoice")
  public ResponseEntity<ByteArrayResource> exportInvoice(){
      return jasperService.exportInvoice();
  }
}
