package ru.diasoft.task.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Errors {
    REQUIRED_PARAM(1, "Missing required param(s): %s"),
    NOT_UNIQUE_REFERENCE_SYSNAME(2, "Not unique reference sysname: %s"),
    REFERENCE_NOT_FOUND_BY_ID(50, "Reference not found by id: %s");

    private Integer code;
    private String string;
}
