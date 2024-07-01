package siwei.ahon.mapserver.services;

import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.model.MapAddress;
import siwei.ahon.mapserver.model.TerrainAddress;
import siwei.ahon.mapserver.pojo.PageFilterPojo;

import java.util.List;

public interface TerrainAddressService {
    PageData<TerrainAddress> getTerrainAddressList(TerrainAddress terrainAddress, PageFilterPojo pf,Integer orderBy);

    Integer addTerrainAddress(TerrainAddress terrainAddress);

    Integer updateTerrainAddress(TerrainAddress terrainAddress);

    Integer deleteTerrainAddress(TerrainAddress terrainAddress);

    TerrainAddress getLastTerrainAddress();

    Integer insertTerrainAddress(List<TerrainAddress> terrainAddresses);

    List<TerrainAddress> getTerrainAddressList (TerrainAddress terrainAddress,Integer orderBy);

    List<TerrainAddress> getAllTerrainAddressList(TerrainAddress terrainAddress);
}
