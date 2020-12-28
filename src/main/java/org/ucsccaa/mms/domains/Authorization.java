package org.ucsccaa.mms.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Authorization {
    @Id
    private LEVEL level;
    private Authorities AuthorList;
}
enum LEVEL{
    LEVEL_1,
    LEVEL_2,
    LEVEL_3,
    LEVEL_4,
    LEVEL_5
}
enum Authorities {

}