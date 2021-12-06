package com.sg.ccm.centermall.model.duty;

import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "duty")
public class Customer implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "CUSTOMER_NAME")
  private String customerName;

  @Column(name = "GENDER")
  private String gender;

  @Column(name = "ADDR")
  private String addr;

  @Column(name = "PHONE_NO")
  private String phoneNo;

  @Column(name = "REGISTRY_TIME")
  private Date registryTime;

  @Column(name = "COMM_INFO")
  private String coomInfo;


  public Customer() {
  }
}
