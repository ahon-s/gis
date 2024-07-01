package siwei.ahon.mapserver.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import siwei.ahon.mapserver.pojo.dto.ModulePermissionDTO;
import siwei.ahon.mapserver.pojo.dto.PrjModuleDTO;
import siwei.ahon.mapserver.expection.BaseException;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.mapper.ModulePermissionMapper;
import siwei.ahon.mapserver.mapper.PrjConfigMapper;
import siwei.ahon.mapserver.mapper.PrjModuleMembershipMapper;
import siwei.ahon.mapserver.model.ModulePermission;
import siwei.ahon.mapserver.model.PrjConfig;
import siwei.ahon.mapserver.model.PrjModuleMembership;
import siwei.ahon.mapserver.pojo.PageFilterPojo;
import siwei.ahon.mapserver.services.ModuleTypeService;
import siwei.ahon.mapserver.services.PrjModuleMembershipService;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;
@Slf4j
@Service
public class PrjModuleMembershipImpl implements PrjModuleMembershipService {

    @Resource
    ModulePermissionServiceImpl modulePermissionService;

    @Resource
    PrjModuleMembershipMapper prjModuleMembershipMapper;

    @Resource
    ModulePermissionMapper modulePermissionMapper;

    @Resource
    ModuleTypeService moduleTypeService;

    @Resource
    PrjConfigMapper prjConfigMapper;

    @Override
    public Integer addPrjModuleMembership(PrjModuleMembership prjModuleMembership) {
        PrjConfig prjConfig = prjConfigMapper.selectById(prjModuleMembership.getPrjId());
        if (isEmpty(prjConfig)) throw  new BaseException("不存在此项目");
        ModulePermission modulePermission = modulePermissionMapper.selectById(prjModuleMembership.getModuleId());
        if (isEmpty(modulePermission)) throw  new BaseException("不存在此单元模块");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("prj_id",prjModuleMembership.getPrjId());
        queryWrapper.eq("module_id",prjModuleMembership.getModuleId());
        PrjModuleMembership prjModuleMembershipValid = prjModuleMembershipMapper.selectOne(queryWrapper);
        if (!isEmpty(prjModuleMembershipValid)) throw new BaseException("已经存在此模块的管理");
        prjModuleMembershipMapper.insert(prjModuleMembership);
        return prjModuleMembership.getId();
    }

    @Override
    public Integer updatePrjModuleMembership(PrjModuleMembership prjModuleMembership) {
        prjModuleMembershipMapper.updateById(prjModuleMembership);
        return prjModuleMembership.getId();
    }

    @Override
    public Map<String, List<PrjModuleDTO>> getPrjModuleMembership_test(ModulePermission modulePermission, PageFilterPojo pf,Integer prjId) {
        PageData<ModulePermissionDTO> modulePermissionList = modulePermissionService.getModulePermissionList(modulePermission, pf);
        List<ModulePermissionDTO> dataList = modulePermissionList.getDataList();

        List<PrjModuleDTO> prjModuleDTOS= dataList.stream().map(e -> {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("prj_id",prjId);
            queryWrapper.eq("module_id", e.getId());
            PrjModuleMembership prjModuleMembership = prjModuleMembershipMapper.selectOne(queryWrapper);
            if (!isEmpty(prjModuleMembership)){
                PrjModuleDTO prjModuleDTO = new PrjModuleDTO();
                prjModuleDTO.setModuleId(e.getId());
                prjModuleDTO.setTypeName(e.getTypeName());
                prjModuleDTO.setCode(e.getCode());
                prjModuleDTO.setName(e.getName());
                prjModuleDTO.setMembershipId(prjModuleMembership.getId());
                prjModuleDTO.setIfShow(prjModuleMembership.getIfShow());
                prjModuleDTO.setIfChecked(prjModuleMembership.getIfChecked());
                return prjModuleDTO;
            }else {
                PrjModuleDTO prjModuleDTO = new PrjModuleDTO();
                prjModuleDTO.setModuleId(e.getId());
                prjModuleDTO.setTypeName(e.getTypeName());
                prjModuleDTO.setCode(e.getCode());
                prjModuleDTO.setName(e.getName());
                return prjModuleDTO;
            }
        }).filter(e->e.getMembershipId()!=null).collect(Collectors.toList());
        Map<String, List<PrjModuleDTO>> prjModuleDTOMaps = prjModuleDTOS.stream().sorted(Comparator.comparing(PrjModuleDTO::getCode)).collect(Collectors.groupingBy(PrjModuleDTO::getTypeName));
        return prjModuleDTOMaps;
    }

    @Override
    public Map<String, List<PrjModuleDTO>> getPrjModuleMembership(Integer prjId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("prj_id",prjId);
        List<PrjModuleMembership> prjModuleMemberships = prjModuleMembershipMapper.selectList(queryWrapper);
        Map<String, String> moduleTypeCodeToName = moduleTypeService.getModuleTypeCodeToName();
        List<PrjModuleDTO> prjModuleDTOs = prjModuleMemberships.stream().map(e -> {
                    PrjModuleDTO prjModuleDTO = new PrjModuleDTO();
                    prjModuleDTO.setMembershipId(e.getId());
                    prjModuleDTO.setModuleId(e.getModuleId());
                    prjModuleDTO.setIfShow(e.getIfShow());
                    prjModuleDTO.setIfChecked(e.getIfChecked());
                    ModulePermission modulePermission = modulePermissionMapper.selectById(e.getModuleId());
                    prjModuleDTO.setCode(modulePermission.getCode());
                    prjModuleDTO.setTypeName(moduleTypeCodeToName.get(modulePermission.getTypeCode()));
                    prjModuleDTO.setCode(modulePermission.getCode());
                    prjModuleDTO.setName(modulePermission.getName());
                    return prjModuleDTO;
                }
        ).collect(Collectors.toList());
        Map<String, List<PrjModuleDTO>> prjModuleDTOsMap= prjModuleDTOs.stream().sorted(Comparator.comparing(PrjModuleDTO::getCode)).collect(Collectors.groupingBy(PrjModuleDTO::getTypeName));
        return prjModuleDTOsMap;
    }

    @Override
    public Integer deletePrjModuleMembership(PrjModuleMembership prjModuleMembership) {
        prjModuleMembershipMapper.deleteById(prjModuleMembership);
        return prjModuleMembership.getId();
    }
}
