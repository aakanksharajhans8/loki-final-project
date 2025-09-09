
package com.example.customer.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
@Entity
@Table(name = "customers")
public class Customer {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(name = "customer_uuid", nullable = false, unique = true) private String customerUuid = UUID.randomUUID().toString();
  @Column(nullable = false) private String firstName;
  @Column(nullable = false) private String lastName;
  @Column(nullable = false, unique = true) private String email;
  private String phone; private LocalDate dob;
  @Column(nullable = false) private boolean kycVerified = false;
  public Long getId(){ return id; }
  public String getCustomerUuid(){ return customerUuid; }
  public String getFirstName(){ return firstName; } public void setFirstName(String f){ this.firstName = f; }
  public String getLastName(){ return lastName; } public void setLastName(String l){ this.lastName = l; }
  public String getEmail(){ return email; } public void setEmail(String e){ this.email = e; }
  public String getPhone(){ return phone; } public void setPhone(String p){ this.phone = p; }
  public LocalDate getDob(){ return dob; } public void setDob(LocalDate d){ this.dob = d; }
  public boolean isKycVerified(){ return kycVerified; } public void setKycVerified(boolean k){ this.kycVerified = k; }
}
