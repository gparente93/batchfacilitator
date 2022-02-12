package it.core.batchfacilitator;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EntityScan(basePackages = { "it.core.batchfacilitator.entity" })
@EnableJpaRepositories(basePackages = { "it.core.batchfacilitator.repository" }) 
@ComponentScan(basePackages = {"it.core.batchfacilitator.model", "it.core.batchfacilitator.service", "it.core.batchfacilitator.config"})
@EnableTransactionManagement 
@EnableBatchProcessing
public class BatchFacilitatorApplication {
	
	 @Autowired
	 JobLauncher jobLauncher;
	 
	 @Autowired  
	 Job job;
	 
	public static void main(String[] args) {
		SpringApplication.run(BatchFacilitatorApplication.class, args);
		System.out.println("********************************************************************************************");
		System.out.println("********************************  BATCH FACILITATOR    **************************************");
		System.out.println("********************************************************************************************");

	}

    public void run(String... args) throws Exception
    {
        JobParameters params = new JobParametersBuilder()
                    .addString("JobID", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();
        jobLauncher.run(job, params);
    }

}
