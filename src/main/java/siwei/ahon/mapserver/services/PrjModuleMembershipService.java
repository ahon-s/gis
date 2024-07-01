package siwei.ahon.mapserver.services;

import siwei.ahon.mapserver.pojo.dto.PrjModuleDTO;
import siwei.ahon.mapserver.model.ModulePermission;
import siwei.ahon.mapserver.model.PrjModuleMembership;
import siwei.ahon.mapserver.pojo.PageFilterPojo;

import java.util.List;
import java.util.Map;

public interface PrjModuleMembershipService {
    Integer addPrjModuleMembership(PrjModuleMembership prjModuleMembership);

    Integer updatePrjModuleMembership(PrjModuleMembership prjModuleMembership);

    Map<String, List<PrjModuleDTO>> getPrjModuleMembership_test(ModulePermission modulePermission, PageFilterPojo pf,Integer prjId);

    Map<String, List<PrjModuleDTO>> getPrjModuleMembership(Integer prjId);

    Integer deletePrjModuleMembership (PrjModuleMembership prjModuleMembership);
}
