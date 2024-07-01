package siwei.ahon.mapserver.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import siwei.ahon.mapserver.mapper.PrjOverviewMapper;
import siwei.ahon.mapserver.model.PrjOverview;
import siwei.ahon.mapserver.services.PrjOverviewService;

import javax.annotation.Resource;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class PrjOverviewServiceImpl implements PrjOverviewService {

    @Resource
    PrjOverviewMapper prjOverviewMapper;


//    @Override
//    public Integer addPrjOverview(PrjOverview prjOverview) {
//        prjOverviewMapper.insert(prjOverview);
//        return prjOverview.getId();
//    }

    @Override
    public PrjOverview getPrjOverview(PrjOverview prjOverview) {
        if (!isEmpty(prjOverview.getId())){
            return  prjOverviewMapper.selectById(prjOverview.getId());
        }
        QueryWrapper<PrjOverview> prjOverviewQueryWrapper = new QueryWrapper<>();
        prjOverviewQueryWrapper.eq("prj_tag",prjOverview.getPrjTag());
        PrjOverview result = prjOverviewMapper.selectOne(prjOverviewQueryWrapper);
        return result;
    }

    @Override
    public Integer updateOrAddPrjOverview(PrjOverview prjOverview) {
        if (!isEmpty(prjOverview.getId())){
            prjOverviewMapper.updateById(prjOverview);
            return prjOverview.getId();
        }
        QueryWrapper<PrjOverview> prjOverviewQueryWrapper = new QueryWrapper<>();
        prjOverviewQueryWrapper.eq("prj_tag",prjOverview.getPrjTag());
        if (prjOverviewMapper.exists(prjOverviewQueryWrapper)){
            int update = prjOverviewMapper.update(prjOverview, prjOverviewQueryWrapper);
            return update;
        }else {
            prjOverviewMapper.insert(prjOverview);
            return prjOverview.getId();
        }


    }
    @Override
    public Integer deletePrjOverview(PrjOverview prjOverview) {
        if (!isEmpty(prjOverview.getId())){
            int de = prjOverviewMapper.deleteById(prjOverview);
            return de;
        }
        QueryWrapper<PrjOverview> prjOverviewQueryWrapper = new QueryWrapper<>();
        prjOverviewQueryWrapper.eq("prj_tag",prjOverview.getPrjTag());
        int delete = prjOverviewMapper.delete(prjOverviewQueryWrapper);
        return delete;
    }
}
