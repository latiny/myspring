package cn.latiny.error;

public class BusinessException extends RuntimeException implements ErrorCode {

    private static final long serialVersionUID = 647248946610917157L;
    private int code;
    private String message;

    public BusinessException() {
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}