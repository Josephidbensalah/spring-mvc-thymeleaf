package com.joseph.web;

import com.joseph.DTO.CustomerDTO;
import com.joseph.DTO.OrderDTO;
import com.joseph.DTO.OrderItemDTO;
import com.joseph.DTO.ProductDTO;
import com.joseph.entity.*;
import com.joseph.repository.OrderItemRepository;
import com.joseph.service.CustomerService;
import com.joseph.service.OrderService;
import com.joseph.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private  CustomerService customerService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping({"/list", "/"})
    public String listOrders(Model theModel) {
        List<Order> theOrders = orderService.getAllOrders();
        theModel.addAttribute("orders", theOrders);
        System.out.println("***************************************************");
        System.out.println("Orders :"+ theOrders);
        System.out.println("****************************************************");
        return "list-orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) throws NullPointerException {
        try {
            Order orderToRemove = orderService.getOrderById(id);

            if (orderToRemove != null) {

                // Delete the order
                orderService.deleteOrder(orderToRemove);

                System.out.println("Deleted Successfully");
            }

            return "redirect:/orders/list";
        } catch (Exception e) {
            System.out.println("exception on DELETE :"+e.getMessage());
            // Handle exceptions, such as NullPointerException or DataIntegrityViolationException
            // You might want to log the exception or show an error message to the user
            return "redirect:/orders/list";
        }
    }


    @GetMapping("/orderForm")
    public String showOrderForm(Model model) {
        OrderDTO orderDTO = new OrderDTO();
        List<CustomerDTO> customerDTOs = toCustomerDTOs(customerService.getCustomers());
        List<ProductDTO> productDTOs = toProductDTOs(productService.getAllProducts());
        model.addAttribute("orderDTO", orderDTO);
        model.addAttribute("products", productDTOs);
        model.addAttribute("customers",customerDTOs);
        return "orderForm";
    }

    @GetMapping("/orderForm/{id}")
    public String showOrderForm(@PathVariable("id") Long id, Model model) {
        Order order = orderService.getOrderById(id);
        List<CustomerDTO> customerDTOs = toCustomerDTOs(customerService.getCustomers());
        List<ProductDTO> productDTOs = toProductDTOs(productService.getAllProducts());
        System.out.println("Customers size : "+customerDTOs.size()+" Products size : "+productDTOs.size());
        OrderDTO orderDTO = convertEntityToDTO(order);
        System.out.println("OrderDTO : "+orderDTO.toString());
        model.addAttribute("orderDTO", orderDTO);
        model.addAttribute("products", productDTOs);
        model.addAttribute("customers",customerDTOs);
        return "orderForm";
    }

    @PostMapping("/saveOrder2")
    public String saveOrder(@ModelAttribute("orderDTO") @Valid OrderDTO orderDTO, BindingResult result) {

        if (result.hasErrors()) {
            // Log or handle binding errors
            System.out.println("Result");
            System.out.println(result.getModel());
            return "redirect:/orders/orderForm";
        }
        System.out.println("*****************************************************");
        System.out.println("orderDTO to be saved : "+orderDTO.toString());
        // Convert DTO to Entity
        Order order = convertDTOToEntity(orderDTO);
        System.out.println("order : "+order.toString());
        // Save the order
         orderService.saveOrder(order);

        return "redirect:/orders/";
    }

    public List<ProductDTO> toProductDTOs(List<Product> list) {
        return list.stream().map(p -> modelMapper.map(p,ProductDTO.class)).collect(Collectors.toList());
    }

    public List<CustomerDTO> toCustomerDTOs(List<Customer> list) {
        return list.stream().map(c -> modelMapper.map(c,CustomerDTO.class)).collect(Collectors.toList());
    }

    public Order convertDTOToEntity(OrderDTO orderDTO) {
        System.out.println("************ DTO CONVERTER **********");
        // Convert the OrderDTO to an Order entity
        Order order = modelMapper.map(orderDTO, Order.class);

        // Convert the CustomerDTO to a Customer entity and set it on the order
        Customer customer = customerService.getCustomer(orderDTO.getCustomerId());
        order.setCustomer(customer);
        System.out.println("Order : Customer =>  "+order.getCustomer().getEmail());

        // Retrieve the products from the database
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            if(orderItemDTO.getProductId() != null){
                Product product = productService.getProductById(orderItemDTO.getProductId());
                // Create a new OrderItem object
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                // Set other properties of the order item
                orderItem.setQuantity(orderItemDTO.getQuantity());
                // Associate the order item with the order
                orderItem.setOrder(order);
                // Add the order item to the order
                orderItems.add(orderItem);
            }

        }

//        for (Long productId : orderDTO.getProductIds()) {
//            Product product = productService.getProductById(productId);
//            // Create a new OrderItem object
//            OrderItem orderItem = new OrderItem();
//            orderItem.setProduct(product);
//            // Set other properties of the order item
//            orderItem.setQuantity(12);
//            // Associate the order item with the order
//            orderItem.setOrder(order);
//            // Add the order item to the order
//            orderItems.add(orderItem);
//        }


        order.setOrderItems(orderItems);
        System.out.println("Order : orderItems =>  "+order.getOrderItems().size());
        return order;
    }

    public OrderDTO convertEntityToDTO(Order order) {
        System.out.println("************ ENTITY CONVERTER **********");
        // Convert the Order entity to an OrderDTO
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        // Convert the Customer entity to a CustomerDTO and set it on the orderDTO
        orderDTO.setCustomerId(order.getCustomer().getId());
        System.out.println("OrderDTO : Customer => "+orderDTO.getCustomerId());

        // Retrieve the product IDs from the order items
        List<Long> productIds = new ArrayList<>();
//        for (OrderItem orderItem : order.getOrderItems()) {
//             Get the product ID and add it to the list
//            productIds.add(orderItem.getProduct().getId());

//        }

        orderDTO.setOrderItems(
                order.getOrderItems()
                        .stream()
                        .map(ot -> modelMapper.map(ot, OrderItemDTO.class))
                        .collect(Collectors.toList()));

        // Set the product IDs on the orderDTO
//        orderDTO.setProductIds(productIds);
//        System.out.println("OrderDTO : productIds => "+orderDTO.getProductIds().size());
        System.out.println("OrderDTO : orderItems => "+orderDTO.getOrderItems().size());

        return orderDTO;
    }



}