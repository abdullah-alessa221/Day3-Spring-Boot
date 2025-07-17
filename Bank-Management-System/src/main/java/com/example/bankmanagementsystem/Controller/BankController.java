package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Model.Customer;
import org.springframework.web.bind.annotation.*;
import com.example.bankmanagementsystem.ApiResponse.ApiResponse;


import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/bank")
public class BankController {

    ArrayList<Customer> customers = new ArrayList<>();
    int currentId = 1;

    @GetMapping("/customers")
    public ArrayList<Customer> getAllCustomers() {
        return customers;
    }

    @PostMapping("/add")
    public ApiResponse addCustomer(@RequestBody Customer customer) {
        customer.setId(currentId++);
        customers.add(customer);
        return new ApiResponse("Customer added successfully", 200);
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateCustomer(@PathVariable int id, @RequestBody Customer updatedCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == id) {
                updatedCustomer.setId(id);
                customers.set(i, updatedCustomer);
                return new ApiResponse("Customer updated successfully", 200);
            }
        }
        return new ApiResponse("Customer not found", 404);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteCustomer(@PathVariable int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                customers.remove(customer);
                return new ApiResponse("Customer deleted successfully", 200);
            }
        }
        return new ApiResponse("Customer not found", 404);
    }

    @PutMapping("/deposit/{id}/{amount}")
    public ApiResponse deposit(@PathVariable int id, @PathVariable double amount) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                customer.setBalance(customer.getBalance() + amount);
                return new ApiResponse("Deposit successful", 200);
            }
        }
        return new ApiResponse("Customer not found", 404);
    }

    @PutMapping("/withdraw/{id}/{amount}")
    public ApiResponse withdraw(@PathVariable int id, @PathVariable double amount) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                if (customer.getBalance() >= amount) {
                    customer.setBalance(customer.getBalance() - amount);
                    return new ApiResponse("Withdrawal successful", 200);
                } else {
                    return new ApiResponse("Insufficient balance", 400);
                }
            }
        }
        return new ApiResponse("Customer not found", 404);
    }
}
