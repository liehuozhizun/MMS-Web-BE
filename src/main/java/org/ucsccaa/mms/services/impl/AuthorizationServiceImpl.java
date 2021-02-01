package org.ucsccaa.mms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ucsccaa.mms.domains.Authorization;
import org.ucsccaa.mms.repositories.AuthorizationRepository;
import org.ucsccaa.mms.services.AuthorService;

import javax.annotation.PostConstruct;
import java.util.*;


@Service
public class AuthorizationServiceImpl implements AuthorService {
    @Autowired
    private AuthorizationRepository authorizationRepo;

    private Set<Authorization.Authority_POST> authoritiesPost_1 = new HashSet<>();
    private Set<Authorization.Authority_GET> authoritiesGet_1 = new HashSet<>();
    private Set<Authorization.Authority_PUT> authoritiesPut_1 = new HashSet<>();
    private Set<Authorization.Authority_DELETE> authoritiesDELETE_1 = new HashSet<>();

    private Set<Authorization.Authority_POST> authoritiesPost_2 = new HashSet<>();
    private Set<Authorization.Authority_GET> authoritiesGet_2 = new HashSet<>();
    private Set<Authorization.Authority_PUT> authoritiesPut_2 = new HashSet<>();
    private Set<Authorization.Authority_DELETE> authoritiesDELETE_2 = new HashSet<>();

    private Set<Authorization.Authority_POST> authoritiesPost_3 = new HashSet<>();
    private Set<Authorization.Authority_GET> authoritiesGet_3 = new HashSet<>();
    private Set<Authorization.Authority_PUT> authoritiesPut_3 = new HashSet<>();
    private Set<Authorization.Authority_DELETE> authoritiesDELETE_3 = new HashSet<>();

    private Set<Authorization.Authority_POST> authoritiesPost_4 = new HashSet<>();
    private Set<Authorization.Authority_GET> authoritiesGet_4 = new HashSet<>();
    private Set<Authorization.Authority_PUT> authoritiesPut_4 = new HashSet<>();
    private Set<Authorization.Authority_DELETE> authoritiesDELETE_4 = new HashSet<>();

    private Set<Authorization.Authority_POST> authoritiesPost_5 = new HashSet<>();
    private Set<Authorization.Authority_GET> authoritiesGet_5 = new HashSet<>();
    private Set<Authorization.Authority_PUT> authoritiesPut_5 = new HashSet<>();
    private Set<Authorization.Authority_DELETE> authoritiesDELETE_5 = new HashSet<>();

    private Hashtable<Authorization.LEVEL, Set<Authorization.Authority_POST>> postAuthoritiesTable = new Hashtable<>();
    private Hashtable<Authorization.LEVEL, Set<Authorization.Authority_PUT>> putAuthoritiesTable = new Hashtable<>();
    private Hashtable<Authorization.LEVEL, Set<Authorization.Authority_DELETE>> deleteAuthoritiesTable = new Hashtable<>();
    private Hashtable<Authorization.LEVEL, Set<Authorization.Authority_GET>> getAuthoritiesTable = new Hashtable<>();

    @PostConstruct
    public void initializor() {
        Authorization authorization1 = authorizationRepo.findByLevel(Authorization.LEVEL.LEVEL_1);
        if(authorization1 == null) {
            Authorization tempAuthorization = new Authorization();
            authoritiesGet_1.add(Authorization.Authority_GET.READ_STAFF_DEPT);
            authoritiesGet_1.add(Authorization.Authority_GET.READ_STAFF_POSITION);
            tempAuthorization.setLevel(Authorization.LEVEL.LEVEL_1);
            tempAuthorization.setAuthoritySet_GET(authoritiesGet_1);
            authorizationRepo.save(tempAuthorization);
        } else {
            authoritiesPost_1 = authorization1.getAuthoritySet_POST();
            authoritiesGet_1 = authorization1.getAuthoritySet_GET();
            authoritiesPut_1 = authorization1.getAuthoritySet_PUT();
            authoritiesDELETE_1 = authorization1.getAuthoritySet_DELETE();
        }

        Authorization authorization2 = authorizationRepo.findByLevel(Authorization.LEVEL.LEVEL_2);
        if(authorization2 == null) {
            Authorization tempAuthorization = new Authorization();
            authoritiesGet_2.add(Authorization.Authority_GET.READ_STAFF_DEPT);
            authoritiesGet_2.add(Authorization.Authority_GET.READ_STAFF_POSITION);
            authoritiesGet_2.add(Authorization.Authority_GET.READ_MEMBER_NAME);
            authoritiesGet_2.add(Authorization.Authority_GET.READ_MEMBER_EMAIL);
            authoritiesGet_2.add(Authorization.Authority_GET.READ_MEMBER_DEGREE);
            tempAuthorization.setLevel(Authorization.LEVEL.LEVEL_2);
            tempAuthorization.setAuthoritySet_GET(authoritiesGet_2);
            authorizationRepo.save(tempAuthorization);
        } else {
            authoritiesPost_2 = authorization2.getAuthoritySet_POST();
            authoritiesGet_2 = authorization2.getAuthoritySet_GET();
            authoritiesPut_2 = authorization2.getAuthoritySet_PUT();
            authoritiesDELETE_2 = authorization2.getAuthoritySet_DELETE();
        }

        Authorization authorization3 = authorizationRepo.findByLevel(Authorization.LEVEL.LEVEL_3);
        if(authorization3 == null) {
            Authorization tempAuthorization = new Authorization();
            authoritiesGet_3.add(Authorization.Authority_GET.READ_STAFF_DEPT);
            authoritiesGet_3.add(Authorization.Authority_GET.READ_STAFF_POSITION);
            authoritiesGet_3.add(Authorization.Authority_GET.READ_MEMBER_NAME);
            authoritiesGet_3.add(Authorization.Authority_GET.READ_MEMBER_EMAIL);
            authoritiesGet_3.add(Authorization.Authority_GET.READ_MEMBER_DEGREE);
            authoritiesGet_3.add(Authorization.Authority_GET.READ_OPT_ALL);
            authoritiesGet_3.add(Authorization.Authority_GET.READ_ORDERS_ALL);

            authoritiesPut_3.add(Authorization.Authority_PUT.EDIT_MEMBER);
            authoritiesPut_3.add(Authorization.Authority_PUT.EDIT_OPT);
            authoritiesPut_3.add(Authorization.Authority_PUT.EDIT_ORDERS);

            authoritiesPost_3.add(Authorization.Authority_POST.ADD_MEMBER);
            authoritiesPost_3.add(Authorization.Authority_POST.ADD_OPT);
            authoritiesPost_3.add(Authorization.Authority_POST.ADD_ORDERS);

            authoritiesDELETE_3.add(Authorization.Authority_DELETE.DELETE_MEMBER);
            authoritiesDELETE_3.add(Authorization.Authority_DELETE.DELETE_OPT);
            authoritiesDELETE_3.add(Authorization.Authority_DELETE.DELETE_ORDERS);

            tempAuthorization.setLevel(Authorization.LEVEL.LEVEL_3);
            tempAuthorization.setAuthoritySet_GET(authoritiesGet_3);
            tempAuthorization.setAuthoritySet_PUT(authoritiesPut_3);
            tempAuthorization.setAuthoritySet_POST(authoritiesPost_3);
            tempAuthorization.setAuthoritySet_DELETE(authoritiesDELETE_3);
            authorizationRepo.save(tempAuthorization);
        } else {
            authoritiesPost_3 = authorization3.getAuthoritySet_POST();
            authoritiesGet_3 = authorization3.getAuthoritySet_GET();
            authoritiesPut_3 = authorization3.getAuthoritySet_PUT();
            authoritiesDELETE_3 = authorization3.getAuthoritySet_DELETE();
        }

        Authorization authorization4 = authorizationRepo.findByLevel(Authorization.LEVEL.LEVEL_4);
        if(authorization4 == null) {
            Authorization tempAuthorization = new Authorization();
            authoritiesGet_4.add(Authorization.Authority_GET.READ_STAFF_ALL);
            authoritiesGet_4.add(Authorization.Authority_GET.READ_MEMBER_ALL);
            authoritiesGet_4.add(Authorization.Authority_GET.READ_OPT_ALL);
            authoritiesGet_4.add(Authorization.Authority_GET.READ_ORDERS_ALL);

            authoritiesPut_4.add(Authorization.Authority_PUT.EDIT_MEMBER);
            authoritiesPut_4.add(Authorization.Authority_PUT.EDIT_OPT);
            authoritiesPut_4.add(Authorization.Authority_PUT.EDIT_ORDERS);

            authoritiesPost_4.add(Authorization.Authority_POST.ADD_MEMBER);
            authoritiesPost_4.add(Authorization.Authority_POST.ADD_OPT);
            authoritiesPost_4.add(Authorization.Authority_POST.ADD_ORDERS);

            authoritiesDELETE_4.add(Authorization.Authority_DELETE.DELETE_MEMBER);
            authoritiesDELETE_4.add(Authorization.Authority_DELETE.DELETE_OPT);
            authoritiesDELETE_4.add(Authorization.Authority_DELETE.DELETE_ORDERS);

            tempAuthorization.setLevel(Authorization.LEVEL.LEVEL_4);
            tempAuthorization.setAuthoritySet_GET(authoritiesGet_4);
            tempAuthorization.setAuthoritySet_PUT(authoritiesPut_4);
            tempAuthorization.setAuthoritySet_POST(authoritiesPost_4);
            tempAuthorization.setAuthoritySet_DELETE(authoritiesDELETE_4);
            authorizationRepo.save(tempAuthorization);
        } else {
            authoritiesPost_4 = authorization4.getAuthoritySet_POST();
            authoritiesGet_4 = authorization4.getAuthoritySet_GET();
            authoritiesPut_4 = authorization4.getAuthoritySet_PUT();
            authoritiesDELETE_4 = authorization4.getAuthoritySet_DELETE();
        }

        Authorization authorization5 = authorizationRepo.findByLevel(Authorization.LEVEL.LEVEL_5);
        if(authorization5 == null) {
            Authorization tempAuthorization = new Authorization();
            authoritiesGet_5.add(Authorization.Authority_GET.READ_STAFF_ALL);
            authoritiesGet_5.add(Authorization.Authority_GET.READ_MEMBER_ALL);
            authoritiesGet_5.add(Authorization.Authority_GET.READ_OPT_ALL);
            authoritiesGet_5.add(Authorization.Authority_GET.READ_ORDERS_ALL);
            authoritiesGet_5.add(Authorization.Authority_GET.READ_RECORD_ALL);

            authoritiesPut_5.add(Authorization.Authority_PUT.EDIT_MEMBER);
            authoritiesPut_5.add(Authorization.Authority_PUT.EDIT_OPT);
            authoritiesPut_5.add(Authorization.Authority_PUT.EDIT_ORDERS);
            authoritiesPut_5.add(Authorization.Authority_PUT.EDIT_STAFF);
            authoritiesPut_5.add(Authorization.Authority_PUT.EDIT_RECORD);

            authoritiesPost_5.add(Authorization.Authority_POST.ADD_MEMBER);
            authoritiesPost_5.add(Authorization.Authority_POST.ADD_OPT);
            authoritiesPost_5.add(Authorization.Authority_POST.ADD_ORDERS);
            authoritiesPost_5.add(Authorization.Authority_POST.ADD_STAFF);
            authoritiesPost_5.add(Authorization.Authority_POST.ADD_RECORD);

            authoritiesDELETE_5.add(Authorization.Authority_DELETE.DELETE_MEMBER);
            authoritiesDELETE_5.add(Authorization.Authority_DELETE.DELETE_OPT);
            authoritiesDELETE_5.add(Authorization.Authority_DELETE.DELETE_ORDERS);
            authoritiesDELETE_5.add(Authorization.Authority_DELETE.DELETE_STAFF);
            authoritiesDELETE_5.add(Authorization.Authority_DELETE.DELETE_RECORD);

            tempAuthorization.setLevel(Authorization.LEVEL.LEVEL_5);
            tempAuthorization.setAuthoritySet_GET(authoritiesGet_5);
            tempAuthorization.setAuthoritySet_PUT(authoritiesPut_5);
            tempAuthorization.setAuthoritySet_POST(authoritiesPost_5);
            tempAuthorization.setAuthoritySet_DELETE(authoritiesDELETE_5);
            authorizationRepo.save(tempAuthorization);
        } else {
            authoritiesPost_5 = authorization5.getAuthoritySet_POST();
            authoritiesGet_5 = authorization5.getAuthoritySet_GET();
            authoritiesPut_5 = authorization5.getAuthoritySet_PUT();
            authoritiesDELETE_5 = authorization5.getAuthoritySet_DELETE();
        }

        getAuthoritiesTable.put(Authorization.LEVEL.LEVEL_1, authoritiesGet_1);
        getAuthoritiesTable.put(Authorization.LEVEL.LEVEL_2, authoritiesGet_2);
        getAuthoritiesTable.put(Authorization.LEVEL.LEVEL_3, authoritiesGet_3);
        getAuthoritiesTable.put(Authorization.LEVEL.LEVEL_4, authoritiesGet_4);
        getAuthoritiesTable.put(Authorization.LEVEL.LEVEL_5, authoritiesGet_5);
        putAuthoritiesTable.put(Authorization.LEVEL.LEVEL_1, authoritiesPut_1);
        putAuthoritiesTable.put(Authorization.LEVEL.LEVEL_2, authoritiesPut_2);
        putAuthoritiesTable.put(Authorization.LEVEL.LEVEL_3, authoritiesPut_3);
        putAuthoritiesTable.put(Authorization.LEVEL.LEVEL_4, authoritiesPut_4);
        putAuthoritiesTable.put(Authorization.LEVEL.LEVEL_5, authoritiesPut_5);

        postAuthoritiesTable.put(Authorization.LEVEL.LEVEL_1, authoritiesPost_1);
        postAuthoritiesTable.put(Authorization.LEVEL.LEVEL_2, authoritiesPost_2);
        postAuthoritiesTable.put(Authorization.LEVEL.LEVEL_3, authoritiesPost_3);
        postAuthoritiesTable.put(Authorization.LEVEL.LEVEL_4, authoritiesPost_4);
        postAuthoritiesTable.put(Authorization.LEVEL.LEVEL_5, authoritiesPost_5);

        deleteAuthoritiesTable.put(Authorization.LEVEL.LEVEL_1, authoritiesDELETE_1);
        deleteAuthoritiesTable.put(Authorization.LEVEL.LEVEL_2, authoritiesDELETE_2);
        deleteAuthoritiesTable.put(Authorization.LEVEL.LEVEL_3, authoritiesDELETE_3);
        deleteAuthoritiesTable.put(Authorization.LEVEL.LEVEL_4, authoritiesDELETE_4);
        deleteAuthoritiesTable.put(Authorization.LEVEL.LEVEL_5, authoritiesDELETE_5);
    }

    public Boolean checkAuthority(String level, String method, String uri) {
        Authorization authorization = new Authorization();
        if (method.equals("GET")) {
            authorization.setAuthoritySet_GET(getAuthoritiesTable.get(Authorization.LEVEL.valueOf(level)));
            for(Authorization.Authority_GET authorityTempElement : authorization.getAuthoritySet_GET()) {
                if(authorityTempElement.toString().contains(uri)) {
                    return true;
                }
            }
        } else if (method.equals("PUT")) {
            authorization.setAuthoritySet_PUT(putAuthoritiesTable.get(Authorization.LEVEL.valueOf(level)));
            for(Authorization.Authority_PUT authorityTempElement : authorization.getAuthoritySet_PUT()) {
                if (authorityTempElement.toString().contains(uri)) {
                    return true;
                }
            }
        } else if (method.equals("POST")) {
            authorization.setAuthoritySet_POST(postAuthoritiesTable.get(Authorization.LEVEL.valueOf(level)));
            for(Authorization.Authority_POST authorityTempElement : authorization.getAuthoritySet_POST()) {
                if (authorityTempElement.toString().contains(uri)) {
                    return true;
                }
            }
        } else {
            authorization.setAuthoritySet_DELETE(deleteAuthoritiesTable.get(Authorization.LEVEL.valueOf(level)));
            for(Authorization.Authority_DELETE authorityTempElement : authorization.getAuthoritySet_DELETE()) {
                if (authorityTempElement.toString().contains(uri)) {
                    return true;
                }
            }
        }

        return false;
    }
    public Authorization getByLevel(Authorization.LEVEL level) {
        Authorization authorization = authorizationRepo.findByLevel(level);
        if (authorization != null) {
            return authorization;
        } else {
            throw new RuntimeException("Invalid argument(this level is null)");
        }
    }

    public void addAuthority(Authorization.LEVEL level, String authority) {
        if (level == null) {
            throw new RuntimeException("level can't be NULL!");
        }
        Authorization authorization = authorizationRepo.findByLevel(level);
        if (authorization != null) {
            if (authority.contains("READ") || authority.contains("GET")) {
                authorization.getAuthoritySet_GET().add(Authorization.Authority_GET.valueOf(authority.toUpperCase().trim()));
            } else if (authority.contains("EDIT") || authority.contains("PUT")) {
                authorization.getAuthoritySet_PUT().add(Authorization.Authority_PUT.valueOf(authority.toUpperCase().trim()));
            } else if (authority.contains("ADD") || authority.contains("POST")) {
                authorization.getAuthoritySet_POST().add(Authorization.Authority_POST.valueOf(authority.toUpperCase().trim()));
            } else {
                authorization.getAuthoritySet_DELETE().add(Authorization.Authority_DELETE.valueOf(authority.toUpperCase().trim()));
            }
            authorizationRepo.save(authorization);
        } else {
            throw new RuntimeException("level is Invalid!");

        }
    }

}

