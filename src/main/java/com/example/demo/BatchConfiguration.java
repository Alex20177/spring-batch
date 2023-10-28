package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Profile;
import com.example.demo.listener.EmpJobExecutionListener;
import com.example.demo.processor.EmployeeProcessor;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfiguration {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job createEmployeeJob(EmpJobExecutionListener listener, Step step1){
        return jobBuilderFactory.get("createEmployeeJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();

    }

    @Bean
    public Step step1(ItemReader<Employee> reader, ItemWriter<Profile> writer){
        return stepBuilderFactory.get("step1")
                .<Employee, Profile>chunk(10)
                .reader(reader)
                .processor(new EmployeeProcessor())
                .writer(writer)
                .build();
    }


}
