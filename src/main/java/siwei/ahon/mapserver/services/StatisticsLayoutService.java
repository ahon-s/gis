package siwei.ahon.mapserver.services;

import siwei.ahon.mapserver.pojo.dto.StatisticsModuleDTO;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.model.StatisticsLayout;
import siwei.ahon.mapserver.pojo.PageFilterPojo;

import java.util.List;

public interface StatisticsLayoutService {

    Integer updateOrAddStatisticsLayout(StatisticsLayout statisticsLayout);

    List<StatisticsModuleDTO> getStatisticsModulesDTO(StatisticsLayout statisticsLayout);

    Integer addStatisticsLayout(StatisticsLayout statisticsLayout);

    Integer deleteStatisticsLayout(StatisticsLayout statisticsLayout);

    PageData<StatisticsLayout> getStatisticsLayoutList(StatisticsLayout statisticsLayout, PageFilterPojo pf);
}
