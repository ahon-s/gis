package siwei.ahon.mapserver.services;

import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.model.MapAddress;
import siwei.ahon.mapserver.pojo.PageFilterPojo;

import java.util.List;

public interface MapAddressService {

    PageData<MapAddress> getMapAddressList(MapAddress mapAddress, PageFilterPojo pf,Integer orderBy);

    List<MapAddress> getAllMapAddressList(MapAddress mapAddress);

    Integer addMapAddress(MapAddress mapAddress);

    Integer updateMapAddress(MapAddress mapAddress);

    Integer deleteMapAddress(MapAddress mapAddress);

    MapAddress getLastMapAddress();

    Integer insertMapAddress(List<MapAddress> mapAddresses);

    List<MapAddress> getMapAddressList(MapAddress mapAddress, Integer orderBy);
}
