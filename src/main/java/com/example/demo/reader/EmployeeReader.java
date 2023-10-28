package com.example.demo.reader;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Profile;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class EmployeeReader {

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

}
