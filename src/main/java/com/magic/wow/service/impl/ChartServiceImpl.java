package com.magic.wow.service.impl;

import com.magic.wow.mapper.ChartMapper;
import com.magic.wow.mapper.DkpMapper;
import com.magic.wow.mapper.UserMapper;
import com.magic.wow.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoxf on 2017/3/9.
 */
@Service
public class ChartServiceImpl implements ChartService {

//    @Autowired
//    private DkpMapper dkpMapper;
    @Autowired
    private ChartMapper chartMapper;

    @Override
    public List<Map<String, Object>> groupByType(int n) {
        return chartMapper.groupByType(n);
    }

}
