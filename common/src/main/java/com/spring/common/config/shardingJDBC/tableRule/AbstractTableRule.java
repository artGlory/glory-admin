package com.spring.common.config.shardingJDBC.tableRule;

public abstract class AbstractTableRule implements TableRuleInterface {

    /**
     * 检查并获取逻辑表
     *
     * @return
     */
    protected String checkLogicTableName(String[] atualDataNodes) {
        String logicTableName = null;
        for (String actualDataNode : atualDataNodes) {
            String logicTN = actualDataNode.substring(actualDataNode.indexOf(".") + 1, actualDataNode.lastIndexOf("_"));
            if (logicTableName == null) {
                logicTableName = logicTN;
            } else {
                if (!logicTableName.equals(logicTN)) {
                    throw new IllegalArgumentException("数据结点设置错误，逻辑表获取失败");
                }
            }
        }
        return logicTableName;
    }

    /**
     * 数据结点
     */
    protected String buildInlineActualDataNodes(String[] atualDataNodes) {
        StringBuffer actualDataNodesBuffer = new StringBuffer();
        for (String actualDataNode : atualDataNodes) {
            if (actualDataNodesBuffer.length() == 0) {
                actualDataNodesBuffer.append(actualDataNode);
            } else {
                actualDataNodesBuffer.append("," + actualDataNode);
            }
        }
        return actualDataNodesBuffer.toString();
    }

}
