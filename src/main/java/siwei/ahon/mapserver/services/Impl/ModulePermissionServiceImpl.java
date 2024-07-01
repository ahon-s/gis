package siwei.ahon.mapserver.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import siwei.ahon.mapserver.annotation.FilterFiledHelper;
import siwei.ahon.mapserver.pojo.dto.ModulePermissionDTO;
import siwei.ahon.mapserver.expection.BaseException;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.mapper.ModulePermissionMapper;
import siwei.ahon.mapserver.mapper.PrjModuleMembershipMapper;
import siwei.ahon.mapserver.model.ModulePermission;
import siwei.ahon.mapserver.pojo.PageFilterPojo;
import siwei.ahon.mapserver.services.ModulePermissionService;
import siwei.ahon.mapserver.services.ModuleTypeService;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;


@Service
public class ModulePermissionServiceImpl implements ModulePermissionService {

    @Resource
    FilterFiledHelper filedHelper;

    @Resource
    ModulePermissionMapper modulePermissionMapper;

    @Resource
    ModuleTypeService moduleTypeService;

    @Resource
    PrjModuleMembershipMapper prjModuleMembershipMapper;

    @Override
    public PageData<ModulePermissionDTO> getModulePermissionList(ModulePermission modulePermission,PageFilterPojo pf) {
        Map<String, String> moduleType = moduleTypeService.getModuleTypeCodeToName();
        Map<String, String> moduleTypeNameToCode = moduleTypeService.getModuleTypeNameToCode();
        modulePermission.setTypeCode(moduleTypeNameToCode.get(modulePermission.getTypeCode()));
        QueryWrapper<ModulePermission> mpQueryWrapper = new QueryWrapper<>(modulePermission);
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(mpQueryWrapper,modulePermission);
        Page<ModulePermission> modulePermissionPage = new Page<ModulePermission>(pf.getPageNum(),pf.getPageSize());
        IPage page = modulePermissionMapper.selectPage(modulePermissionPage,queryWrapper);
        List<ModulePermission> records = page.getRecords();
        List<ModulePermissionDTO> collect = records.stream().map(e -> {
            if(isEmpty(moduleType.get(e.getTypeCode()))){
                throw new BaseException("没有此类型");
            }
            ModulePermissionDTO modulePermissionDTO = new ModulePermissionDTO();
            BeanUtils.copyProperties(e, modulePermissionDTO);
            modulePermissionDTO.setTypeName(moduleType.get(e.getTypeCode()));
            return modulePermissionDTO;
        }).collect(Collectors.toList());
        PageData<ModulePermissionDTO> pd = new PageData<>(page.getCurrent(),page.getSize(), page.getTotal(), collect);
        return pd;
    }

    @Override
    public Integer addModulePermission(ModulePermission modulePermission) {
        preModulePermission(modulePermission);
        QueryWrapper<ModulePermission> queryWrapper = new QueryWrapper();
        queryWrapper.eq("type_code",modulePermission.getTypeCode());
        queryWrapper.orderByDesc("code");
        String code = modulePermissionMapper.selectList(queryWrapper).get(0).getCode();
        String real_code = null;
        try {
            real_code = String.valueOf(Integer.parseInt(code)+1);
        } catch (Exception e){
            real_code = code.substring(0,code.length()-1)+(Integer.parseInt(code.substring(code.length()-1))+1);
        }
        modulePermission.setCode(real_code);
        modulePermissionMapper.insert(modulePermission);
        return modulePermission.getId();

    }

    @Override
    public Integer deleteModulePermission(ModulePermission modulePermission) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("module_id",modulePermission.getId());
        prjModuleMembershipMapper.delete(queryWrapper);
        modulePermissionMapper.deleteById(modulePermission);
        return modulePermission.getId();

    }

    @Override
    public Integer updateModulePermission(ModulePermission modulePermission) {
        preModulePermission(modulePermission);
        modulePermissionMapper.updateById(modulePermission);
        return modulePermission.getId();
    }

    private void preModulePermission(ModulePermission modulePermission){
        Map<String, String> moduleTypeNameToCode = moduleTypeService.getModuleTypeNameToCode();
        if(isEmpty(moduleTypeNameToCode.get(modulePermission.getTypeCode()))){
            throw new BaseException("没有此类型名称");
        }
        modulePermission.setTypeCode(moduleTypeNameToCode.get(modulePermission.getTypeCode()));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type_code",modulePermission.getTypeCode());
        queryWrapper.eq("code",modulePermission.getCode());
        if (!isEmpty( modulePermissionMapper.selectOne(queryWrapper))){
            throw new BaseException("此类型的对应code已经存在");
        }

    }

}
