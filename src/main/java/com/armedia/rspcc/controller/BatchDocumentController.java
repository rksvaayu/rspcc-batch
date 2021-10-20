package com.armedia.rspcc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.armedia.rspcc.model.BatchDocument;
import com.armedia.rspcc.repository.BatchDocumentRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class BatchDocumentController {

  @Autowired
  BatchDocumentRepository BatchDocumentRepository;

  @GetMapping("/BatchDocuments")
  public ResponseEntity<List<BatchDocument>> getAllBatchDocuments(@RequestParam(required = false) String BatchName) {
    try {
      List<BatchDocument> BatchDocuments = new ArrayList<BatchDocument>();

      if (BatchName == null)
        BatchDocumentRepository.findAll().forEach(BatchDocuments::add);
      else
        BatchDocumentRepository.findByBatchNameContaining(BatchName).forEach(BatchDocuments::add);

      if (BatchDocuments.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(BatchDocuments, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/BatchDocuments/{BatchId}")
  public ResponseEntity<BatchDocument> getBatchDocumentById(@PathVariable("BatchId") long BatchId) {
    BatchDocument BatchDocument = BatchDocumentRepository.findByBatchId(BatchId);

    if (BatchDocument != null) {
      return new ResponseEntity<>(BatchDocument, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/BatchDocuments")
  public ResponseEntity<String> createBatchDocument(@RequestBody BatchDocument BatchDocument) {
    try {
      BatchDocumentRepository.save(new BatchDocument(BatchDocument.getBatchName(), BatchDocument.getSiteId(), BatchDocument.getUserIdKeyVerified()));
      return new ResponseEntity<>("BatchDocument was created successfully.", HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/BatchDocuments/{BatchId}")
  public ResponseEntity<String> updateBatchDocument(@PathVariable("BatchId") long BatchId, @RequestBody BatchDocument BatchDocument) {
    BatchDocument _BatchDocument = BatchDocumentRepository.findByBatchId(BatchId);

    if (_BatchDocument != null) {
      _BatchDocument.setBatchId(BatchId);
      _BatchDocument.setBatchName(BatchDocument.getBatchName());
      _BatchDocument.setSiteId(BatchDocument.getSiteId());
      _BatchDocument.setUserIdKeyVerified(BatchDocument.getUserIdKeyVerified());

      BatchDocumentRepository.update(_BatchDocument);
      return new ResponseEntity<>("BatchDocument was updated successfully.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Cannot find BatchDocument with BatchId=" + BatchId, HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/BatchDocuments/{BatchId}")
  public ResponseEntity<String> deleteBatchDocument(@PathVariable("BatchId") long BatchId) {
    try {
      int result = BatchDocumentRepository.deleteByBatchId(BatchId);
      if (result == 0) {
        return new ResponseEntity<>("Cannot find BatchDocument with BatchId=" + BatchId, HttpStatus.OK);
      }
      return new ResponseEntity<>("BatchDocument was deleted successfully.", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Cannot delete BatchDocument.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/BatchDocuments")
  public ResponseEntity<String> deleteAllBatchDocuments() {
    try {
      int numRows = BatchDocumentRepository.deleteAll();
      return new ResponseEntity<>("Deleted " + numRows + " BatchDocument(s) successfully.", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Cannot delete BatchDocuments.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
  
  /*

  @GetMapping("/BatchDocuments/BatchName")
  public ResponseEntity<List<BatchDocument>> findByBatchName(@PathVariable("BatchName") String BatchName) {
    try {
      List<BatchDocument> BatchDocuments = BatchDocumentRepository.findByBatchName(BatchName);

      if (BatchDocuments.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(BatchDocuments, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  } */

}
