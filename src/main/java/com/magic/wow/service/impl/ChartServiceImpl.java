package com.magic.wow.service.impl;

import com.magic.wow.mapper.ChartMapper;
import com.magic.wow.service.ChartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoxf on 2017/3/9.
 */
@Service
public class ChartServiceImpl implements ChartService {

    //    @Resource
//    private DkpMapper dkpMapper;
    @Resource
    private ChartMapper chartMapper;

    @Override
    public List<Map<String, Object>> groupByType(int n) {
        return chartMapper.groupByType(n);
    }

}
