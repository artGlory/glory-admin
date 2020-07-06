package com.spring.common.config.shardingJDBC.tableRule;

import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.routing.strategy.none.NoneShardingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NoneShardingTableRules {

    private static final String[] actualDataNodes = new String[]{
            "multi_module_db.admin_role",
            "multi_module_db.admin_privilege",
            "multi_module_db.admin_user",
            "multi_module_db.system_config",
            "multi_module_db.admin_role_privilege"
    };


    public static List<TableRuleConfiguration> buildTableRuleConfiguration() {
        List<TableRuleConfiguration> tableRuleConfigurationList = new ArrayList<>();
        for (String dataNode : actualDataNodes) {
            /**
             * 获取逻辑表
             */
            String logicTableName = dataNode.split("\\.")[1];
/*
数据结点
 */
            String inlineActualDataNodes = dataNode;
        /*
        表规则
         */
            TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration();
            tableRuleConfiguration.setLogicTable(logicTableName);
            tableRuleConfiguration.setActualDataNodes(inlineActualDataNodes);
/*
数据库分片
 */
            tableRuleConfiguration.setDatabaseShardingStrategyConfig(NoneShardingStrategy::new);
            tableRuleConfiguration.setTableShardingStrategyConfig(NoneShardingStrategy::new);
            log.info("【tableRuleConfiguration-init】" + tableRuleConfiguration.getLogicTable() + "  " + inlineActualDataNodes);
            tableRuleConfigurationList.add(tableRuleConfiguration);
        }

        return tableRuleConfigurationList;
    }
}
