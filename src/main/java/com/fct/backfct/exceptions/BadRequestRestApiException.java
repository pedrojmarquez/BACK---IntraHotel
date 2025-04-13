package com.fct.backfct.exceptions;

import com.fct.backfct.exceptions.errors.RestApiErrorCode;
import com.fct.backfct.exceptions.errors.RestApiErrorDetail;
import org.springframework.http.HttpStatus;

import java.util.List;

public class BadRequestRestApiException extends CustomRestApiException {

    public BadRequestRestApiException(RestApiErrorCode code) {
        super(HttpStatus.BAD_REQUEST, code);
    }

    public BadRequestRestApiException(RestApiErrorCode code, String customMessage) {
        super(HttpStatus.BAD_REQUEST, code, customMessage);
    }

    public BadRequestRestApiException(RestApiErrorCode code, List<RestApiErrorDetail> errorsDetail) {
        super(HttpStatus.BAD_REQUEST, code, errorsDetail);
    }

    public BadRequestRestApiException(RestApiErrorCode code, String customMessage, List<RestApiErrorDetail> errorsDetail) {
        super(HttpStatus.BAD_REQUEST, code, customMessage, errorsDetail);
    }
}
