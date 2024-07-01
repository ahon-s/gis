package siwei.ahon.mapserver.services;

import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.model.ModuleType;
import siwei.ahon.mapserver.pojo.PageFilterPojo;

import java.util.Map;

public interface ModuleTypeService {
    Map<String , String> getModuleTypeCodeToName();

    Map<String , String> getModuleTypeNameToCode();

    Integer addModuleType(ModuleType moduleType);


    Integer deleteModuleType(ModuleType moduleType);

    PageData<ModuleType> getModuleTypeList(ModuleType moduleType, PageFilterPojo pf);

}
