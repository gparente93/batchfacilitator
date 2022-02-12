package it.core.batchfacilitator.config;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.core.batchfacilitator.service.FacilitatorService;
import it.core.batchfacilitator.task.FirstTask;
import it.core.batchfacilitator.task.SecondTask;

@Configuration
public class JobConfiguration extends DefaultBatchConfigurer {

    @Override
    protected JobRepository createJobRepository() throws Exception {
        MapJobRepositoryFactoryBean factoryBean = new MapJobRepositoryFactoryBean();
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    public FirstTask firstTasklet(FacilitatorService facilitatorService){
        return new FirstTask(facilitatorService);
    }
    @Bean
    public SecondTask statoDatTasklet(FacilitatorService facilitatorService){
        return new SecondTask(facilitatorService);
    }


}