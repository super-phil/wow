package com.magic.wow.controller;

import com.magic.wow.model.DTRequest;
import com.magic.wow.model.DTResponse;
import com.magic.wow.model.Dkp;
import com.magic.wow.service.DkpService;
import com.magic.wow.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhaoxf on 2017/3/8.
 */
@Slf4j
@RestController
@RequestMapping("dkp")
public class DkpController {
    @Resource
    private DkpService dkpService;


    @GetMapping(value = {"", "index"})
    public ModelAndView list() {
        return new ModelAndView("dkp");
    }

    @PostMapping("list")
    public DTResponse list(@RequestBody DTRequest dtRequest) {
        List<Dkp> list = dkpService.findByQuery(dtRequest);
        DTResponse<Dkp> dtResponse = new DTResponse<>();
        dtResponse.setDraw(dtRequest.getDraw());
        Long count = dkpService.countByQuery(dtRequest);
        dtResponse.setRecordsTotal(count);//总数据
        dtResponse.setRecordsFiltered(count);
        dtResponse.setData(list);
        return dtResponse;
    }

    @PostMapping("add")
    public Object add(@RequestParam("id") String uid, @RequestParam("type") String role, String num, String username, String info, @RequestParam("dpk_type") String type, @RequestParam("dt") String dt) {
        Dkp dkp = new Dkp();
        dkp.setUsername(username);
        dkp.setUid(Long.valueOf(uid));
        dkp.setType(type);
        dkp.setRole(role);
        dkp.setInfo(info);
        dkp.setNum(Integer.valueOf(num));
        dkp.setCreateTime(new DateTime(dt).toDate());
        dkpService.add(dkp);
        return ResultUtils.operationSuccess();

    }

    @PostMapping("del")
    public Object add(int id) {
        System.out.println(dkpService.del(id));
        return ResultUtils.operationSuccess();
    }

}
