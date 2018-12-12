package com.ly.bt.controller.pub;

import com.baomidou.mybatisplus.plugins.Page;
import com.ly.bt.model.entity.BtPointGis;
import com.ly.bt.model.entity.BtPointInfo;
import com.ly.bt.service.BtPointGisService;
import com.ly.bt.service.BtPointInfoService;
import com.ly.common.util.GeoHashUtil;
import com.ly.common.util.R;
import com.ly.common.util.exception.ParamsErrorException;
import com.ly.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 打开点详细信息 前端控制器
 * </p>
 *
 * @Author liyang
 * @Create 2018-11-16
 */
@Api(value = "打卡点查询controller", tags = {"打卡查询操作-->公开接口"})
@RestController
@RequestMapping("/pub/bt/point")
public class PubBtPointInfoController extends BaseController {

    @Autowired
    private BtPointInfoService infoService;

    @Autowired
    private BtPointGisService gisService;


    /**
     * 通过ID查询
     *
     * @param id ID
     * @return BtPointInfo
     */
    @ApiOperation(value = "获取坐标详细信息", notes = "ID查询")
    @ApiImplicitParam(name = "id", value = "坐标ID", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{id}")
    public R<BtPointInfo> get(@PathVariable Integer id) {
        return new R<>(infoService.selectDetailByIdService(id));
    }


    /**
     * 查询附近的打卡点
     *
     * @param latitude
     * @param longitude
     * @param distance
     * @return
     */
    @ApiOperation(value = "查询附近的打卡点", notes = "坐标查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "latitude", value = "坐标", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "longitude", value = "坐标", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "一页数据条数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "distance", value = "半径距离 单位(m)", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/distance")
    public R<Page<BtPointGis>> page(String latitude, String longitude, Integer distance,
                                    @RequestParam(required = false, defaultValue = "1") Integer page,
                                    @RequestParam(required = false, defaultValue = "20") Integer limit) {
        if (GeoHashUtil.isRoundOut(latitude, longitude)) {
            throw new ParamsErrorException("坐标不正确！");
        }
        return new R(gisService.selectDistanceService(latitude, longitude, distance, page, limit));
    }


    @GetMapping("/distance1")
    public R<Page<BtPointInfo>> page1(String latitude, String longitude, Integer distance,
                                    @RequestParam(required = false, defaultValue = "1") Integer page,
                                    @RequestParam(required = false, defaultValue = "20") Integer limit) {
        if (GeoHashUtil.isRoundOut(latitude, longitude)) {
            throw new ParamsErrorException("坐标不正确！");
        }
        return new R(infoService.selectDistanceService(latitude, longitude, distance, page, limit));
    }

}
