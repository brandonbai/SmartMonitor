package com.github.brandonbai.smartmonitor.controller;

import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * dashboard controller
 * @author brandonbai
 * @since 2018/10/24
 */
@RestController
@RequestMapping("/dashboard")
@Api(tags = "dashboard")
public class DashboardController {

    @Resource
    private RedisService redisService;

    @PostMapping("/info/statistic")
    @ApiOperation(value="信息统计", response = Response.class)
    public Response getStatisticInfo() {

        Map<String, String> map = redisService.getDashboardStatisticInfo();

        return Response.ok(map);
    }

    @PostMapping("/month/statistic")
    @ApiOperation(value="月数据量统计", response = Response.class)
    public Response getStatisticMonthInfo() {

        Map<String, Object> map = redisService.getMonthDataStatistic();

        return Response.ok(map);
    }
}
