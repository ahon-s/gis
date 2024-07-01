package siwei.ahon.mapserver.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import siwei.ahon.mapserver.expection.BaseException;
import siwei.ahon.mapserver.mapper.WideScreenParameterMapper;
import siwei.ahon.mapserver.model.WideScreenParameter;
import siwei.ahon.mapserver.services.WideScreenParameterService;

import javax.annotation.Resource;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class WideScreenParameterImpl implements WideScreenParameterService {

    @Resource
    WideScreenParameterMapper wideScreenParameterMapper;

    @Override
    public WideScreenParameter getScreenWidth(String prjTag,String type) {
        if (isEmpty(type)) type = "0";
        QueryWrapper<WideScreenParameter> wideScreenParameterQueryWrapper = new QueryWrapper<>();
        wideScreenParameterQueryWrapper.eq("prj_tag",prjTag).eq("type",type);
        WideScreenParameter wideScreenParameter = wideScreenParameterMapper.selectOne(wideScreenParameterQueryWrapper);
        return wideScreenParameter;
    }

    @Override
    public String setScreenWidth(WideScreenParameter wideScreenParameter) {
        if (isEmpty(wideScreenParameter.getType())) wideScreenParameter.setType("0");
        QueryWrapper<WideScreenParameter> wideScreenParameterQueryWrapper = new QueryWrapper<>();
        wideScreenParameterQueryWrapper.eq("prj_tag",wideScreenParameter.getPrjTag()).eq("type",wideScreenParameter.getType());
        WideScreenParameter wideScreenParameterT = wideScreenParameterMapper.selectOne(wideScreenParameterQueryWrapper);
        if (isEmpty(wideScreenParameterT)){
            switch (wideScreenParameter.getType()) {
                case "1":
                    if (isEmpty(wideScreenParameter.getScreenWidth()))
                    wideScreenParameter.setScreenWidth("0");
            }
            int insert = wideScreenParameterMapper.insert(wideScreenParameter);
            return "添加成功id: " + wideScreenParameter.getId();
        }else {
            int update = wideScreenParameterMapper.update(wideScreenParameter, wideScreenParameterQueryWrapper);
            return "修改成功id: " + wideScreenParameterT.getId();
        }
    }

    @Override
    public String deleteScreenWidth(WideScreenParameter wideScreenParameter) {
        if (isEmpty(wideScreenParameter.getType())) wideScreenParameter.setType("0");
        QueryWrapper<WideScreenParameter> wideScreenParameterQueryWrapper = new QueryWrapper<>();
        wideScreenParameterQueryWrapper.eq("prj_tag",wideScreenParameter.getPrjTag()).eq("type",wideScreenParameter.getType());
        int delete = wideScreenParameterMapper.delete(wideScreenParameterQueryWrapper);
        if (isEmpty(delete)) throw new BaseException("不存在此记录");
        return "删除成功: " + delete;
    }
}
