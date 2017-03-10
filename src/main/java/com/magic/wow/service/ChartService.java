package com.magic.wow.service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoxf on 2017/3/9.
 */
public interface ChartService {


    List<Map<String, Object>> groupByType(int n);
}
