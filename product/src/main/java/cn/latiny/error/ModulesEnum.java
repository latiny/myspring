package cn.latiny.error;

public enum ModulesEnum {

    SYSTEM(Integer.valueOf(500), "系统错误码"),

    PRIVILEGE_MANAGEMENT(Integer.valueOf(670), "权限管理", "security"),
    FILE_MANAGEMENT(Integer.valueOf(680), "文件管理"),
    GATEWAY_MANAGEMENT(Integer.valueOf(690), "网关管理"),
    MESSAGE_NOTICE(Integer.valueOf(700), "消息通知"),

    NOTICE_SERVICE(Integer.valueOf(890), "消息服务", "notice"),
    INVITE_SERVICE(Integer.valueOf(900), "邀请码服务"),

    MESSAGE_PUSH(Integer.valueOf(950), "消息通知", "push"),
    USER_SERVICE(Integer.valueOf(960), "用户服务", "user"),
    PLATFORM_SERVICE(Integer.valueOf(970), "平台服务"),
    ADD_SERVICE(Integer.valueOf(980), "增值服务"),
    OPEN_SDK_SERVICE(Integer.valueOf(990), "OPEN_SDK"),
    OPEN_API_SERVICE(Integer.valueOf(1000), "OPEN_API服务");

    private Integer moduleCode;
    private String moduleName;
    private String moduleNickName;

    private ModulesEnum(Integer moduleCode, String moduleName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
    }

    private ModulesEnum(Integer moduleCode, String moduleName, String moduleNickName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.moduleNickName = moduleNickName;
    }

    public Integer getModuleCode() {
        return this.moduleCode;
    }

    public String getModuleNickName() {
        return this.moduleNickName;
    }

    public String getModuleName() {
        return this.moduleName;
    }
}
