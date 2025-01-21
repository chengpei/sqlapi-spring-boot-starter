package top.chengpei.sqlapi.modules.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.chengpei.sqlapi.modules.util.Page;
import top.chengpei.sqlapi.modules.util.R;
import top.chengpei.sqlapi.modules.service.SqlapiConfigService;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class SqlapiController {

    @Resource
    private SqlapiConfigService apiGenerateConfigService;

    @GetMapping("/pageQuery/{apiPostfix}")
    public R<Page<Map<String, Object>>> query(@PathVariable("apiPostfix") String apiPostfix, @RequestParam(required = false) Map<String, String> params) {
        Page<Map<String, Object>> pages = apiGenerateConfigService.executeQuery(apiPostfix, params);
        return R.data(pages);
    }

    @GetMapping("/queryOne/{apiPostfix}")
    public R<Map<String, Object>> queryOne(@PathVariable("apiPostfix") String apiPostfix, @RequestParam(required = false) Map<String, String> params) {
        Map<String, Object> result = apiGenerateConfigService.executeQueryOne(apiPostfix, params);
        return R.data(result);
    }
}
