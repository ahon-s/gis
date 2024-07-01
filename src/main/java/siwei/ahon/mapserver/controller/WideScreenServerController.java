package siwei.ahon.mapserver.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import siwei.ahon.mapserver.model.WideScreenParameter;
import siwei.ahon.mapserver.pojo.dto.StatisticsModuleDTO;
import siwei.ahon.mapserver.expection.MyResult;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.model.PrjOverview;
import siwei.ahon.mapserver.model.StatisticsLayout;
import siwei.ahon.mapserver.model.StatisticsModule;
import siwei.ahon.mapserver.pojo.PageFilterPojo;
import siwei.ahon.mapserver.services.PrjOverviewService;
import siwei.ahon.mapserver.services.StatisticsLayoutService;
import siwei.ahon.mapserver.services.StatisticsModuleService;
import siwei.ahon.mapserver.services.WideScreenParameterService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/widescreenserver")
@CrossOrigin(allowedHeaders  = {"*"})
@Validated
public class WideScreenServerController {
    @Resource
    StatisticsModuleService statisticsModuleService;

    @Resource
    StatisticsLayoutService statisticsLayoutService;

    @Resource
    PrjOverviewService prjOverviewService;

    @Resource
    WideScreenParameterService wideScreenParameterService;


    /** 统计模块 **/
    /**新增统计模块*/
    @PostMapping("addStatisticsModule")
    public MyResult addStatisticsModule(@Valid StatisticsModule module){
        Integer result = statisticsModuleService.addStatisticsModule(module);
        return MyResult.success(result);
    }

    /**批量新增统计模块*/
    @PostMapping("addStatisticsModuleList")
    public MyResult addStatisticsModule(@RequestBody List<StatisticsModule> statisticsModuleList){
        Integer result = statisticsModuleService.addStatisticsModuleList(statisticsModuleList);
        return MyResult.success(result);
    }

    /**获取统计模块*/
    @GetMapping("getStatisticsModuleList")
    public MyResult getStatisticsModuleList(StatisticsModule statisticsModule, PageFilterPojo pf){
        PageData<StatisticsModule> statisticsModuleList = statisticsModuleService.getStatisticsModuleList(statisticsModule, pf);
        return MyResult.success(statisticsModuleList);
    }
    /**删除统计模块*/
    @PostMapping("deleteStatisticsModule")
    public MyResult deleteStatisticsModule(StatisticsModule statisticsModule){
        Integer result = statisticsModuleService.deleteStatisticsModule(statisticsModule);
        return MyResult.success(result);
    }
    /**修改统计模块*/
    @PostMapping("updateStatisticsModule")
    public MyResult updateStatisticsModule(@Valid StatisticsModule statisticsModule){
        Integer result = statisticsModuleService.updateStatisticsModule(statisticsModule);
        return MyResult.success(result);
    }


    /**修改或者新增统计布局展示模块*/
    @PostMapping("updateOrAddStatisticsLayout")
    public MyResult updateOrAddStatisticsLayout(@Valid StatisticsLayout statisticsLayout){
        Integer result = statisticsLayoutService.updateOrAddStatisticsLayout(statisticsLayout);
        return MyResult.success(result);
    }

    /**获取统计布局显示的模块内容*/
    @GetMapping("getStatisticsModulesDTO")
    public MyResult getStatisticsLayoutModules(StatisticsLayout statisticsLayout){
        List<StatisticsModuleDTO> statisticsModulesDTO = statisticsLayoutService.getStatisticsModulesDTO(statisticsLayout);
        return MyResult.success(statisticsModulesDTO);
    }
    @PostMapping("deleteStatisticsLayout")
    public MyResult deleteStatisticsLayout(StatisticsLayout statisticsLayout){
        Integer result = statisticsLayoutService.deleteStatisticsLayout(statisticsLayout);
        return MyResult.success(result);
    }

    /**获取项目概述*/
    @GetMapping("getPrjOverview")
    public MyResult getPrjOverview(@Valid PrjOverview prjOverview){
        PrjOverview result = prjOverviewService.getPrjOverview(prjOverview);
        return MyResult.success(result);
    }

    /**更新或者增加项目概述*/
    @PostMapping("updateOrAddPrjOverview")
    public MyResult updateOrAddPrjOverview(@Valid PrjOverview prjOverview){
        Integer result = prjOverviewService.updateOrAddPrjOverview(prjOverview);
        return MyResult.success(result);
    }
    /**删除项目概述*/
    @PostMapping("deletePrjOverview")
    public MyResult deletePrjOverview(PrjOverview prjOverview){
        Integer result = prjOverviewService.deletePrjOverview(prjOverview);
        return MyResult.success(result);
    }

    /**获取屏幕宽度*/
    @GetMapping("getWideScreenParameter")
    public MyResult getWideScreenParameter(String prjTag,String type){
        WideScreenParameter screenWidth = wideScreenParameterService.getScreenWidth(prjTag,type);
        return MyResult.success(screenWidth);
    }

    /**添加和修改屏幕宽度*/
    @PostMapping("setWideScreenParameter")
    public MyResult setWideScreenParameter(WideScreenParameter wideScreenParameter){
        String message = wideScreenParameterService.setScreenWidth(wideScreenParameter);
        return MyResult.success(message);
    }

    /**删除屏幕宽度*/
    @PostMapping("deleteWideScreenParameter")
    public MyResult deleteWideScreenParameter(WideScreenParameter wideScreenParameter){
        String message = wideScreenParameterService.deleteScreenWidth(wideScreenParameter);
        return MyResult.success(message);
    }


}
