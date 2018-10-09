package com.primedice.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Currency {
    Long id;
    Long userId;
    Long depositChange;
}
