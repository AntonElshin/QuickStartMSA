/*
 * Created by DQCodegen
 */
package ru.diasoft.micro.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsDeliveredMessage {

    @JsonProperty("quid")
    protected String quid;

}
