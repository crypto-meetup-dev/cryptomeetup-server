package com.ly.bt.controller.pub;

import com.ly.bt.service.BtPointGisService;
import com.ly.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 地图坐标信息 前端控制器
 * </p>
 *
 * @Author liyang
 * @Create 2018-11-16
 */
@RestController
@RequestMapping("/pub/bt/point/gis")
public class PubBtPointGisController extends BaseController {

    @Autowired
    private BtPointGisService btPointGisService;

}
