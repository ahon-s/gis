package siwei.ahon.mapserver.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import siwei.ahon.mapserver.annotation.FilterFiledHelper;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.mapper.StatisticsLayoutMapper;
import siwei.ahon.mapserver.mapper.StatisticsModuleMapper;
import siwei.ahon.mapserver.model.StatisticsLayout;
import siwei.ahon.mapserver.model.StatisticsModule;
import siwei.ahon.mapserver.pojo.PageFilterPojo;
import siwei.ahon.mapserver.services.StatisticsModuleService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StatisticsModuleServiceImpl implements StatisticsModuleService {
    @Resource
    StatisticsModuleMapper statisticsModuleMapper;
    @Resource
    StatisticsLayoutMapper statisticsLayoutMapper;
    @Resource
    FilterFiledHelper filedHelper;
    @Override
    public Integer addStatisticsModule(StatisticsModule statisticsModule) {
        statisticsModuleMapper.insert(statisticsModule);
        return statisticsModule.getId();
    }

    @Override
    public Integer addStatisticsModuleList(List<StatisticsModule> statisticsModuleList) {
        statisticsModuleList.forEach(e ->statisticsModuleMapper.insert(e));
        return statisticsModuleList.size();
    }

    @Override
    public PageData<StatisticsModule> getStatisticsModuleList(StatisticsModule statisticsModule, PageFilterPojo pf) {
        QueryWrapper<StatisticsModule> queryWrapper = new QueryWrapper<>();
        QueryWrapper smQueryWrapper = filedHelper.getQueryWrapper(queryWrapper,statisticsModule);
        Page<StatisticsModule> statisticsModulePage = new Page<StatisticsModule>(pf.getPageNum(), pf.getPageSize());
        IPage page = statisticsModuleMapper.selectPage(statisticsModulePage,smQueryWrapper);
        PageData pd = new PageData(page);
        return pd;
    }

    @Override
    public Integer updateStatisticsModule(StatisticsModule statisticsModule) {
        int update = statisticsModuleMapper.updateById(statisticsModule);
        return update;
    }

    @Override
    public Integer deleteStatisticsModule(StatisticsModule statisticsModule) {

        Integer layoutDelete = statisticsLayoutMapper.delete(new LambdaQueryWrapper<StatisticsLayout>().eq(StatisticsLayout::getModuleId, statisticsModule.getId()));
        Integer delete = statisticsModuleMapper.deleteById(statisticsModule);
        return layoutDelete+delete;
    }
}
