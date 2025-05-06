package br.com.rotafood.api.common.application.service;

import br.com.rotafood.api.common.application.dto.AddressDto;
import br.com.rotafood.api.common.application.dto.CustomerDto;
import br.com.rotafood.api.common.domain.entity.Address;
import br.com.rotafood.api.common.domain.entity.Customer;
import br.com.rotafood.api.common.domain.entity.CustomerAddress;
import br.com.rotafood.api.common.domain.repository.CustomerRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> getByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    @Transactional
    public Customer createOrUpdateWithAddressIfDelivery(CustomerDto dto, AddressDto addressDto) {
        Customer customer = customerRepository.findByPhone(dto.phone())
                .orElseGet(() -> {
                    Customer newCustomer = new Customer();
                    newCustomer.setPhone(dto.phone());
                    return newCustomer;
                });

        customer.setName(dto.name());

        if (addressDto != null) {
            boolean alreadyLinked = false;

            for (CustomerAddress ca : customer.getAddresses()) {
                Address a = ca.getAddress();
                if (a.getId() != null && a.getId().equals(addressDto.id())) {
                    a.updateFromAddressDto(addressDto);
                    alreadyLinked = true;
                    break;
                }
            }

            if (!alreadyLinked) {
                Address newAddress = addressDto.id() != null
                    ? new Address(addressDto.id(), addressDto.country(), addressDto.state(), addressDto.city(),
                        addressDto.neighborhood(), addressDto.postalCode(), addressDto.streetName(),
                        addressDto.streetNumber(), addressDto.formattedAddress(), addressDto.complement(),
                        addressDto.latitude(), addressDto.longitude())
                    : new Address(addressDto);

                CustomerAddress customerAddress = new CustomerAddress();
                customerAddress.setCustomer(customer);
                customerAddress.setAddress(newAddress);
                customer.getAddresses().add(customerAddress);
            }
        }

        return customerRepository.save(customer);
    }


}
