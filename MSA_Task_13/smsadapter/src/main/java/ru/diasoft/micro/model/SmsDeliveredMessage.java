/*
 * Created by DQCodegen
 */
package ru.diasoft.micro.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SmsDeliveredMessage {

    @JsonProperty("quid")
    protected String quid;

}
