package top.chengpei.sqlapi.modules.service.impl;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.chengpei.sqlapi.modules.entity.SqlapiConfig;
import top.chengpei.sqlapi.modules.service.SqlapiConfigService;
import top.chengpei.sqlapi.modules.util.Page;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SqlapiConfigServiceImpl implements SqlapiConfigService {

	private final TemplateEngine templateEngine = TemplateUtil.createEngine(new TemplateConfig());

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public SqlapiConfig getByApiPostfix(String apiPostfix) {
		return jdbcTemplate.queryForObject("select * from t_api_generate_config a where a.api_postfix='"+apiPostfix+"'", new BeanPropertyRowMapper<>(SqlapiConfig.class));
	}

	private String getApiStatement(String apiPostfix) {
		SqlapiConfig sqlapiConfig = getByApiPostfix(apiPostfix);
		if (sqlapiConfig == null) {
			throw new RuntimeException("接口不存在，" + apiPostfix);
		}
		String statement = sqlapiConfig.getStatementContent();
		if (!StringUtils.hasText(statement)) {
			throw new RuntimeException("接口statement未配置，" + apiPostfix);
		}
		return statement;
	}

	@Override
	public Page<Map<String, Object>> executeQuery(String apiPostfix, Map<String, String> params) {
		String statementTemplate = getApiStatement(apiPostfix);
		Template template = templateEngine.getTemplate(statementTemplate);
		String statement = template.render(params);
		int current = 1;
		int size = 10;
		String currentStr = params.get("current");
		if (StringUtils.hasText(currentStr)) {
			current = Integer.parseInt(currentStr);
		}
		String sizeStr = params.get("size");
		if (StringUtils.hasText(sizeStr)) {
			size = Integer.parseInt(sizeStr);
		}
		int offset = (current - 1) * size;
		Long total = jdbcTemplate.queryForObject("select count(1) from (" + statement + ") x", Long.class);
		List<Map<String, Object>> records = jdbcTemplate.queryForList("select * from (" + statement + ") x limit "+offset+", " + size);
		Page<Map<String, Object>> pageResult = new Page<>();
		pageResult.setRecords(records);
		pageResult.setTotal(total == null ? 0 : total);
		pageResult.setCurrent(current);
		pageResult.setSize(size);
		return pageResult;
	}

	@Override
	public Map<String, Object> executeQueryOne(String apiPostfix, Map<String, String> params) {
		String statementTemplate = getApiStatement(apiPostfix);
		Template template = templateEngine.getTemplate(statementTemplate);
		String statement = template.render(params);
		return jdbcTemplate.queryForMap(statement);
	}
}
