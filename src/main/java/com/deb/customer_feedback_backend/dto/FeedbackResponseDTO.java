package com.deb.customer_feedback_backend.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackResponseDTO {
	public String submittedBy;
    public List<FeedbackAnswerDTO> answers;
}
