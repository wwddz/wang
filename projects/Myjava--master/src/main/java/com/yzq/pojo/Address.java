package com.yzq.pojo;

import java.io.Serializable;

public class Address implements Serializable {
  private Integer id;

  private String address;

  private String recipient;

  private String rephone;

  private Boolean deaddress;

  private Integer uid;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address == null ? null : address.trim();
  }

  public String getRecipient() {
    return recipient;
  }

  public void setRecipient(String recipient) {
    this.recipient = recipient == null ? null : recipient.trim();
  }

  public String getRephone() {
    return rephone;
  }

  public void setRephone(String rephone) {
    this.rephone = rephone == null ? null : rephone.trim();
  }

  public Boolean getDeaddress() {
    return deaddress;
  }

  public void setDeaddress(Boolean deaddress) {
    this.deaddress = deaddress;
  }

  public Integer getUid() {
    return uid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }
}