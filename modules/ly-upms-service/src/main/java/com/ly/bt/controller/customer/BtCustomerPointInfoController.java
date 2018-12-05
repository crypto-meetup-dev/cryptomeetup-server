package com.ly.bt.controller.customer;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ly.bt.model.entity.BtPointInfo;
import com.ly.bt.service.BtPointGisService;
import com.ly.bt.service.BtPointInfoService;
import com.ly.common.util.GeoHashUtil;
import com.ly.common.util.Query;
import com.ly.common.util.R;
import com.ly.common.util.exception.ParamsErrorException;
import com.ly.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 打开点详细信息 前端控制器
 * </p>
 *
 * @Author liyang
 * @Create 2018-11-16
 */
@Api(value = "用户打卡点controller", tags = {"用户打卡点-->用户接口"})
@RestController
@RequestMapping("/bt/customer/point")
public class BtCustomerPointInfoController extends BaseController {

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
    @ApiOperation(value = "获取打卡点详细信息", notes = "ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "坐标ID", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "header")
    })
    @GetMapping("/{id}")
    public R<BtPointInfo> get(@PathVariable Integer id, @RequestHeader("userId") Integer userId) {
        BtPointInfo info = infoService.selectDetailByIdService(id);
        if (info != null && !userId.equals(info.getUserId())) {
            throw new ParamsErrorException();
        }
        return new R(info);
    }


    /**
     * 我的打卡点
     *
     * @param userId
     * @param page
     * @param status
     * @return
     */
    @ApiOperation(value = "查询我的打卡点", notes = "我的打卡点数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "header"),
            @ApiImplicitParam(name = "page", value = "页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "坐标状态", required = false, dataType = "int", paramType = "query")
    })
    @GetMapping("/list")
    public R<Page<BtPointInfo>> page(@RequestHeader("userId") Integer userId, int page, @RequestParam("status") String status) {

        Map params = new HashedMap();
        if (StringUtils.isNotEmpty(status)) {
            params.put("status", status);
        }
        params.put("userId", userId);
        params.put("page", page);

        Query query = new Query(params);
        EntityWrapper entityWrapper = new EntityWrapper<>();
        Page<BtPointInfo> page1 = infoService.selectPage(query, entityWrapper);
        return new R(page1);
    }


    @ApiOperation(value = "创建打卡点", notes = "创建打卡点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "header"),
            @ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "des", value = "描述", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "images", value = "图片信息", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "latitude", value = "坐标", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "longitude", value = "坐标", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/create")
    public R<BtPointInfo> create(
            @RequestParam(required = true) String title,
            @RequestParam(required = true) String des,
            @RequestParam(required = true) String images,
            @RequestParam(required = true) String latitude,
            @RequestParam(required = true) String longitude,
            @RequestHeader("userId") Integer userId) {

        BtPointInfo pointInfo = new BtPointInfo();
        pointInfo.setTitle(title);
        pointInfo.setDes(des);
        pointInfo.setImages(images);
        if (GeoHashUtil.isRoundOut(latitude, longitude)) {
            throw new ParamsErrorException("坐标不正确！");
        }
        pointInfo.setUserId(userId);

        return new R(infoService.createPointService(pointInfo, latitude, longitude));
    }


    @ApiOperation(value = "更新打卡点", notes = "更新打卡点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "header")
    })
    @PostMapping("/update")
    public R<Boolean> update(BtPointInfo pointInfo,
                             @RequestHeader("userId") Integer userId) {
        BtPointInfo info = infoService.selectById(pointInfo.getId());
        if (!userId.equals(info.getUserId())) {
            throw new ParamsErrorException();
        }
        pointInfo.setUserId(userId);

        return new R(infoService.updateById(pointInfo));
    }

}
