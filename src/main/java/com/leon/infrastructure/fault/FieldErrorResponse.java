package com.leon.infrastructure.fault;

import lombok.Value;

@Value
class FieldErrorResponse {

    private final String fieldName;

    private final String errorMsg;

}
