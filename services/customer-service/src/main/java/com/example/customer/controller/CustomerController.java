package com.example.customer.controller;

import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
  private final CustomerRepository repo;

  public CustomerController(CustomerRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<Customer> all() {
    return repo.findAll();
  }

  @PostMapping
  public Customer create(@RequestBody Customer c) {
    return repo.save(c);
  }

  @GetMapping("/{uuid}")
  public Customer one(@PathVariable("uuid") String uuid) {
    return repo.findByCustomerUuid(uuid).orElseThrow();
  }

  @PutMapping("/{uuid}")
  public Customer update(@PathVariable("uuid") String uuid, @RequestBody Customer in) {
    Customer c = repo.findByCustomerUuid(uuid).orElseThrow();
    c.setFirstName(in.getFirstName());
    c.setLastName(in.getLastName());
    c.setEmail(in.getEmail());
    c.setPhone(in.getPhone());
    return repo.save(c);
  }

  @DeleteMapping("/{uuid}")
  public void delete(@PathVariable("uuid") String uuid) {
    repo.delete(repo.findByCustomerUuid(uuid).orElseThrow());
  }

  @PostMapping("/{uuid}/kyc")
  public Customer toggle(
          @PathVariable("uuid") String uuid,
          @RequestParam(name = "verified") boolean verified
  ) {
    Customer c = repo.findByCustomerUuid(uuid).orElseThrow();
    c.setKycVerified(verified);
    return repo.save(c);
  }
}
