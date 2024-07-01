package siwei.ahon.mapserver.services.Impl;

import org.springframework.stereotype.Service;
import siwei.ahon.mapserver.mapper.WebModuleMapper;
import siwei.ahon.mapserver.model.WebModule;
import siwei.ahon.mapserver.services.WebModuleService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WebModuleServiceImpl implements WebModuleService {

    @Resource
    WebModuleMapper webModuleMapper;

    @Override
    public List<WebModule> getWebModuleList() {
        List<WebModule> webModules = webModuleMapper.selectList(null);
        return webModules;
    }
}
