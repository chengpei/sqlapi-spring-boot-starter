package top.chengpei.sqlapi.modules.service;

import top.chengpei.sqlapi.modules.entity.SqlapiConfig;
import top.chengpei.sqlapi.modules.util.Page;

import java.util.Map;

/**
 * API生成配置 服务类
 *
 * @author dsj
 * @since 2024-08-16
 */
public interface SqlapiConfigService {


	SqlapiConfig getByApiPostfix(String apiPostfix);
    Page<Map<String, Object>> executeQuery(String apiPostfix, Map<String, String> params);

    Map<String, Object> executeQueryOne(String apiPostfix, Map<String, String> params);
}
