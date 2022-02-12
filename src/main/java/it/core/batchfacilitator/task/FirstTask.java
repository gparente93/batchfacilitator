package it.core.batchfacilitator.task;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import it.core.batchfacilitator.service.FacilitatorService;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FirstTask implements Tasklet {

	FacilitatorService facilitatorService;

	public FirstTask(FacilitatorService facilitatorService) {
		this.facilitatorService = facilitatorService;
	}

	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		try {
			log.info("execute First Task every 5 seconds");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return RepeatStatus.FINISHED;
	}

}