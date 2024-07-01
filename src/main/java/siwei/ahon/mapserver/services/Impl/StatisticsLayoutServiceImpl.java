package siwei.ahon.mapserver.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import siwei.ahon.mapserver.annotation.FilterFiledHelper;
import siwei.ahon.mapserver.pojo.dto.StatisticsModuleDTO;
import siwei.ahon.mapserver.expection.BaseException;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.mapper.StatisticsLayoutMapper;
import siwei.ahon.mapserver.mapper.StatisticsModuleMapper;
import siwei.ahon.mapserver.model.StatisticsLayout;
import siwei.ahon.mapserver.model.StatisticsModule;
import siwei.ahon.mapserver.pojo.PageFilterPojo;
import siwei.ahon.mapserver.services.StatisticsLayoutService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class StatisticsLayoutServiceImpl implements StatisticsLayoutService {

    @Resource
    StatisticsLayoutMapper statisticsLayoutMapper;

    @Resource
    StatisticsModuleMapper statisticsModuleMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Override
    public Integer updateOrAddStatisticsLayout(StatisticsLayout statisticsLayout) {
        if (!isEmpty(statisticsLayout.getId())){
            int result = statisticsLayoutMapper.updateById(statisticsLayout);
            return result;
        }
        QueryWrapper<StatisticsLayout> statisticsLayoutQueryWrapper = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(statisticsLayoutQueryWrapper, statisticsLayout);

        QueryWrapper<StatisticsModule> statisticsModuleQueryWrapper = new QueryWrapper<>();
        statisticsModuleQueryWrapper.eq("id",statisticsLayout.getModuleId());

        if (!statisticsModuleMapper.exists(statisticsModuleQueryWrapper)) throw new BaseException("不存在改统计模块，请新添加");

        if (statisticsLayoutMapper.exists(queryWrapper)){
            int update = statisticsLayoutMapper.update(statisticsLayout, queryWrapper);

            return update;

        }else {
            statisticsLayoutMapper.insert(statisticsLayout);
            return statisticsLayout.getId();
        }
    }

    @Override
    public List<StatisticsModuleDTO> getStatisticsModulesDTO(StatisticsLayout statisticsLayout) {
        QueryWrapper<StatisticsLayout> statisticsLayoutQueryWrapper = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(statisticsLayoutQueryWrapper, statisticsLayout);
        List<StatisticsLayout> layouts = statisticsLayoutMapper.selectList(queryWrapper);
        List<StatisticsModuleDTO> moduleDTOS = layouts.stream().map(e -> {
            StatisticsModule statisticsModule = statisticsModuleMapper.selectById(e.getModuleId());
            StatisticsModuleDTO statisticsModuleDTO = new StatisticsModuleDTO();
            statisticsModuleDTO.setLayoutId(e.getId());
            statisticsModuleDTO.setModuleId(e.getModuleId());
            statisticsModuleDTO.setStatisticsCode(e.getStatisticsCode());
            statisticsModuleDTO.setModuleName(statisticsModule.getName());
            statisticsModuleDTO.setComponentName(statisticsModule.getComponentName());
            return statisticsModuleDTO;
        }).collect(Collectors.toList());

        return moduleDTOS;
    }

    @Override
    public Integer addStatisticsLayout(StatisticsLayout statisticsLayout) {
        statisticsLayoutMapper.insert(statisticsLayout);
        return statisticsLayout.getId();
    }

    @Override
    public Integer deleteStatisticsLayout(StatisticsLayout statisticsLayout) {

        if (!isEmpty(statisticsLayout.getId())){
            int result = statisticsLayoutMapper.deleteById(statisticsLayout);
            return result;
        }
        QueryWrapper<StatisticsLayout> statisticsLayoutQueryWrapper = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(statisticsLayoutQueryWrapper, statisticsLayout);

        int delete = statisticsLayoutMapper.delete(queryWrapper);
        return delete;
    }

    @Override
    public PageData<StatisticsLayout> getStatisticsLayoutList(StatisticsLayout statisticsLayout, PageFilterPojo pf) {
        QueryWrapper<StatisticsLayout> statisticsLayoutQueryWrapper = new QueryWrapper<>();
        QueryWrapper filedHelperQueryWrapper = filedHelper.getQueryWrapper(statisticsLayoutQueryWrapper, pf);
        Page<StatisticsLayout> statisticsLayoutPage = new Page<StatisticsLayout>(pf.getPageNum(), pf.getPageSize());
        IPage page = statisticsLayoutMapper.selectPage(statisticsLayoutPage,filedHelperQueryWrapper);
        PageData pd = new PageData(page);
        return pd;
    }
}
