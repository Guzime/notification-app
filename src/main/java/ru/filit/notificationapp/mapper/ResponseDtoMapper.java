package ru.filit.notificationapp.mapper;

import org.springframework.stereotype.Component;
import ru.filit.notificationapp.dto.ResponseDto;
import ru.filit.notificationapp.dto.ResponseResultDto;
import ru.filit.notificationapp.type.StatusCode;

@Component
public class ResponseDtoMapper {

    public ResponseDto createResponseDto(Object object, StatusCode code, String message) {
        return ResponseDto.builder().object(object).responseResultDto(new ResponseResultDto(code, message)).build();
    }
}
