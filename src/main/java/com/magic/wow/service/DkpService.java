package com.magic.wow.service;

import com.magic.wow.model.DTRequest;
import com.magic.wow.model.Dkp;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhaoxf on 2017/3/8.
 */
public interface DkpService {
    List<Dkp> findByQuery(DTRequest dtRequest);

    long countByQuery(DTRequest dtRequest);

    @Transactional
    void add(Dkp dkp);

    @Transactional
    int del(int id);

    Dkp findById(int id);

}
