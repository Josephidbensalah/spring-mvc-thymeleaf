package com.joseph.services;

import com.joseph.config.PersistenceJPAConfigTest;
import com.joseph.entity.Customer;
import com.joseph.repository.CustomerRepository;
import com.joseph.service.CustomerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { PersistenceJPAConfigTest.class })
@Transactional
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeAll
    @DisplayName("Testing before all")
    public static void beforeAll(){
        System.out.println("before all");
    }

    @BeforeEach
    @DisplayName("Setting up Customer Object ")
    void setUp() {
        // Given : Setup object or precondition
        customer = new Customer();
        customer.setFirstName("Joseph");
        customer.setLastName("Saleh");
        customer.setEmail("joseph@saleh.com");
        customerRepository.save(customer);
        System.out.println("from setUp");
    }

    @Test
    @DisplayName("Testing Saving a new Customer")
    void test_save_Customer() {
        customer = new Customer();
        customer.setFirstName("Ahmed");
        customer.setLastName("lawine");
        customer.setEmail("ahmed@lawin.com");

        // When : Action of behavious that we are going to test
        Customer saveCustomer = customerService.saveCustomer(customer);

        // Then : Verify the output
        assertNotNull(saveCustomer);
        assertEquals(saveCustomer.getFirstName(),"Ahmed");
    }

    @Test
    @DisplayName("Testing Get Customer by ID")
    public void test_get_customer(){
        // We use the already saved customer in db
        assertEquals(1L,customerService.getCustomer(1L).getId());
        assertNotNull(customerService.getCustomer(1L));
        System.out.println(customerService.getCustomer(1L).toString());
    }

    @Test
    @DisplayName("Testing updating a customer...")
     public void test_update_customer(){
        // We get the customer to update
        Customer customerToUpdate = customerRepository.findById(1L).get();

        // We update the Customer email
        customerToUpdate.setEmail("ahmed.updated@mail.com");
        customerService.saveCustomer(customerToUpdate);

        // We verify the new email
        assertEquals("ahmed.updated@mail.com",customerRepository.findById(1L).get().getEmail());
     }

     @Test
     @DisplayName("Testing deleting a given Customer")
     public void test_delete_customer(){
         // We get the customer to delete
//         Customer customerToDelete = customerRepository.findById(1L).get();

         // We delete the customer
         customerService.deleteCustomer(1L);
         Optional<Customer> deletedCustomer = customerRepository.findById(1L);

         // We verify if the customer is empty or not
         assertFalse(deletedCustomer.isPresent());
     }

    @AfterEach
    @DisplayName("Deleting the objects ")
    void tearDown() {
//        customerRepository.deleteAll();
        System.out.println("from tearDown");
    }

    @AfterAll
    @DisplayName("Testing after all")
    public static void afterAll(){
        System.out.println("after all");
    }
}
