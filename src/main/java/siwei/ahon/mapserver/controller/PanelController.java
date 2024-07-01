package siwei.ahon.mapserver.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import siwei.ahon.mapserver.expection.MyResult;
import siwei.ahon.mapserver.model.MapAddress;
import siwei.ahon.mapserver.model.TerrainAddress;
import siwei.ahon.mapserver.services.MapAddressService;
import siwei.ahon.mapserver.services.TerrainAddressService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/panelserver")
@CrossOrigin(allowedHeaders  = {"*"})
@Validated
public class PanelController {
    @Resource
    MapAddressService mapAddressService;

    @Resource
    TerrainAddressService terrainAddressService;

    @GetMapping("/getMapAddressList")
    public MyResult getMapAddress(MapAddress mapAddress,Integer orderBy){

        List<MapAddress> mapAddressList = mapAddressService.getMapAddressList(mapAddress, orderBy);

        return MyResult.success(mapAddressList);

    }

    @GetMapping("/getTerrainAddressList")
    public MyResult getTerrainAddress (TerrainAddress terrainAddress, Integer orderBy){
        List<TerrainAddress> terrainAddresses = terrainAddressService.getTerrainAddressList(terrainAddress, orderBy);
        return MyResult.success(terrainAddresses);

    }




}
