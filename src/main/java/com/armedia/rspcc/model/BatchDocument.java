package com.armedia.rspcc.model;

public class BatchDocument {

  private long BatchId;
  private String BatchName;
  private String SiteId;
  private String UserIdKeyVerified;

  public BatchDocument() {

  }
  
  public BatchDocument(long BatchId, String BatchName, String SiteId, String UserIdKeyVerified) {
    this.BatchId = BatchId;
    this.BatchName = BatchName;
    this.SiteId = SiteId;
    this.UserIdKeyVerified=UserIdKeyVerified;
  }

  public BatchDocument(String BatchName, String SiteId, String UserIdKeyVerified) {
	    this.BatchName = BatchName;
	    this.SiteId = SiteId;
	    this.UserIdKeyVerified=UserIdKeyVerified;
	  }

  
  public void setBatchId(long BatchId) {
    this.BatchId = BatchId;
  }
  
  public long getBatchId() {
    return BatchId;
  }

  public String getBatchName() {
    return BatchName;
  }

  public void setBatchName(String BatchName) {
    this.BatchName = BatchName;
  }

  public String getSiteId() {
	    return SiteId;
	  }
  
  public void setSiteId(String SiteId) {
	 this.SiteId = SiteId;
	  }
	  
  public String getUserIdKeyVerified() {
	  return UserIdKeyVerified;
		  }

 public void setUserIdKeyVerified(String UserIdKeyVerified) {
	 this.UserIdKeyVerified = UserIdKeyVerified;
		  }

  @Override
  public String toString() {
    return "BatchDocument [Batch_id=" + BatchId + ", BatchName="
  + BatchName + ", SiteId=" + SiteId + ", UserIdKeyVerified=" + UserIdKeyVerified + "]";
  }

}
