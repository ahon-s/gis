package siwei.ahon.mapserver.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import siwei.ahon.mapserver.annotation.FilterFiledHelper;
import siwei.ahon.mapserver.expection.BaseException;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.mapper.PrjConfigMapper;
import siwei.ahon.mapserver.mapper.TerrainAddressMapper;
import siwei.ahon.mapserver.model.MapAddress;
import siwei.ahon.mapserver.model.PrjConfig;
import siwei.ahon.mapserver.model.TerrainAddress;
import siwei.ahon.mapserver.model.UserRight;
import siwei.ahon.mapserver.pojo.PageFilterPojo;
import siwei.ahon.mapserver.services.TerrainAddressService;
import siwei.ahon.mapserver.util.RedisUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;
import static siwei.ahon.mapserver.util.ObjectIsNullUtil.checkFieldAllNull;

@Service
public class TerrainAddressServiceImpl implements TerrainAddressService {
    @Resource
    TerrainAddressMapper terrainAddressMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Resource
    PrjConfigMapper prjConfigMapper;

    @Resource
    RedisUtils redisUtils;

    @Resource
    private HttpServletRequest request;

    @Override
    public PageData<TerrainAddress> getTerrainAddressList(TerrainAddress terrainAddress, PageFilterPojo pf,Integer orderBy) {
        TerrainAddress terAddress = turnNameToCode(terrainAddress);
        QueryWrapper<TerrainAddress> tQueryWrapper = new QueryWrapper<>(terAddress);
        QueryWrapper<TerrainAddress> queryWrapper = filedHelper.getQueryWrapper(tQueryWrapper, terAddress);
        timeFilter(queryWrapper, pf);

        String token = request.getHeader("token");
        UserRight userRight = (UserRight) redisUtils.get(token);
        if (StringUtils.isEmpty(userRight.getGroups())) throw new BaseException("没有任何权限，请联系管理员");
        if (!userRight.getGroups().equals("all")){
            String[] splits = userRight.getGroups().split(",");
            List<PrjConfig> prjConfigs = prjConfigMapper.selectList(null);
            Map<Integer, String> prjCodeMap = prjConfigs.stream().collect(Collectors.toMap(PrjConfig::getId, PrjConfig::getPrjCode));
            for (int i = 0; i < splits.length; i++) {
                queryWrapper.eq("prj_code",prjCodeMap.get(Integer.valueOf(splits[i]))).or();
            }
        }

        if (StringUtils.isEmpty(orderBy) || orderBy == 1){
            queryWrapper.orderByAsc("m_date");
        }else {
            queryWrapper.orderByDesc("m_date");
        }
        Page<TerrainAddress> tAddressPage = new Page<TerrainAddress>(pf.getPageNum(), pf.getPageSize());
        IPage page =terrainAddressMapper.selectPage(tAddressPage, queryWrapper);
        PageData pd = new PageData(page);
        return pd;
    }

    @Override
    public Integer addTerrainAddress(TerrainAddress terrainAddress) {
        turnNameToCode(terrainAddress);
        terrainAddressMapper.insert(terrainAddress);
        return terrainAddress.getId();

    }

    @Override
    public Integer updateTerrainAddress(TerrainAddress terrainAddress) {
        turnNameToCode(terrainAddress);
        terrainAddressMapper.updateById(terrainAddress);
        return terrainAddress.getId();
    }

    @Override
    public Integer deleteTerrainAddress(TerrainAddress terrainAddress) {
       terrainAddressMapper.deleteById(terrainAddress);
       return terrainAddress.getId();
    }

    @Override
    public TerrainAddress getLastTerrainAddress() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("m_date");
        TerrainAddress terrainAddress = (TerrainAddress) (TerrainAddress) terrainAddressMapper.selectList(queryWrapper).get(0);
        return  terrainAddress;
    }

    @Override
    public Integer insertTerrainAddress(List<TerrainAddress> terrainAddresses) {
        for (TerrainAddress terrainAddress: terrainAddresses
             ) {
            terrainAddressMapper.insert(terrainAddress);
        }
        return 1;
    }

    @Override
    public List<TerrainAddress> getTerrainAddressList(TerrainAddress terrainAddress, Integer orderBy) {


        QueryWrapper<TerrainAddress> tQueryWrapper = new QueryWrapper<>(terrainAddress);
        QueryWrapper<TerrainAddress> queryWrapper = filedHelper.getQueryWrapper(tQueryWrapper, terrainAddress);

        if (!StringUtils.isEmpty(orderBy)) {
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
        List<TerrainAddress> terrainAddresses = terrainAddressMapper.selectList(queryWrapper);
        return terrainAddresses;
    }

    @Override
    public List<TerrainAddress> getAllTerrainAddressList(TerrainAddress terrainAddress) {
        QueryWrapper<TerrainAddress> teQueryWrapper = new QueryWrapper<>();
        QueryWrapper<TerrainAddress> queryWrapper = filedHelper.getQueryWrapper(teQueryWrapper,terrainAddress);
        if (!StringUtils.isEmpty(terrainAddress.getKey())){
            queryWrapper.eq("`key`",terrainAddress.getKey());
        }
        String token = request.getHeader("token");
        UserRight userRight = (UserRight) redisUtils.get(token);
        if (StringUtils.isEmpty(userRight.getGroups())) throw new BaseException("没有任何权限，请联系管理员");
        if (!userRight.getGroups().equals("all")){
            String[] splits = userRight.getGroups().split(",");
            List<PrjConfig> prjConfigs = prjConfigMapper.selectList(null);
            Map<Integer, String> prjCodeMap = prjConfigs.stream().collect(Collectors.toMap(PrjConfig::getId, PrjConfig::getPrjCode));

            for (int i = 0; i < splits.length; i++) {
                queryWrapper.eq("prj_code",prjCodeMap.get(Integer.valueOf(splits[i]))).or();
            }
        }

        queryWrapper.orderByDesc("m_date");

        List<TerrainAddress> terAddresses = terrainAddressMapper.selectList(queryWrapper);
        return terAddresses;
    }

    private void timeFilter(QueryWrapper qw, PageFilterPojo pf) {
        qw.gt("m_date", pf.getsTime());
        qw.lt("m_date", pf.geteTime());
    }

    //将name转成code
    @SneakyThrows
    public TerrainAddress turnNameToCode(TerrainAddress terrainAddress){
        if (checkFieldAllNull(terrainAddress)){
            terrainAddress = new TerrainAddress();
        } else if (!StringUtils.isEmpty(terrainAddress.getPrjCode())){
            QueryWrapper<PrjConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("prj_name",terrainAddress.getPrjCode());
            PrjConfig prjConfig = prjConfigMapper.selectOne(queryWrapper);
            if (StringUtils.isEmpty(prjConfig)){
                throw  new BaseException("没有此prjName");
            }
            terrainAddress.setPrjCode(prjConfig.getPrjCode());
        }
        return terrainAddress;
    }


}
