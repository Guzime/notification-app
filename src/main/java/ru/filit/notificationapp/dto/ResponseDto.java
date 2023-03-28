package ru.filit.notificationapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseDto {
    @JsonProperty("data")
    private Object object;
    @JsonProperty("result")
    private ResponseResultDto responseResultDto;
}
