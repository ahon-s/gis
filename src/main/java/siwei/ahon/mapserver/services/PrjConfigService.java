package siwei.ahon.mapserver.services;

import siwei.ahon.mapserver.pojo.dto.PrjConfigDTO;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.model.PrjConfig;
import siwei.ahon.mapserver.pojo.PageFilterPojo;

import java.util.List;

public interface PrjConfigService {
    PageData<PrjConfig> getPrjConfigList(PrjConfig prj,PageFilterPojo pf);

    List<PrjConfigDTO> getPrjList();

    Integer addPrjConfig(PrjConfig prj);

    Integer updatePrjConfig(PrjConfig prj);

    Integer updatePriConfigUrl(PrjConfig prjConfig);

    Integer addPriConfigMapInfo(PrjConfig prjConfig);

    Integer deletePrjConfig(PrjConfig prj);


}
