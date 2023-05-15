package com.swn.jamu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    /*private final BranchProcurementService branchProcurementService;
    private final DistributorProcurementService distributorProcurementService;

    @Autowired
    public NotificationConfig(BranchProcurementService branchProcurementService,
                              DistributorProcurementService distributorProcurementService) {
        this.branchProcurementService = branchProcurementService;
        this.distributorProcurementService = distributorProcurementService;
    }*/

    @Bean(name = "branchNotification")
    public String getBranchNotification() {
        //System.out.println(username);
        return "2";
//        return branchProcurementService.countProcurementWaiting(username);
    }
}
