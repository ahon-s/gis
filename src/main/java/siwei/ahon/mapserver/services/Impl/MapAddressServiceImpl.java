package siwei.ahon.mapserver.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import siwei.ahon.mapserver.annotation.FilterFiledHelper;
import siwei.ahon.mapserver.expection.BaseException;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.mapper.MapAddressMapper;
import siwei.ahon.mapserver.mapper.PrjConfigMapper;
import siwei.ahon.mapserver.model.MapAddress;
import siwei.ahon.mapserver.model.PrjConfig;
import siwei.ahon.mapserver.model.UserRight;
import siwei.ahon.mapserver.pojo.PageFilterPojo;
import siwei.ahon.mapserver.services.MapAddressService;
import siwei.ahon.mapserver.util.RedisUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;
import static siwei.ahon.mapserver.util.ObjectIsNullUtil.checkFieldAllNull;


@Service
public class MapAddressServiceImpl implements MapAddressService {
    @Resource
    MapAddressMapper mapAddressMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Resource
    PrjConfigMapper prjConfigMapper;

    @Resource
    RedisUtils redisUtils;

    @Resource
    private HttpServletRequest request;

    @Override
    public PageData<MapAddress> getMapAddressList(MapAddress mapAddress, PageFilterPojo pf,Integer orderBy) {
        MapAddress mAddress = turnNameToCode(mapAddress);
        QueryWrapper<MapAddress> maQueryWrapper = new QueryWrapper<>(mAddress);
        QueryWrapper<MapAddress> queryWrapper = filedHelper.getQueryWrapper(maQueryWrapper,mAddress);

        timeFilter(queryWrapper,pf);
        String token = request.getHeader("token");
        UserRight userRight = (UserRight) redisUtils.get(token);
        if (isEmpty(userRight.getGroups())) throw new BaseException("没有任何权限，请联系管理员");
        if (!userRight.getGroups().equals("all")){
            String[] splits = userRight.getGroups().split(",");
            List<PrjConfig> prjConfigs = prjConfigMapper.selectList(null);
            Map<Integer, String> prjCodeMap = prjConfigs.stream().collect(Collectors.toMap(PrjConfig::getId, PrjConfig::getPrjCode));

            for (int i = 0; i < splits.length; i++) {
                queryWrapper.eq("prj_code",prjCodeMap.get(Integer.valueOf(splits[i]))).or();
            }
        }

        if (isEmpty(orderBy) || orderBy == 1){
            queryWrapper.orderByAsc("m_date");
        }else {
            queryWrapper.orderByDesc("m_date");
        }
        Page<MapAddress> mapAddressPage = new Page<MapAddress>(pf.getPageNum(), pf.getPageSize());
        IPage page = mapAddressMapper.selectPage(mapAddressPage,queryWrapper);
        PageData pd = new PageData(page);
        return pd;

    }

    @Override
    public List<MapAddress> getAllMapAddressList(MapAddress mapAddress) {

        QueryWrapper<MapAddress> maQueryWrapper = new QueryWrapper<>();
        QueryWrapper<MapAddress> queryWrapper = filedHelper.getQueryWrapper(maQueryWrapper,mapAddress);
        if (!isEmpty(mapAddress.getKey())){
            queryWrapper.eq("`key`",mapAddress.getKey());
        }
        String token = request.getHeader("token");
        UserRight userRight = (UserRight) redisUtils.get(token);
        if (isEmpty(userRight.getGroups())) throw new BaseException("没有任何权限，请联系管理员");
        if (!userRight.getGroups().equals("all")){
            String[] splits = userRight.getGroups().split(",");
            List<PrjConfig> prjConfigs = prjConfigMapper.selectList(null);
            Map<Integer, String> prjCodeMap = prjConfigs.stream().collect(Collectors.toMap(PrjConfig::getId, PrjConfig::getPrjCode));

            for (int i = 0; i < splits.length; i++) {
                queryWrapper.eq("prj_code",prjCodeMap.get(Integer.valueOf(splits[i]))).or();
            }
        }

        queryWrapper.orderByDesc("m_date");

        List<MapAddress> mapAddresses = mapAddressMapper.selectList(queryWrapper);
        return mapAddresses;
    }

    @Override
    public Integer addMapAddress(MapAddress mapAddress) {
        turnNameToCode(mapAddress);
        mapAddressMapper.insert(mapAddress);
        return mapAddress.getId();

    }

    @Override
    public Integer updateMapAddress(MapAddress mapAddress) {
        if (isEmpty(mapAddress.getId())){
            throw new BaseException("id不能为空");
        }
        turnNameToCode(mapAddress);
        mapAddressMapper.updateById(mapAddress);
        return mapAddress.getId();
    }

    @Override
    public Integer deleteMapAddress(MapAddress mapAddress) {
        mapAddressMapper.deleteById(mapAddress.getId());
        return mapAddress.getId();
    }

    @Override
    public MapAddress getLastMapAddress() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("m_date");
        MapAddress mapAddress = (MapAddress) mapAddressMapper.selectList(queryWrapper).get(0);
        return mapAddress;
    }

    @Override
    public Integer insertMapAddress(List<MapAddress> mapAddresses) {
        for (MapAddress mapAddress : mapAddresses){
            mapAddressMapper.insert(mapAddress);
        }
        return 1;
    }

    @Override
    public List<MapAddress> getMapAddressList(MapAddress mapAddress, Integer orderBy) {
        QueryWrapper<MapAddress> maQueryWrapper = new QueryWrapper<>(mapAddress);
        QueryWrapper<MapAddress> queryWrapper = filedHelper.getQueryWrapper(maQueryWrapper,mapAddress);
        if (!isEmpty(orderBy)) {
            switch (orderBy){
//            case 1: queryWrapper.orderByAsc("id"); break;
                case 2: queryWrapper.orderByDesc("id"); break;
                case 3: queryWrapper.orderByAsc("prj_code"); break;
                case 4: queryWrapper.orderByDesc("prj_code"); break;
                case 5: queryWrapper.orderByAsc("`key`"); break;
                case 6: queryWrapper.orderByDesc("`key`"); break;
                case 7: queryWrapper.orderByAsc("m_date"); break;
                case 8: queryWrapper.orderByDesc("m_date"); break;
                case 9: queryWrapper.orderByAsc("map_url"); break;
                case 10: queryWrapper.orderByDesc("map_url"); break;
                default:
                    queryWrapper.orderByAsc("id");
                    break;
            }
        }

        List<MapAddress> mapAddresses = mapAddressMapper.selectList(queryWrapper);
        return mapAddresses;
    }

    private  void timeFilter(QueryWrapper qw, PageFilterPojo pf){
        qw.gt("m_date",pf.getsTime());
        qw.lt("m_date",pf.geteTime());
    }
    //将name转成code,当传递的mapAddress为空时,重写建立mapAddress

    @SneakyThrows
    public MapAddress turnNameToCode(MapAddress mapAddress){

        if (checkFieldAllNull(mapAddress)){
            mapAddress = new MapAddress();
        } else if (!isEmpty(mapAddress.getPrjCode())){
            QueryWrapper<PrjConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("prj_name",mapAddress.getPrjCode());
            PrjConfig prjConfig = prjConfigMapper.selectOne(queryWrapper);
            if (isEmpty(prjConfig)){
                throw  new BaseException("没有此prjName");
            }
            mapAddress.setPrjCode(prjConfig.getPrjCode());
        }
        return mapAddress;
    }
}
