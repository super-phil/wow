package com.magic.wow.controller;

import com.magic.wow.util.ResultUtils;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by zhaoxf on 2017/3/6.
 */
@RestController
@RequestMapping("home")
public class HomeController {


    /**
     * SELECT
     * DATE_FORMAT(create_time, '%h:%s') AS hs,
     * COUNT(id)
     * FROM
     * USER
     * WHERE DATE_SUB(NOW(), INTERVAL 5 SECOND  ) <= create_time
     *
     * @param interval the interval
     * @return object object
     */
    @PostMapping("ajax")
    public Object chart(@RequestParam(value = "interval", required = false) int interval) {
//        Map<String, Object> data = userService.getIntervalData(interval);
        Map<String, Object> data = new HashMap<>();
        data.put("x", new DateTime().toString("HH:mm:ss"));
        data.put("y", new Random().nextInt(99));
        return ResultUtils.success(data);
    }
}
