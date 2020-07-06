package com.spring.common.config.shardingJDBC;


import com.alibaba.druid.pool.DruidDataSource;
import com.spring.common.config.shardingJDBC.tableRule.LogAdminLoginTableRule;
import com.spring.common.config.shardingJDBC.tableRule.LogOperateTableRule;
import com.spring.common.config.shardingJDBC.tableRule.NoneShardingTableRules;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.exceptionSorter}")
    private boolean exceptionSorter;

    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;

    @Value("${spring.datasource.useGlobalDataSourceStat}")
    private boolean useGlobalDataSourceStat;
    /**
     * 数据库分片
     */
    private final static String[] dbs = new String[]{
            "multi_module_db"
            , "multi_module_db_01"
    };

    /**
     * 连接池
     *
     * @return
     */
    @Bean
    public DataSource dataSource() {
        if (dbs == null) {
            throw new IllegalArgumentException("dbs==null");
        }
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        for (String db : dbs) {
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(driverClassName);
            druidDataSource.setUrl(dbUrl.replace("XXX", db));
            druidDataSource.setUsername(username);
            druidDataSource.setPassword(password);
            druidDataSource.setInitialSize(initialSize);
            druidDataSource.setMinIdle(minIdle);
            druidDataSource.setMaxActive(maxActive);
            druidDataSource.setMaxWait(maxWait);
            druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            druidDataSource.setValidationQuery(validationQuery);
            druidDataSource.setTestWhileIdle(testWhileIdle);
            druidDataSource.setTestOnBorrow(testOnBorrow);
            druidDataSource.setTestOnReturn(testOnReturn);
            druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
            druidDataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
            druidDataSource.setConnectionProperties(connectionProperties);
            dataSourceMap.put(db, druidDataSource);
        }

        // 配置分片规则

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        NoneShardingTableRules.buildTableRuleConfiguration().forEach(tableRuleConfiguration -> {//无分片规则
            shardingRuleConfig.getTableRuleConfigs().add(tableRuleConfiguration);
        });
        shardingRuleConfig.getTableRuleConfigs()//logOperate分片规则
                .add(new LogOperateTableRule().buildTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs()//logAdminLog分片规则
                .add(new LogAdminLoginTableRule().buildTableRuleConfiguration());

        // 获取数据源对象
        DataSource dataSource = null;
        try {
            Properties properties = new Properties();
//            properties.put("sql.show", "true");
            dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap<String, Object>(), properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

}