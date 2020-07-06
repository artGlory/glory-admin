package com.spring.common.config.shardingJDBC.tableRule;

import io.shardingjdbc.core.api.config.TableRuleConfiguration;

/**
 * 分库分表-表规则
 */
public interface TableRuleInterface {

    /**
     * 生成表规则（sharding根据表规则分库分表）
     */
    TableRuleConfiguration buildTableRuleConfiguration();
}
