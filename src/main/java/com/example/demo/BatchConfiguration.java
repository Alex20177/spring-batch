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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfiguration {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Employee> reader(){
        return new FlatFileItemReaderBuilder<Employee>()
                .name("employeeItemReader")
                .resource(new ClassPathResource("employees.csv"))
                .delimited()
                .names("empCode","empName","expInYears")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Employee>(){{setTargetType(Employee.class);}})
                .linesToSkip(1)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Profile> writer(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Profile>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Profile>())
                .sql("INSERT INTO Profile (EMP_CODE, EMP_NAME, PROFILE_NAME) VALUES (:empCode, :empName, :profileName)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public ItemProcessor<Employee, Profile> processor(){
        return  new EmployeeProcessor();
    }

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
    public Step step1(ItemReader<Employee> reader, ItemProcessor<Employee, Profile> processor
    , ItemWriter<Profile> writer){
        return stepBuilderFactory.get("step1")
                .<Employee, Profile>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}
