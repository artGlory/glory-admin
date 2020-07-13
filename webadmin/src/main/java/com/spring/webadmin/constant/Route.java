package com.spring.webadmin.constant;

public class Route {

    public static final String main = "/";
    public static final String path = "/api";

    public static class Admin {
        public static final String path = "/adminUser";
        public static final String adminLogin = "/adminLogin";
        public static final String getAdminInfo = "/getAdminInfo";
        public static final String adminLogout = "/adminLogout";
        public static final String getPageAdminInfo = "/getPageAdminInfo";
        public static final String updateAdminRole = "/updateAdminRole";
        public static final String addAdminInfo = "/addAdminInfo";
        public static final String delAdminInfo = "/delAdminInfo";
        public static final String updateAdminPassword = "/updateAdminPassword";
        public static final String updateSelfPassword = "/updateSelfPassword";
        public static final String getSelfOperateTimeline = "/getSelfOperateTimeline";
        public static final String getGoogleKey = "/getGoogleKey";
        public static final String bindGoogleKey = "/bindGoogleKey";
        public static final String loginWithGooleAuthentication = "/loginWithGooleAuthentication";
        public static final String forbidAdminLogin = "/forbidAdminLogin";
        public static final String deleteAdminGoogleKey = "/deleteAdminGoogleKey";
    }

    public static class AdminRole {
        public static final String path = "/adminRole";
        public static final String getRolesTree = "/getRolesTree";
        public static final String addRole = "/addRole";
        public static final String updateRole = "/updateRole";
        public static final String deleteRole = "/deleteRole";
        public static final String getAllSelfSubRole = "/getAllSelfSubRole";
        public static final String getAllSubRole = "/getAllSubRole";
    }

    public static class AdminPrivilege {
        public static final String path = "/adminPrivelege";
        public static final String getAdminPermissionTree = "/getAdminPermissionTree";
        public static final String updateAdminRolePermissions = "/updateAdminRolePermissions";
    }

    public static class AdminSystem {
        public static final String path = "/amdinSystem";
        public static final String getPlatformInfo = "getPlatformInfo";
        public static final String getPageConfig = "getPageConfig";
        public static final String updateSystemConfig = "updateSystemConfig";
        public static final String getGoogleAuthenticationDownload = "getGoogleAuthenticationDownload";
    }

    public static class AdminLoginLog {
        public static final String path = "/adminLoginLog";
        public static final String getPageLoginLog = "/getPageLoginLog";
    }
    public static class AdminOperateLog{
        public static final String path="/adminOperateLog";
        public static final String getPageOperateLog="/getPageOperateLog";
    }

}
