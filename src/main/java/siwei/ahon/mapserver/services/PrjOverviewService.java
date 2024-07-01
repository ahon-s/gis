package siwei.ahon.mapserver.services;

import siwei.ahon.mapserver.model.PrjOverview;

public interface PrjOverviewService {

//    Integer addPrjOverview(PrjOverview prjOverview);

    PrjOverview getPrjOverview(PrjOverview prjOverview);

    Integer updateOrAddPrjOverview(PrjOverview prjOverview);

    Integer deletePrjOverview(PrjOverview prjOverview);

}
