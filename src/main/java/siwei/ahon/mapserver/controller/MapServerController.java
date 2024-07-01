package siwei.ahon.mapserver.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import siwei.ahon.mapserver.pojo.dto.ModulePermissionDTO;
import siwei.ahon.mapserver.pojo.dto.PrjConfigDTO;
import siwei.ahon.mapserver.pojo.dto.PrjModuleDTO;
import siwei.ahon.mapserver.expection.MyResult;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.model.*;
import siwei.ahon.mapserver.pojo.PageFilterPojo;
import siwei.ahon.mapserver.services.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mapserver")
@CrossOrigin(allowedHeaders  = {"*"})
@Validated
public class MapServerController {

    @Resource
    MapAddressService mapAddressService;

    @Resource
    TerrainAddressService terrainAddressService;

    @Resource
    PrjConfigService prjConfigService;

    @Resource
    ModulePermissionService modulePermissionService;

    @Resource
    ModuleTypeService moduleTypeService;

    @Resource
    PrjModuleMembershipService prjModuleMembershipService;


    @Resource
    WebModuleService webModuleService;

    /**获得map地址列表**/
    @GetMapping("getMapAddressList")
    public MyResult getMapAddressList(MapAddress map, PageFilterPojo pf,Integer orderBy){
        PageData<MapAddress> mapAddressList = mapAddressService.getMapAddressList(map, pf,orderBy);
        return  MyResult.success(mapAddressList);
    }
    @GetMapping("getAllMapAddressList")
    public MyResult getAllMapAddressList(MapAddress mapAddress){
        List<MapAddress> allMapAddressList = mapAddressService.getAllMapAddressList(mapAddress);
        return MyResult.success(allMapAddressList);
    }
    /**添加map地址**/
    @PostMapping("addMapAddress")
    public MyResult addMapAddress(@Valid MapAddress map){
        Integer result = mapAddressService.addMapAddress(map);
        return MyResult.success(result);
    }
    /**修改map地址**/
    @PostMapping("updateMapAddress")
    public MyResult updateMapAddress(@Valid MapAddress map){
        Integer result = mapAddressService.updateMapAddress(map);
        return MyResult.success(result);
    }
    /**删除map地址**/
    @PostMapping("deleteMapAddress")
    public MyResult deleteMapAddress(MapAddress map){
        Integer result = mapAddressService.deleteMapAddress(map);
        return MyResult.success(result);
    }
    @GetMapping("getLastMapAddress")
    public MyResult getLastMapAddress(){
        MapAddress lastMapAddress = mapAddressService.getLastMapAddress();
        return MyResult.success(lastMapAddress);
    }
    @PostMapping("insertMapAddress")
    public MyResult insertMapAddress(@RequestBody List<MapAddress> mapAddresses){
        Integer result = mapAddressService.insertMapAddress(mapAddresses);
        return MyResult.success(result);
    }


    /**获得Terrain地址列表**/
    @GetMapping("getTerrainAddressList")
    public MyResult getTerrainAddressList(TerrainAddress terrainAddress, PageFilterPojo pf,Integer orderBy){
        System.out.println(orderBy);
        PageData<TerrainAddress> tAddressList = terrainAddressService.getTerrainAddressList(terrainAddress, pf,orderBy);
        return  MyResult.success(tAddressList);
    }
    /**添加Terrain地址**/
    @PostMapping("addTerrainAddress")
    public MyResult addTerrainAddress(@Valid TerrainAddress terrainAddress){
        Integer result = terrainAddressService.addTerrainAddress(terrainAddress);
        return MyResult.success(result);
    }
    /**修改Terrain地址**/
    @PostMapping("updateTerrainAddress")
    public MyResult updateTerrainAddress(@Valid TerrainAddress terrainAddress){
        Integer result = terrainAddressService.updateTerrainAddress(terrainAddress);
        return MyResult.success(result);
    }
    /**删除Terrain地址**/
    @PostMapping("deleteTerrainAddress")
    public MyResult deleteTerrainAddress(TerrainAddress terrainAddress){
        Integer result = terrainAddressService.deleteTerrainAddress(terrainAddress);
        return MyResult.success(result);
    }
    @PostMapping("insertTerrainAddress")
    public MyResult insertTerrainAddress(@RequestBody List<TerrainAddress> terrainAddresses){
        terrainAddressService.insertTerrainAddress(terrainAddresses);
        return MyResult.success(1);
    }
    @GetMapping("getLastTerrainAddress")
    public MyResult getLastTerrainAddress(){
        TerrainAddress terrainAddress = terrainAddressService.getLastTerrainAddress();
        return MyResult.success(terrainAddress);
    }

    //获取全部地形地址
    @GetMapping("getAllTerrainAddressList")
    public MyResult getAllTerrainAddressList(TerrainAddress terrainAddress){
        List<TerrainAddress> allTerrainAddressList = terrainAddressService.getAllTerrainAddressList(terrainAddress);
        return MyResult.success(allTerrainAddressList);
    }

    /**获得项目管理列表**/
    @GetMapping("getPrjConfigList")
    public MyResult getPrjConfigList(PrjConfig prj,PageFilterPojo pf){
        PageData<PrjConfig> priConfigList = prjConfigService.getPrjConfigList(prj, pf);
        return MyResult.success(priConfigList);
    }
    @GetMapping("getPrjList")
    public MyResult getPrjList(){
        List<PrjConfigDTO> mapPrjList = prjConfigService.getPrjList();
        return MyResult.success(mapPrjList);
    }
    /**添加项目**/
    @PostMapping("addPrjConfig")
    public MyResult addPrjConfig(@Valid PrjConfig prj){
        Integer result = prjConfigService.addPrjConfig(prj);
        return MyResult.success(result);
    }
    @PostMapping("updatePrjConfig")
    public MyResult updatePrjConfig(@Valid PrjConfig prj){
        Integer result = prjConfigService.updatePrjConfig(prj);
        return MyResult.success(result);
    }
    @PostMapping("updatePriConfigUrl")
    public MyResult updatePriConfigUrl(PrjConfig prj){
        Integer result = prjConfigService.updatePriConfigUrl(prj);
        return MyResult.success(result);
    }

    @PostMapping("addPriConfigMapInfo")
    public MyResult addPriConfigMapInfo(@RequestBody PrjConfig prj){
        Integer result = prjConfigService.addPriConfigMapInfo(prj);
        return MyResult.success(result);
    }



    @PostMapping("deletePrjConfig")
    public MyResult deletePrjConfig(PrjConfig prj){
        Integer result = prjConfigService.deletePrjConfig(prj);
        return MyResult.success(result);
    }



    /**获取模块单元列表**/
    @GetMapping("getModulePermissionList")
    public  MyResult getModulePermissionList(ModulePermission modulePermission, PageFilterPojo pf){
        PageData<ModulePermissionDTO> module = modulePermissionService.getModulePermissionList(modulePermission,pf);
        return MyResult.success(module);
    }
    /**添加模块单元**/
    @PostMapping("addModulePermission")
    public  MyResult addModulePermission(@Valid ModulePermission modulePermission){
        Integer result = modulePermissionService.addModulePermission(modulePermission);
        return MyResult.success(result);
    }
    /**修改模块单元**/
    @PostMapping("updateModulePermission")
    public  MyResult updateModulePermission(@Valid ModulePermission modulePermission){
        Integer result = modulePermissionService.updateModulePermission(modulePermission);
        return MyResult.success(result);
    }
    /**删除模块单元**/
    @PostMapping("deleteModulePermission")
    public MyResult deleteModulePermission(ModulePermission modulePermission){
        Integer result = modulePermissionService.deleteModulePermission(modulePermission);
        return MyResult.success(result);
    }



    /**增删查模块单元类型**/
    @GetMapping("getModuleTypeList")
    public MyResult getModuleTypeList(ModuleType moduleType,PageFilterPojo pf){
        PageData<ModuleType> moduleTypeList = moduleTypeService.getModuleTypeList(moduleType, pf);
        return MyResult.success(moduleTypeList);
    }
    @PostMapping("addModuleType")
    public MyResult addModuleType(@Valid ModuleType moduleType){
        Integer result = moduleTypeService.addModuleType(moduleType);
        return MyResult.success(result);
    }
    @PostMapping("deleteModuleType")
    public MyResult deleteModuleType(ModuleType moduleType){
        Integer result = moduleTypeService.deleteModuleType(moduleType);
        return MyResult.success(result);
    }



    /**图层控制**/
    @GetMapping("getPrjModuleMembership")
    public MyResult getPrjModuleMembership(Integer prjId){
        Map<String, List<PrjModuleDTO>> prjModuleMembership = prjModuleMembershipService.getPrjModuleMembership(prjId);
        return MyResult.success(prjModuleMembership);
    }
    @PostMapping("updatePrjModuleMembership")
    public MyResult updateModuleMembership(@RequestBody List<PrjModuleMembership> prjModuleMemberships){
        prjModuleMemberships.stream().forEach(e->{
            prjModuleMembershipService.updatePrjModuleMembership(e);
        });
        return MyResult.success("保存成功");
    }
    @PostMapping("addPrjModuleMembership")
    public MyResult addPrjModuleMembership(@Valid PrjModuleMembership prjModuleMembership){
        Integer result = prjModuleMembershipService.addPrjModuleMembership(prjModuleMembership);
        return MyResult.success(result);
    }
    @PostMapping("deleteModuleMembership")
    public MyResult deleteModuleMembership(PrjModuleMembership prjModuleMembership){
        Integer result = prjModuleMembershipService.deletePrjModuleMembership(prjModuleMembership);
        return MyResult.success(result);
    }


    /**获取网页对应模块**/
    @GetMapping("getWebModuleList")
    public MyResult getWebModuleList(){
        List<WebModule> webModuleList = webModuleService.getWebModuleList();
        return MyResult.success(webModuleList);
    }

    /**获取统计布局显示的模块内容*/
}
