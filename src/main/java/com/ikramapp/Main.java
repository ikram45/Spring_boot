package com.ikramapp;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")



public class Main {
    private final CustomerRepository customerRepositorya;
    public Main(CustomerRepository customerRepositorya){
        this.customerRepositorya= customerRepositorya;
    }
    public static void main(String[] args){
        //System.out.println("hey");
        SpringApplication.run(Main.class,args);
    }
    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepositorya.findAll();
    }
    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){

    }
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepositorya.save(customer);

    }
    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepositorya.deleteById(id);
    }

    @PutMapping({"customerId"})
    public void updateCustomer(@PathVariable("customerId") Integer id,NewCustomerRequest request){

        List<Customer> Thecustomers= getCustomers();
        for(Customer c:Thecustomers){
            if(c.getId()==id){
                c.setName(request.name());
                c.setEmail(request.email());
                c.setAge(request.age());
                customerRepositorya.save(c);
                break;
            }
        }
      
    }
}