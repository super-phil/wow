package com.magic.wow.config;


import com.google.common.collect.Maps;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhaoxf on 2017/3/6.
 * http://blog.csdn.net/catoop/article/details/50520958
 */
@Configuration
public class ShiroConfiguration {

    @Bean(name = "ehCacheManager")
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager cacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * @return
     * @see org.apache.shiro.mgt.SecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm());
        manager.setCacheManager(cacheManager());
        manager.setSessionManager(defaultWebSessionManager());
        return manager;
    }

    /**
     * @return
     * @see DefaultWebSessionManager
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(cacheManager());
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;
    }

    /**
     * @return
     */
    @Bean
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public WowRealm userRealm() {
        WowRealm userRealm = new WowRealm();
        userRealm.setCacheManager(cacheManager());
        return userRealm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     *
     * @author SHANHY
     * @create 2016年1月14日
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean/*, StudentService stuService, IScoreDao scoreDao*/) {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/home", "authc");//anon 可以理解为不拦截
        filterChainDefinitionMap.put("/chart", "authc");//anon 可以理解为不拦截
        filterChainDefinitionMap.put("/user", "authc");//anon 可以理解为不拦截
        filterChainDefinitionMap.put("/**/list", "authc");
        filterChainDefinitionMap.put("/**/add", "perms[admin]");
        filterChainDefinitionMap.put("/**/del", "perms[admin]");
        filterChainDefinitionMap.put("/login", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {

        ShiroFilterFactoryBean bean = new MShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/home");
        bean.setUnauthorizedUrl("/403");
        Map<String, Filter> filters = Maps.newHashMap();
        filters.put("perms", new WowFilter());
        bean.setFilters(filters);
        loadShiroFilterChain(bean/*, stuService, scoreDao*/);
        return bean;
    }

}
