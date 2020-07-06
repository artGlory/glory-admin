package com.spring.common.help.flyway;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;

public class FlywayTest {

    public static void main(String[] args) {
        String dbName = "multi_module_db";
        String url = "jdbc:mysql://127.0.0.1:3306/"
                + dbName
                + "?useUnicode=true"
                + "&characterEncoding=UTF-8"
                + "&allowMultiQueries=true"
                + "&rewriteBatchedStatements=true"
                + "&useSSL=false"
                + "&serverTimezone=GMT%2B8";
        String user = "root";
        String password = "root";

        ClassicConfiguration classicConfiguration = new ClassicConfiguration();
//        classicConfiguration.setLocationsAsStrings(new String[]{"db/migration"});
        classicConfiguration.setLocationsAsStrings(new String[]{"db/" + dbName});
        Flyway flyway = Flyway.configure()
                .configuration(classicConfiguration)
                .dataSource(url, user, password)
                .load();
        // 删除当前 schema 下所有表
        flyway.clean();
        // 创建 flyway_schema_history 表
        flyway.baseline();

        // 删除 flyway_schema_history 表中失败的记录
//        flyway.repair();

        // 检查 sql 文件
//        flyway.validate();
        // 执行数据迁移
        flyway.migrate();
    }
}