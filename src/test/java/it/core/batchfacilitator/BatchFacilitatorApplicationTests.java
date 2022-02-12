package it.core.batchfacilitator;

import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import it.core.batchfacilitator.BatchFacilitatorApplication;

@SpringBootTest(classes = BatchFacilitatorApplication.class)
@ActiveProfiles("dev")
@TestConfiguration 
class BatchFacilitatorApplicationTests implements MergedBeanDefinitionPostProcessor  {
	
    @Autowired
    private Job job;       

    @Bean(name="jtestl")
    public JobLauncherTestUtils jobLauncherTestUtils() {
        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJob(job);
        jobLauncherTestUtils.launchStep("stepSend");
        return jobLauncherTestUtils;
    }
    
    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        if(beanName.equals("jtestl")) {
            beanDefinition.getPropertyValues().add("Job", getMyBeanFirstAImpl());
        }
    }

    private Object getMyBeanFirstAImpl() {
        return job;
    }


}
