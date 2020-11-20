package com.mikayelovich.config;

import com.mikayelovich.model.RoleEntity;
import com.mikayelovich.model.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    private final ApplicationContext context;


    public HibernateConfig(ApplicationContext context) {
        this.context = context;
    }

    /**
     *  creates a Hibernate SessionFactory.
     *  This is the usual way to set up a shared Hibernate <b>SessionFactory</b>
     *  in a Spring application <b>context</b>.
     * @return SessionFactory
     */
    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setConfigLocation(context.getResource("classpath:hibernate.cfg.xml")); // load hibernate config from that file
        factoryBean.setAnnotatedClasses(UserEntity.class, RoleEntity.class);
        return factoryBean;
    }

    /**
     * HibernateTransactionManager binds a Hibernate <b>Session</b> from the specified factory to the <b>thread</b>,
     * potentially allowing for one thread-bound Session per factory.
     * @return HibernateTransactionManager
     */
    @Bean
    public HibernateTransactionManager getTransactionManager() {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
}
