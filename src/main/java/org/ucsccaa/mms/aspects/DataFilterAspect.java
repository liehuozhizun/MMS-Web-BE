package org.ucsccaa.mms.aspects;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.ucsccaa.mms.controllers.MemberController;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.models.ServiceResponse;

import java.util.List;

@Aspect
@Component
public class DataFilterAspect {
    public void llll() {
    }

    @Before("execution(* org.ucsccaa.mms.controllers.MemberController.getAll())")
    public void authCheck() {
        System.out.println("----------------- (Before) AUTH CHECK -----------");
    }
//    @After
//    @AfterThrowing(within("org.ucsccaa.mms.controllers.MemberController"))

    @AfterReturning(returning = "list", pointcut = "execution(* org.ucsccaa.mms.controllers.*.*())")
    public ServiceResponse<List<Member>> filterData(ServiceResponse<List<Member>> list) {
        System.out.println("----------------- (AfterReturning) Filter Data  -----------");
        list.getPayload().add(new Member());
        return list;
    }
//
//    @Around("execution(* org.ucsccaa.mms.controllers.MemberController.*())")
//    public void log() {
//        System.out.println("我是个log");
//    }
}