package io.ori.task.mdbcurrencyboot.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class Pairs implements Serializable {
    private String Id;
    private String low;
    private String high;
    private String pair;
//    private static final Logger logger = LoggerFactory.getLogger(Pairs.class);
}
