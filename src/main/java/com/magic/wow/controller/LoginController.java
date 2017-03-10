package com.magic.wow.controller;

import com.magic.wow.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * Created by zhaoxf on 2017/3/6.
 */
@Slf4j
@RestController
public class LoginController {
    @RequestMapping(value = {"/", "/index", "/login"}, method = RequestMethod.GET)
    public ModelAndView index(RedirectAttributes redirectAttributes, Model model) {
        Map<String, ?> flashAttributes = redirectAttributes.getFlashAttributes();
        if (!flashAttributes.isEmpty()) {//redirectAttributes 相当于重定向后携带隐藏参数 原理是先存到session 跳转完成后马上移除 安全
            model.addAttribute("msg", flashAttributes.get("msg"));
            model.addAttribute("username", flashAttributes.get("username"));
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@Valid User user, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String username = user.getUsername();
        UsernamePasswordToken token = new UsernamePasswordToken(username, user.getPwd());
        //获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        redirectAttributes.addFlashAttribute("username", username);
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            log.info("对用户[" + username + "]进行登录验证..验证开始");
            subject.login(token);
        } catch (UnknownAccountException uae) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("msg", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("msg", "密码不正确");
        } catch (LockedAccountException lae) {
            log.info("对用户[" + user.getUsername() + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("msg", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            redirectAttributes.addFlashAttribute("msg", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            log.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", "用户名或密码不正确");
        }
        //验证是否登录成功
        if (subject.isAuthenticated()) {
            log.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            //            request.getSession().setAttribute("user", subject.getPrincipal());//登陆成功存session
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);//如果是未登录 点链接进来的 (也就是上次访问的地址)
            if (null != savedRequest) {
                String requestURI = savedRequest.getRequestURI();
                log.info("对用户[" + username + "]进行登录验证..验证通过");
                return new ModelAndView("redirect:" + requestURI);//重定向上次访问的页面
            } else {
                log.info("对用户[" + username + "]进行登录验证..验证通过");
                return new ModelAndView("redirect:/home");
            }
        } else {
            token.clear();
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes) {
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("msg", "您已安全退出");
        return "redirect:/login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        log.info("------没有权限-------");
        return "403";
    }

}
