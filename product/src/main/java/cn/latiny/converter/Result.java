package cn.latiny.converter;

import cn.latiny.error.ErrorCode;

public class Result<T> {
    private boolean success;
    private T data;
    private Integer errorCode;
    private String errorMessage;

    public Result() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String toString() {
        return "Result{success=" + this.success + ", data=" + this.data + ", errorCode=" + this.errorCode + ", errorMessage='" + this.errorMessage + '\'' + '}';
    }

    public static final class Builder {
        private boolean success;
        private Object data;
        private Integer errorCode;
        private String errorMessage;

        public Builder() {
        }

        public Builder success() {
            this.success = true;
            return this;
        }

        public Builder fail(Integer errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder fail(Integer errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder fail(ErrorCode errorCode) {
            this.errorCode = errorCode.getCode();
            this.errorMessage = errorCode.getMessage();
            return this;
        }

        public Builder data(Object o) {
            this.data = o;
            return this;
        }

        public Result build() {
            Result result = new Result();
            result.setSuccess(this.success);
            result.setData(this.data);
            result.setErrorCode(this.errorCode);
            result.setErrorMessage(this.errorMessage);
            return result;
        }
    }
}
