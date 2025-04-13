package br.com.rotafood.api.application.controller.v1;

import br.com.rotafood.api.application.dto.customer.FullCustomerDto;
import br.com.rotafood.api.application.service.customer.CustomerService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiVersion.VERSION + "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/phone/{phone}")
    public FullCustomerDto getCustomerByPhone(@PathVariable String phone) {
        return customerService.getByPhone(phone)
                .map(FullCustomerDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }
}
