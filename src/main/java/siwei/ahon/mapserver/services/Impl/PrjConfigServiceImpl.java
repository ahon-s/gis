package siwei.ahon.mapserver.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import siwei.ahon.mapserver.annotation.FilterFiledHelper;
import siwei.ahon.mapserver.expection.BaseException;
import siwei.ahon.mapserver.pojo.dto.PrjConfigDTO;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.mapper.PrjConfigMapper;
import siwei.ahon.mapserver.model.PrjConfig;
import siwei.ahon.mapserver.pojo.PageFilterPojo;
import siwei.ahon.mapserver.services.PrjConfigService;

import javax.annotation.Resource;
import java.util.List;

import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;


public@Service
class PrjConfigServiceImpl implements PrjConfigService {

    @Resource
    PrjConfigMapper prjConfigMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Override
    public PageData<PrjConfig> getPrjConfigList(PrjConfig prj, PageFilterPojo pf) {
        QueryWrapper<PrjConfig> prjQueryWrapper = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(prjQueryWrapper, prj);
        Page<PrjConfig> prjConfigPage = new Page(pf.getPageNum(), pf.getPageSize());
        IPage page = prjConfigMapper.selectPage(prjConfigPage,queryWrapper);
        PageData pd= new PageData(page);
        return pd;
    }
    @Override
    public List<PrjConfigDTO> getPrjList() {
        List<PrjConfig> prjConfigs = prjConfigMapper.selectList(null);
        List<PrjConfigDTO> prjList = prjConfigs.stream().map(e -> {
                    PrjConfigDTO prjConfigDTO = new PrjConfigDTO();
                    prjConfigDTO.setId(e.getId());
                    prjConfigDTO.setPrjName(e.getPrjName());
                    return prjConfigDTO;
                }
        ).collect(Collectors.toList());
        return prjList;
    }

    @Override
    public Integer addPrjConfig(PrjConfig prj) {

        prjConfigMapper.insert(prj);
        return prj.getId();
    }

    @Override
    public Integer updatePrjConfig(PrjConfig prj) {
        prjConfigMapper.updateById(prj);
        return prj.getId();
    }

    @Override
    public Integer updatePriConfigUrl(PrjConfig prjConfig) {
        if (isEmpty(prjConfig)) throw new BaseException("不能传空对象");
        UpdateWrapper<PrjConfig> prjConfigUpdateWrapper = new UpdateWrapper<>();
        prjConfigUpdateWrapper.eq("id",prjConfig.getId());
        prjConfigUpdateWrapper.set("access_address",prjConfig.getAccessAddress());
        prjConfigUpdateWrapper.set("personnel_access_address",prjConfig.getPersonnelAccessAddress());
        int update = prjConfigMapper.update(null, prjConfigUpdateWrapper);
        return update;
    }

    @Override
    public Integer addPriConfigMapInfo(PrjConfig prjConfig) {
        UpdateWrapper<PrjConfig> prjConfigUpdateWrapper = new UpdateWrapper<>();
        prjConfigUpdateWrapper.eq("id",prjConfig.getId());
        prjConfigUpdateWrapper.set(" map_coordinate",prjConfig.getMapCoordinate());
        prjConfigUpdateWrapper.set("map_Level",prjConfig.getMapLevel());

        int update = prjConfigMapper.update(null, prjConfigUpdateWrapper);
        return update;
    }

    @Override
    public Integer deletePrjConfig(PrjConfig prj) {
        prjConfigMapper.deleteById(prj);
        return prj.getId();
    }


}
