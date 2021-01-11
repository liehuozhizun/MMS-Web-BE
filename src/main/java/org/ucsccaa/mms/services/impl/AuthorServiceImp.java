package org.ucsccaa.mms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ucsccaa.mms.domains.Authorization;
import org.ucsccaa.mms.repositories.AuthorizeRepository;
import org.ucsccaa.mms.services.AuthorService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AuthorServiceImp implements AuthorService {
    @Autowired
    AuthorizeRepository authorRepo;
    @PostConstruct
    private void initializeAuthor() {
        List<Authorization> insertAuthorList = new ArrayList<>();
        Authorization checkAuthorLevel1 = authorRepo.findByLevel(Authorization.LEVEL.LEVEL_1);
        if (checkAuthorLevel1 == null) {
            Authorization authorization = new Authorization();
            ArrayList<Authorization.Authorities> authorityList = new ArrayList<>();
            authorityList.add(Authorization.Authorities.READ_STAFF_DEPT);
            authorityList.add(Authorization.Authorities.READ_STAFF_POSITION);
            authorization.setAuthorityList(authorityList);
            authorization.setLevel(Authorization.LEVEL.LEVEL_1);
            insertAuthorList.add(authorization);
        }

        Authorization checkAuthorLevel2 = authorRepo.findByLevel(Authorization.LEVEL.LEVEL_2);
        if (checkAuthorLevel2 == null) {
            Authorization authorization = new Authorization();
            ArrayList<Authorization.Authorities> authorityList = new ArrayList<>();
            authorityList.add(Authorization.Authorities.READ_STAFF_DEPT);
            authorityList.add(Authorization.Authorities.READ_STAFF_POSITION);
            authorityList.add(Authorization.Authorities.READ_MEMBER_NAME);
            authorityList.add(Authorization.Authorities.READ_MEMBER_EMAIL);
            authorityList.add(Authorization.Authorities.READ_MEMBER_DEGREE);
            authorization.setAuthorityList(authorityList);
            authorization.setLevel(Authorization.LEVEL.LEVEL_2);
            insertAuthorList.add(authorization);
        }

        Authorization checkAuthorLevel3 = authorRepo.findByLevel(Authorization.LEVEL.LEVEL_3);
        if (checkAuthorLevel3 == null) {
            Authorization authorization = new Authorization();
            ArrayList<Authorization.Authorities> authorityList = new ArrayList<>();
            authorityList.add(Authorization.Authorities.READ_STAFF_DEPT);
            authorityList.add(Authorization.Authorities.READ_STAFF_POSITION);
            authorityList.add(Authorization.Authorities.READ_MEMBER_NAME);
            authorityList.add(Authorization.Authorities.READ_MEMBER_EMAIL);
            authorityList.add(Authorization.Authorities.READ_MEMBER_DEGREE);
            authorityList.add(Authorization.Authorities.ADD_MEMBER);
            authorityList.add(Authorization.Authorities.DELETE_MEMBER);
            authorityList.add(Authorization.Authorities.ADD_OPT);
            authorityList.add(Authorization.Authorities.DELETE_OPT);
            authorityList.add(Authorization.Authorities.READ_OPT_ALL);
            authorityList.add(Authorization.Authorities.ADD_ORDERS);
            authorityList.add(Authorization.Authorities.DELETE_ORDERS);
            authorityList.add(Authorization.Authorities.READ_ORDERS_ALL);
            authorization.setAuthorityList(authorityList);
            authorization.setLevel(Authorization.LEVEL.LEVEL_3);
            insertAuthorList.add(authorization);
        }
        Authorization checkAuthorLevel4 = authorRepo.findByLevel(Authorization.LEVEL.LEVEL_4);
        if (checkAuthorLevel4 == null) {
            Authorization authorization = new Authorization();
            ArrayList<Authorization.Authorities> authorityList = new ArrayList<>();
            authorityList.add(Authorization.Authorities.ADD_MEMBER);
            authorityList.add(Authorization.Authorities.DELETE_MEMBER);
            authorityList.add(Authorization.Authorities.ADD_OPT);
            authorityList.add(Authorization.Authorities.DELETE_OPT);
            authorityList.add(Authorization.Authorities.READ_OPT_ALL);
            authorityList.add(Authorization.Authorities.ADD_ORDERS);
            authorityList.add(Authorization.Authorities.DELETE_ORDERS);
            authorityList.add(Authorization.Authorities.READ_ORDERS_ALL);
            authorityList.add(Authorization.Authorities.READ_STAFF_ALL);
            authorityList.add(Authorization.Authorities.READ_MEMBER_ALL);
            authorization.setLevel(Authorization.LEVEL.LEVEL_4);
            authorization.setAuthorityList(authorityList);
            insertAuthorList.add(authorization);
        }

        //save level5 authorizations to the database
        Authorization checkAuthorLevel5 = authorRepo.findByLevel(Authorization.LEVEL.LEVEL_5);
        if (checkAuthorLevel5 == null) {
            Authorization authorization = new Authorization();
            ArrayList<Authorization.Authorities> authorityList = new ArrayList<>();
            authorityList.add(Authorization.Authorities.ADD_MEMBER);
            authorityList.add(Authorization.Authorities.DELETE_MEMBER);
            authorityList.add(Authorization.Authorities.ADD_OPT);
            authorityList.add(Authorization.Authorities.DELETE_OPT);
            authorityList.add(Authorization.Authorities.READ_OPT_ALL);
            authorityList.add(Authorization.Authorities.ADD_ORDERS);
            authorityList.add(Authorization.Authorities.DELETE_ORDERS);
            authorityList.add(Authorization.Authorities.READ_ORDERS_ALL);
            authorityList.add(Authorization.Authorities.READ_STAFF_ALL);
            authorityList.add(Authorization.Authorities.READ_MEMBER_ALL);
            authorityList.add(Authorization.Authorities.ADD_STAFF);
            authorityList.add(Authorization.Authorities.DELETE_STAFF);
            authorityList.add(Authorization.Authorities.ADD_RECORD);
            authorityList.add(Authorization.Authorities.DELETE_RECORD);
            authorityList.add(Authorization.Authorities.READ_RECORD_ALL);
            authorization.setLevel(Authorization.LEVEL.LEVEL_5);
            authorization.setAuthorityList(authorityList);
            insertAuthorList.add(authorization);
        }
        // Insert Authorities List into database
        if (!insertAuthorList.isEmpty()) {
            authorRepo.saveAll(insertAuthorList);
        }

    }
    public Boolean checkAuthority(String level, String... authority) {
        Authorization authorization = new Authorization();
        Authorization.LEVEL authorLevel = Authorization.LEVEL.valueOf(level);
        authorization = authorRepo.findByLevel(authorLevel);

        if (authority[0].equals("GET")) authority[0] = "READ";
        if (authority[0].equals("PUT") || authority[0].equals("POST")) authority[0] = "ADD";

        for(int i = 0; i < authorization.getAuthorityList().size(); i++) {
            String tempAuthority = authorization.getAuthorityList().get(i).toString();
            if ((tempAuthority.contains(authority[0]) && tempAuthority.contains(authority[1]))
                && (tempAuthority.contains("READ") && tempAuthority.contains("ALL"))) return true;
            if (tempAuthority.contains(authority[0]) && tempAuthority.contains(authority[1])) return true;
        }
        return false;
    }
    public Authorization getByLevel(Authorization.LEVEL level) {
        Authorization authorization = authorRepo.findByLevel(level);
        if (authorization != null) {
            return authorization;
        } else {
           throw new RuntimeException("Invalid argument(this level is null)");
        }
    }

    public void addAuthority(String level, String authority) {
        Authorization authorization = authorRepo.findByLevel(Authorization.LEVEL.valueOf(level));
        if (authorization != null) {
            authorization.getAuthorityList().add(Authorization.Authorities.valueOf(authority));
            authorRepo.save(authorization);
        } else {
            throw new RuntimeException("Invalid argument(couldn't find this level)");
        }
    }

}
