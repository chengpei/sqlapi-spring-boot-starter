package top.chengpei.sqlapi.modules.entity;

import java.io.Serializable;

public class SqlapiConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String apiName;

    /**
     * 后缀
     */
    private String apiPostfix;

    /**
     * SQL
     */
    private String statementContent;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiPostfix() {
        return apiPostfix;
    }

    public void setApiPostfix(String apiPostfix) {
        this.apiPostfix = apiPostfix;
    }

    public String getStatementContent() {
        return statementContent;
    }

    public void setStatementContent(String statementContent) {
        this.statementContent = statementContent;
    }
}
