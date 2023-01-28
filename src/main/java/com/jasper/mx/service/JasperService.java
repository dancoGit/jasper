package com.jasper.mx.service;

import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import com.jasper.mx.model.entity.Main;

public interface JasperService {
  public List<Main> getAll();
  public ResponseEntity<ByteArrayResource> exportInvoice();
}
