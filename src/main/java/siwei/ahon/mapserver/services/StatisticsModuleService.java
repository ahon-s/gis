package siwei.ahon.mapserver.services;

import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.model.StatisticsModule;
import siwei.ahon.mapserver.pojo.PageFilterPojo;

import java.util.List;

public interface StatisticsModuleService {
    Integer addStatisticsModule(StatisticsModule statisticsModule);

    Integer addStatisticsModuleList(List<StatisticsModule> statisticsModuleList);

    PageData<StatisticsModule> getStatisticsModuleList(StatisticsModule statisticsModule, PageFilterPojo pf);

    Integer updateStatisticsModule(StatisticsModule statisticsModule);

    Integer deleteStatisticsModule(StatisticsModule statisticsModule);
}
