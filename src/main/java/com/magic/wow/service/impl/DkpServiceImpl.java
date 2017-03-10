package com.magic.wow.service.impl;

import com.magic.wow.mapper.DkpMapper;
import com.magic.wow.mapper.UserMapper;
import com.magic.wow.model.DTRequest;
import com.magic.wow.model.Dkp;
import com.magic.wow.service.DkpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhaoxf on 2017/3/8.
 */
@Service
public class DkpServiceImpl implements DkpService {

    @Autowired
    private DkpMapper dkpMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public long countByQuery(DTRequest dtRequest) {
        return dkpMapper.countByQuery(dtRequest);
    }

    @Override
    public void add(Dkp dkp) {
        dkpMapper.add(dkp);
        if ("活动加分".equals(dkp.getType())) {
            userMapper.updateDynamic(dkp.getNum(), dkp.getUid());
        } else {
            userMapper.updateConsume(dkp.getNum(), dkp.getUid());
        }
    }

    @Override
    public Dkp findById(int id) {
        return dkpMapper.findById(id);
    }

    @Override
    public List<Dkp> findByQuery(DTRequest dtRequest) {
        return dkpMapper.pageByQuery(dtRequest);
    }

    @Override
    public int del(int id) {
        Dkp dkp = findById(id);
        if (dkp != null) {
            if ("活动加分".equals(dkp.getType())) {
                userMapper.updateDynamic(-dkp.getNum(), dkp.getUid());
            } else {
                userMapper.updateConsume(-dkp.getNum(), dkp.getUid());
            }
            return dkpMapper.del(id);
        }
        return 0;
    }
}
