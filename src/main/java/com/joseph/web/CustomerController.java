package com.joseph.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.joseph.entity.Customer;
// import com.joseph.exception.ResourceNotFoundException;
import com.joseph.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping({"/list", "/"})
    public String listCustomers(Model theModel) {
        List<Customer> theCustomers = customerService.getCustomers();
        theModel.addAttribute("customers", theCustomers);
        return "list-customers";
    }

    @GetMapping("/customerForm")
    public String customerForm(Model model) {
        model.addAttribute("customer", new Customer());
        System.out.println("********* Add Customer *********");
        System.out.println("inside the Customer Form for adding new Customer ");
        System.out.println("Customer : "+model.getAttribute("customer").toString());
        System.out.println("********* end Add Customer **********");
        return "customerForm";
    }
    @GetMapping("/customerForm/{id}")
    public String customerForm(@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.getCustomer(id);
        model.addAttribute("customer", customer);

        System.out.println("********* Update Customer *********");
        System.out.println("inside the Customer Form for updating existing Customer ");
        System.out.println("Customer : "+model.getAttribute("customer").toString());
        System.out.println("********* end Update Customer **********");
        return "customerForm";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
        System.out.println("********* Saving(add / update ) Customer *********");
        System.out.println("inside the Customer Save method ");
        System.out.println("Customer : "+theCustomer.toString());
        System.out.println("********* Add Customer **********");
        customerService.saveCustomer(theCustomer);
        return "redirect:/customers/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) throws NullPointerException {
        customerService.deleteCustomer(id);
        return "redirect:/customers/list";
    }
}