package com.magic.wow.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.wow.service.ChartService;
import com.magic.wow.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoxf on 2017/3/9.
 */
@Slf4j
@RestController
@RequestMapping("chart")
public class ChartController {
    @Resource
    private ChartService chartService;

    @GetMapping(value = {"", "index"})
    public ModelAndView list() {

        return new ModelAndView("dkp-chart");
    }

    @PostMapping("dkp")
    public Object dkpSort(@RequestParam(defaultValue = "3", required = false) String n) {
        List<Map<String, Object>> maps = chartService.groupByType(Integer.valueOf(n));//前三
        /**
         * {
         "v": 144,
         "type": "猎人",
         "username": "Baitoufa"
         }
         {
         name: "Fruit",
         categories: ["Apple", "Banana", "Orange"]
         }
         */
        Map<String, List<String>> users = Maps.newLinkedHashMap();
        List<Integer> vs = Lists.newArrayList();
        for (Map<String, Object> map : maps) {
            List<String> user = users.get((String) map.get("type"));
            if (user == null) {
                users.put((String) map.get("type"), Lists.newArrayList());
                users.get(map.get("type")).add((String) map.get("username"));
            } else {
                users.get(map.get("type")).add((String) map.get("username"));
            }
            vs.add(Integer.valueOf(map.get("v").toString()));
        }
        List<Map<String, Object>> fmt = Lists.newArrayList();
        for (Map.Entry<String, List<String>> entry : users.entrySet()) {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("name", entry.getKey());
            map.put("categories", entry.getValue());
            fmt.add(map);
        }
        log.info(JSON.toJSONString(vs));
        log.info(JSON.toJSONString(fmt));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("categories", fmt);
        jsonObject.put("series", vs);
        return ResultUtils.success(jsonObject);
    }

}
