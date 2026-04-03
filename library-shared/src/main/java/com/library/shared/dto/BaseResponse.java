package com.library.shared.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public abstract class BaseResponse{
    @JsonSerialize(using = ToStringSerializer.class)
    protected Long id;
}
