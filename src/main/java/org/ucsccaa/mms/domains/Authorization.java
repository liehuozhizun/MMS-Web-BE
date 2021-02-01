package org.ucsccaa.mms.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authorization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING)
    private LEVEL level;
    @ElementCollection
    private Set<Authority_GET> authoritySet_GET;
    @ElementCollection
    private Set<Authority_PUT> authoritySet_PUT;
    @ElementCollection
    private Set<Authority_POST> authoritySet_POST;
    @ElementCollection
    private Set<Authority_DELETE> authoritySet_DELETE;


    public enum LEVEL {
        LEVEL_1,
        LEVEL_2,
        LEVEL_3,
        LEVEL_4,
        LEVEL_5
    }

    public enum Authority_GET {
        // LEVEL 1
        READ_STAFF_DEPT,
        READ_STAFF_POSITION,
        // LEVEL 2
        READ_MEMBER_NAME,
        READ_MEMBER_EMAIL,
        READ_MEMBER_DEGREE,
        // LEVEL 3
        READ_OPT_ALL,
        READ_ORDERS_ALL,
        // LEVEL 4
        READ_MEMBER_ALL,
        READ_STAFF_ALL,
        // LEVEL 5
        READ_RECORD_ALL,
    }

    public enum Authority_PUT {
        // LEVEL 3
        EDIT_MEMBER,
        EDIT_OPT,
        EDIT_ORDERS,
        // LEVEL 5
        EDIT_STAFF,
        EDIT_RECORD
    }

    public enum Authority_POST {
        // LEVEL 3
        ADD_MEMBER,
        ADD_OPT,
        ADD_ORDERS,
        // LEVEL 5
        ADD_STAFF,
        ADD_RECORD

    }

    public enum Authority_DELETE {
        //LEVEL 3
        DELETE_MEMBER,
        DELETE_OPT,
        DELETE_ORDERS,
        //LEVEL 5
        DELETE_STAFF,
        DELETE_RECORD
    }
}