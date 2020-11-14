package org.ucsccaa.mms.domains;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String type;
    private String name;
    private String gender;
    private String birth;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String wechat;
    private String country;
    private String address1;
    private String address2;
    private String city;
    private String postal;
    @Column(unique = true)
    private String stdId;
    private String program;
    private String endDate;
    private String degree;
    private String major1;
    private String major2;
    private String careerStatus;
    private String company;
    private String position;
    private Boolean searchAuthorization;
}
