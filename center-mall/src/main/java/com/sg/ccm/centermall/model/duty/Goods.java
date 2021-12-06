package com.sg.ccm.equipment.model.duty;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "dutyentry")
public class Goods implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "GOODS_NAME")
  private Integer goodsName;

  @Column(name = "GOODS_PRICE")
  private String goodsPrice;

  @Column(name = "GOODS_INPUT_TIME")
  private Date goodInputTime;

  @Column(name = "COMM_INFO")
  private String commInfo;


  public Goods() {
  }
}
