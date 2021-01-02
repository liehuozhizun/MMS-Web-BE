package org.ucsccaa.mms.domains;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dept;
    private String position;
    @ManyToOne
    private Authorization authorization;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="memId", referencedColumnName = "id")
    private Member member;
}
