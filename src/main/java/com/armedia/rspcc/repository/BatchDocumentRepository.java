package com.armedia.rspcc.repository;

import java.util.List;

import com.armedia.rspcc.model.BatchDocument;

public interface BatchDocumentRepository {
  int save(BatchDocument BatchDocument);

  int update(BatchDocument BatchDocument);

  BatchDocument findByBatchId(Long id);

  int deleteByBatchId(Long id);

  List<BatchDocument> findAll();

//  List<BatchDocument> findByBatchId(String BatchId);

  List<BatchDocument> findByBatchNameContaining(String BatchName);

  int deleteAll();
}
