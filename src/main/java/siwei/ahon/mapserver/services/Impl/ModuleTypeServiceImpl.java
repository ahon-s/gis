package siwei.ahon.mapserver.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import siwei.ahon.mapserver.annotation.FilterFiledHelper;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.mapper.ModuleTypeMapper;
import siwei.ahon.mapserver.model.ModuleType;
import siwei.ahon.mapserver.pojo.PageFilterPojo;
import siwei.ahon.mapserver.services.ModuleTypeService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ModuleTypeServiceImpl implements ModuleTypeService {
    @Resource
    ModuleTypeMapper moduleTypeMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Override
    public Map<String, String> getModuleTypeCodeToName() {
        List<ModuleType> moduleTypes = moduleTypeMapper.selectList(null);
        Map<String, String> collect = moduleTypes.stream().collect(Collectors.toMap(e -> e.getTypeCode(), e -> e.getTypeName()));
        return collect;
    }

    @Override
    public Map<String, String> getModuleTypeNameToCode() {
        List<ModuleType> moduleTypes = moduleTypeMapper.selectList(null);
        Map<String, String> collect = moduleTypes.stream().collect(Collectors.toMap(e -> e.getTypeName(),e -> e.getTypeCode()));
        return collect;
    }

    @Override
    public Integer addModuleType(ModuleType moduleType) {
        moduleTypeMapper.insert(moduleType);
        return moduleType.getId();
    }

    @Override
    public Integer deleteModuleType(ModuleType moduleType) {
        moduleTypeMapper.deleteById(moduleType);
        return moduleType.getId();
    }

    @Override
    public PageData<ModuleType> getModuleTypeList(ModuleType moduleType, PageFilterPojo pf) {
        QueryWrapper<ModuleType> maQueryWrapper = new QueryWrapper<>(moduleType);
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(maQueryWrapper,moduleType);
        Page<ModuleType> moduleTypePage = new Page<ModuleType>(pf.getPageNum(), pf.getPageSize());
        IPage page = moduleTypeMapper.selectPage(moduleTypePage,queryWrapper);
        PageData pd = new PageData(page);
        return pd;
    }
}
