package com.khedmatkar.demo.account;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.repository.CustomerRepository;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import com.khedmatkar.demo.account.repository.UserRepository;
import com.khedmatkar.demo.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
public class AccountService {
    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final CustomerRepository customerRepository;

    public AccountService(UserRepository userRepository,
                          SpecialistRepository specialistRepository,
                          CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.specialistRepository = specialistRepository;
        this.customerRepository = customerRepository;
    }

    public void thrrow() {
        throw new NotFoundException("asdfasdfsadf");
    }

    public User findUserFromUserDetails(UserDetails userDetails) {
        String email = userDetails.getUsername();
        // todo: remove this exception and define appropriate exceptions
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found"
                ));
    }

    public User findConcreteUserClassFromUserDetails(UserDetails userDetails) {
        String email = userDetails.getUsername();
        var specialist = specialistRepository.findByEmail(email);
        if (specialist.isPresent())
            return specialist.get();
        var customer = customerRepository.findByEmail(email);
        if (customer.isPresent())
            return customer.get();

        throw new RuntimeException();
        // todo: throw user not found exception
    }
}
