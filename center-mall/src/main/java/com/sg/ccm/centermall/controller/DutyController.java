package com.sg.ccm.centermall.controller;

import com.sg.ccm.centermall.model.duty.Customer;
import com.sg.ccm.centermall.repository.dutyrepository.CustomerRepository;
import com.sg.ccm.centermall.repository.dutyrepository.GoodsRepository;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DutyController {
  private static final Logger log = LoggerFactory.getLogger(DutyController.class);

  @Autowired
  private CustomerRepository customerRepository;
  //@Autowired
  //private GoodsRepository goodsRepository;

  @Operation(summary = "This is to fetch All the duty data stored in the key-value store")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "Fetched All the duty data from the key-value store",
              //          content = {@Content(mediaType = "application/json", schema =

              content = {
                  @Content(
                      mediaType = "application/json",
                      array = @ArraySchema(schema = @Schema(implementation = DutyController.class)))
              }),
          @ApiResponse(responseCode = "404", description = "NOt Available", content = @Content)
      })

  // 1 get customer
  @GetMapping("/mall/customer")
  public List<Customer> getduty() {
    return customerRepository.findAll();
  }

  @Modifying
  @PostMapping("/mall/customer")
  public Customer updateduty(@RequestBody Customer duty) {
    return customerRepository.save(duty);
  }

  @Modifying
  @DeleteMapping("/mall/customer")
  public List<Customer> deleteduty(@RequestBody Customer duty) {
    customerRepository.delete(duty);
    return customerRepository.findAll();
  }


}
