package ru.diasoft.task.exceptions;

public class BusinessException extends Exception {

    private int code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Errors error, Object... args) {
        super(String.format(error.getString(), args));
        this.code = error.getCode();
    }

    public int getCode() {
        return code;
    }
}
