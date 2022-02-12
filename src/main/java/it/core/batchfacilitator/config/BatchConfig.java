package it.core.batchfacilitator.config;
 
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import it.core.batchfacilitator.task.FirstTask;
import it.core.batchfacilitator.task.SecondTask;
import lombok.extern.log4j.Log4j2;
@Log4j2
@Configuration
@EnableBatchProcessing
@Import({BatchScheduler.class})
public class BatchConfig {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;
  
    @Autowired
    private SimpleJobLauncher jobLauncherSend;
    
    @Autowired
    private SimpleJobLauncher jobLauncherStatus;
    
    @Autowired
    private PlatformTransactionManager transactionManager;
	
	@Autowired
	private FirstTask firstTask;
	
	@Autowired
	private SecondTask secondTask;
    
    @Bean
    public Step stepSend(){
        return steps.get("first")
                .tasklet(firstTask)
                .transactionManager(transactionManager)
                .build();
    }
  
    @Bean
    public Step stepStatus(){
        return steps.get("second")
                .tasklet(secondTask)
                .transactionManager(transactionManager)
                .build();
    }
    
    @Primary
    @Bean
    public Job sendJob(){
        return jobs.get("Job")
                .incrementer(new RunIdIncrementer())
                .start(stepSend())
                .build();
    }
    
    @Bean
    public Job statusJob(){
        return jobs.get("Job2")
                .incrementer(new RunIdIncrementer())
                .start(stepStatus())
                .build();
    }

    @Scheduled(fixedRate = 5000) //5 seconds
    public void reportCurrentTime() throws Exception{
    	log.info("The time is now {}", dateFormat.format(new Date()));
        JobParameters param = new JobParametersBuilder().addString("JobID",
                String.valueOf(System.currentTimeMillis())).toJobParameters();
        JobExecution execution =  jobLauncherSend.run(sendJob(), param);
        System.out.println("Send Job Execution Status: " + execution.getStatus());
    }
    //(cron = "0 30 1 * * 7") 
    @Scheduled(fixedRate = 15000) //15 seconds
    public void reportCurrentTimeStatus() throws Exception{
    	log.info("The time is now {}", dateFormat.format(new Date()));
        JobParameters param = new JobParametersBuilder().addString("JobID",
                String.valueOf(System.currentTimeMillis())).toJobParameters();
        JobExecution execution =  jobLauncherStatus.run(statusJob(), param);
        System.out.println("Status Job Execution Status: " + execution.getStatus());
    }
}