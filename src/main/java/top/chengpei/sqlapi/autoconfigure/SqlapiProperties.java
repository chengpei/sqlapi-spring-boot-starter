package top.chengpei.sqlapi.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sqlapi")
public class SqlapiProperties {

    /**
     * 这里是预留的配置类，暂时没什么用，如果有兴趣可以加上方言的配置，然后修改代码做不同数据库方言的支持
     */
    private String enabled;

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
