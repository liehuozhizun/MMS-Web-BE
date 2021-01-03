package org.ucsccaa.mms.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LEVEL level;
    @Column(name = "authority_list", columnDefinition = "LONGVARBINARY")
    private ArrayList<Authorities> authorityList;

   public enum LEVEL{
        LEVEL_1,
        LEVEL_2,
        LEVEL_3,
        LEVEL_4,
        LEVEL_5
    }
    public enum Authorities {
        // LEVEL 1
        READ_STAFF_DEPT,
        READ_STAFF_POSITION,
        // LEVEL 2
        READ_MEMBER_NAME,
        READ_MEMBER_EMAIL,
        READ_MEMBER_DEGREE,
        // LEVEL 3
        ADD_MEMBER,
        DELETE_MEMBER,
        ADD_OPT,
        DELETE_OPT,
        READ_OPT_ALL,
        ADD_ORDERS,
        DELETE_ORDERS,
        READ_ORDERS_ALL,
        // LEVEL 4
        READ_MEMBER_ALL,
        READ_STAFF_ALL,
        // LEVEL 5
        ADD_STAFF,
        DELETE_STAFF,
        ADD_RECORD,
        DELETE_RECORD,
        READ_RECORD_ALL,
    }
}