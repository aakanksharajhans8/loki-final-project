package com.cams.notification.register.model;
public class RegisteredUser {
  private Long id; private String username; private String email; private String phoneNumber; private String passwordHash;
  public RegisteredUser(){}
  public RegisteredUser(String username,String email,String phoneNumber,String passwordHash){
    this.username=username; this.email=email; this.phoneNumber=phoneNumber; this.passwordHash=passwordHash;
  }
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getUsername(){return username;} public void setUsername(String v){username=v;}
  public String getEmail(){return email;} public void setEmail(String v){email=v;}
  public String getPhoneNumber(){return phoneNumber;} public void setPhoneNumber(String v){phoneNumber=v;}
  public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
}
