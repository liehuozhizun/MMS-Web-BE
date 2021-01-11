package org.ucsccaa.mms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class MembershipManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MembershipManagementSystemApplication.class, args);
    }
}
