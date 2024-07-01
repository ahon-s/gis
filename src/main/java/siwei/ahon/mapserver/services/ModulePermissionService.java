package siwei.ahon.mapserver.services;

import siwei.ahon.mapserver.pojo.dto.ModulePermissionDTO;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.model.ModulePermission;
import siwei.ahon.mapserver.pojo.PageFilterPojo;

public interface ModulePermissionService {
    PageData<ModulePermissionDTO> getModulePermissionList(ModulePermission modulePermission, PageFilterPojo pf);

    Integer addModulePermission(ModulePermission modulePermission);

    Integer deleteModulePermission(ModulePermission modulePermission);

    Integer updateModulePermission(ModulePermission modulePermission);
}
