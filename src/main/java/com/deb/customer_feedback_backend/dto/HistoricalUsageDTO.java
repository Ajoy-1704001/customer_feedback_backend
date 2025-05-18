package com.deb.customer_feedback_backend.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricalUsageDTO {
	private LocalDate date;
    private Integer count;
    private Integer limit;
    private Double percentage;
}
