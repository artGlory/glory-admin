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
        public static final String updateAdminInfo = "/updateAdminInfo";
        public static final String addAdminInfo = "/addAdminInfo";
        public static final String delAdminInfo = "/delAdminInfo";

    }

    public static class AdminRole {
        public static final String path = "/adminRole";
        public static final String getRolesTree = "/getRolesTree";
        public static final String addRole = "/addRole";
        public static final String updateRole = "/updateRole";
        public static final String deleteRole = "/deleteRole";
        public static final String getAllSelfSubRole = "/getAllSelfSubRole";
    }

    public static class AdminPrivilege {
        public static final String path = "/adminPrivelege";
        public static final String getAdminPermissionTree = "/getAdminPermissionTree";
        public static final String updateAdminRolePermissions = "/updateAdminRolePermissions";
    }

    public static class System {
        public static final String path = "/amdinSystem";
        public static final String getPlatformInfo = "getPlatformInfo";
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
