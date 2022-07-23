package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.service.domain.AdminServiceRequestHistoryFinder;
import com.khedmatkar.demo.service.dto.ServiceRequestListViewDTO;
import com.khedmatkar.demo.service.domain.CustomerServiceRequestHistoryFinder;
import com.khedmatkar.demo.service.domain.ServiceRequestFinder;
import com.khedmatkar.demo.service.domain.SpecialistServiceRequestHistoryFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/serviceRequests")
public class ServiceRequestHistoryController {
    private final AccountService accountService;
    private final ApplicationContext context;

    public ServiceRequestHistoryController(AccountService accountService,
                                           ApplicationContext context) {
        this.accountService = accountService;
        this.context = context;
    }

    @GetMapping("/")
    @RolesAllowed("ROLE_USER")
    public List<ServiceRequestListViewDTO> getHistory(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        var user = accountService.findUserFromUserDetails(userDetails);
        var finder = getServiceRequestHistoryFinderForUser(user);
        return finder.getServiceRequests(user)
                .stream()
                .map(ServiceRequestListViewDTO::from)
                .collect(Collectors.toList());
    }

    private ServiceRequestFinder getServiceRequestHistoryFinderForUser(User user) { // todo: do a better design
        switch (user.getType()) {
            case CUSTOMER:
                return context.getBean(CustomerServiceRequestHistoryFinder.class);
            case SPECIALIST:
                return context.getBean(SpecialistServiceRequestHistoryFinder.class);
            case ADMIN:
                return context.getBean(AdminServiceRequestHistoryFinder.class);
            default:
        }
        throw new IllegalStateException("Unexpected value: " + user.getType());
    }
}
