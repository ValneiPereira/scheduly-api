package com.scheduly.api.web.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private Integer status;
    private String error;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime timestamp;
}
