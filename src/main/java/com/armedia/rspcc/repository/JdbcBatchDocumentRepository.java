package com.armedia.rspcc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.armedia.rspcc.model.*;

@Repository
public class JdbcBatchDocumentRepository implements BatchDocumentRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public int save(BatchDocument BatchDocument) {
    return jdbcTemplate.update("INSERT INTO BatchDocuments (BatchName, SiteId, UserIdKeyVerified) VALUES(?,?,?)",
        new Object[] {BatchDocument.getBatchName(), BatchDocument.getSiteId(), BatchDocument.getUserIdKeyVerified() });
  }

  @Override
  public int update(BatchDocument BatchDocument) {
    return jdbcTemplate.update("UPDATE BatchDocuments set BatchName=?, SiteId=?, UserIdKeyVerified=?  WHERE BatchId=?",
        new Object[] {BatchDocument.getBatchName(), BatchDocument.getSiteId(), BatchDocument.getUserIdKeyVerified(), BatchDocument.getBatchId() });
  }

  @Override
  public BatchDocument findByBatchId(Long BatchId) {
    try {
      BatchDocument batchDocument = jdbcTemplate.queryForObject("SELECT * FROM BatchDocuments WHERE BatchId=?",
          BeanPropertyRowMapper.newInstance(BatchDocument.class), BatchId);

      return batchDocument;
    } catch (IncorrectResultSizeDataAccessException e) {
      return null;
    }
  }

  @Override
  public int deleteByBatchId(Long BatchId) {
    return jdbcTemplate.update("DELETE FROM BatchDocuments WHERE BatchId=?", BatchId);
  }

  @Override
  public List<BatchDocument> findAll() {
    return jdbcTemplate.query("SELECT * from BatchDocuments", BeanPropertyRowMapper.newInstance(BatchDocument.class));
  }

  
  @Override
  public List<BatchDocument> findByBatchNameContaining(String BatchName) {
    String q = "SELECT * from BatchDocuments WHERE BatchName LIKE '%" + BatchName + "%' collate binary_ci";

    return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(BatchDocument.class));
  }

  @Override
  public int deleteAll() {
    return jdbcTemplate.update("DELETE from BatchDocuments");
  }
}
