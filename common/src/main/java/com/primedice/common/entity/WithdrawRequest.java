package com.primedice.common.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WithdrawRequest {
    private Long value;
    private String unit;
}
