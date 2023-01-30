package io.ori.task.mdbcurrencyboot.service.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Component
@Document("pairs")
public class Pairs implements Serializable {
    @Id
    private String id;
    private String low;
    private String high;
    private String last;
    private String pair;
}
