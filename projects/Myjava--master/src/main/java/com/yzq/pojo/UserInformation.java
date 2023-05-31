package com.yzq.pojo;

import java.io.Serializable;
import java.util.Date;

public class UserInformation implements Serializable {
  private Integer id;

  private Date modified;

  private String username;

  private String phone;

  private String realname;

  private String clazz;

  private String sno;

  private String dormitory;

  private String gender;

  private Date createtime;

  private String avatar;

  private String school;

  private String campus;

  private boolean manager;

  private String contact;

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public boolean isManager() {
    return manager;
  }

  public void setManager(boolean manager) {
    this.manager = manager;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getModified() {
    return modified == null ? null : (Date) modified.clone();
  }

  public void setModified(Date modified) {
    this.modified = modified == null ? null : (Date) modified.clone();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username == null ? null : username.trim();
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone == null ? null : phone.trim();
  }

  public String getRealname() {
    return realname;
  }

  public void setRealname(String realname) {
    this.realname = realname == null ? null : realname.trim();
  }

  public String getClazz() {
    return clazz;
  }

  public void setClazz(String clazz) {
    this.clazz = clazz == null ? null : clazz.trim();
  }

  public String getSno() {
    return sno;
  }

  public void setSno(String sno) {
    this.sno = sno == null ? null : sno.trim();
  }

  public String getDormitory() {
    return dormitory;
  }

  public void setDormitory(String dormitory) {
    this.dormitory = dormitory == null ? null : dormitory.trim();
  }

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public String getCampus() {
    return campus;
  }

  public void setCampus(String campus) {
    this.campus = campus;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender == null ? null : gender.trim();
  }

  public Date getCreatetime() {
    return createtime == null ? null : (Date) createtime.clone();
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime == null ? null : (Date) createtime.clone();
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar == null ? null : avatar.trim();
  }
}