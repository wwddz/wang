package com.yzq.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderForm implements Serializable {
  private Integer id;

  private Date modified;

  private Integer display;

  private Integer uid;

  private Integer sellerId;

  private Integer shopId;

  private Integer quantity;

  private String context;

  private String recipient;

  private String rephone;

  private String readdress;

  private Integer status;


  public void setReaddress(String readdress) {
    this.readdress = readdress == null ? null : readdress.trim();
  }

  public Date getModified() {
    return modified == null ? null : (Date) modified.clone();
  }

  public void setModified(Date modified) {
    this.modified = modified == null ? null : (Date) modified.clone();
  }

  public void setContext(String context) {
    this.context = context == null ? null : context.trim();
  }
}