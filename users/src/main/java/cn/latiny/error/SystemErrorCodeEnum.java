package cn.latiny.error;

public enum SystemErrorCodeEnum implements ErrorCode {

    ILLEGAL_PARAM(1, "参数[param=%s]: %s"),
    REQUEST_PARAM_FORMAT_ILLEGAL(3, "请求体格式不正确"),
    NULL_VALUE(4, "空值！"),
    DUPLICATE_ERROR_CODE(6, "存在重复错误码"),
    DUPLICATE_MODULE_CODE(7, "存在重复使用模块编码"),
    SIGNED_INSPECTE_FAIL(8, "验签失败"),
    TRANSCODING_FAIL(9, "转码失败"),
    NO_IMPLEMENTATION(10, "暂无实现"),
    DUPLICATE_KEY_EXCEPTION(11, "请勿重复提交"),
    REQUEST_EXIST_WRONG(12, "请求存在问题，需要重试"),
    LOCK_FAILED(400, "服务器正忙，请稍后重试！"),
    INVALID_TOKEN(401, "token was invalid."),
    EXPIRED_TOKEN(402, "token was expired."),
    PERFORM_FORBIDDEN(403, "没有该操作权限！"),
    NO_LOGIN(404, "请登录！"),
    BLACKLIST_ERROR(405, "暂无权限，请联系运营方！"),
    UNEXPECTED(500, "未预期的异常！");

    private int code;
    private String message;
    private ModulesEnum module;

    private SystemErrorCodeEnum(int value, String message) {
        this.module = ModulesEnum.SYSTEM;
        this.code = this.module.getModuleCode().intValue() * ErrorCodeConstants.MODULE_LEVEL.intValue() + value;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
