package com.magic.wow.controller;

import com.magic.wow.model.DTRequest;
import com.magic.wow.model.DTResponse;
import com.magic.wow.model.User;
import com.magic.wow.service.UserService;
import com.magic.wow.util.ExcelUtils;
import com.magic.wow.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * Created by zhaoxf on 2017/3/6.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    private int count = 1;

    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public ModelAndView list() {

//        for (int i = 0; i < 32; i++) {
//            User user = new User();
//            user.setUsername("第" + i + "人");
//            user.setDesc(i + "desc");
//            user.setPwd(i + "pwd");
//            user.setType(i % 2);
//
//        }
//        if (count == 1) {
//            synchronized (this) {
//                if (count == 1) {
//                    File file = new File("C:\\Users\\zhaoxf\\Desktop\\DKP.xlsx");
//                    List<User> users = ExcelUtils.read(file);
//                    for (User user : users) {
//                        userService.addUser(user);
//                    }
//                    count++;
//                }
//            }
//        }

//        file.deleteOnExit();
//        List<List<Object>> lists=read(file);
        return new ModelAndView("user");
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public DTResponse list(@RequestBody DTRequest dtRequest) {
        log.info("-------------------");
        List<User> list = userService.findByQuery(dtRequest);
        DTResponse<User> dtResponse = new DTResponse<>();
        dtResponse.setDraw(dtRequest.getDraw());
        long count = userService.countByQuery(dtRequest);
        dtResponse.setRecordsTotal(count);//总数据
        dtResponse.setRecordsFiltered(count);
        dtResponse.setData(list);
        return dtResponse;
    }

    @RequestMapping("/add")
    public Object add(User user) {
        user.setUsername(user.getUsername().trim());
        userService.addUser(user);
        return ResultUtils.operationSuccess();

    }

    @RequestMapping("/del")
    public Object add(int id) {
        System.out.println(userService.del(id));
        return ResultUtils.operationSuccess();
    }
}
