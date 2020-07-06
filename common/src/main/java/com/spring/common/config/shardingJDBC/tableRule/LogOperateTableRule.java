package com.spring.common.config.shardingJDBC.tableRule;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class LogOperateTableRule extends AbstractTableRule {
    private static final String[] actualDataNodes = new String[]{
            "multi_module_db_01.log_operate_01"
            , "multi_module_db_01.log_operate_02"
    };

    @Override
    public TableRuleConfiguration buildTableRuleConfiguration() {

        /**
         * 获取逻辑表
         */
        String logicTableName = checkLogicTableName(actualDataNodes);
/*
数据结点
 */
        String inlineActualDataNodes = buildInlineActualDataNodes(actualDataNodes);
        /*
        表规则
         */
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration();
        tableRuleConfiguration.setLogicTable(logicTableName);
        tableRuleConfiguration.setActualDataNodes(inlineActualDataNodes);
/*
数据库分片
 */
        StandardShardingStrategyConfiguration standardShardingStrategyConfiguration = new StandardShardingStrategyConfiguration(
                "operator", SelfShardingAlgorithm.class.getName()
        );
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(standardShardingStrategyConfiguration);
        tableRuleConfiguration.setTableShardingStrategyConfig(standardShardingStrategyConfiguration);
        log.info("【tableRuleConfiguration-init】" + tableRuleConfiguration.getLogicTable() + "  " + inlineActualDataNodes);
        return tableRuleConfiguration;
    }

    /**
     * 分库分表算法-单键分库分表
     */
    public static class SelfShardingAlgorithm implements PreciseShardingAlgorithm<String> {
        @Override
        public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
            int num = shardingValue.getValue().hashCode() % availableTargetNames.size() + 1;
            String result = null;
            for (String name : availableTargetNames) {
                if (name.endsWith(String.format("%02d", num))) {
                    result = name;
                }
            }
            return result;
        }
    }

}
