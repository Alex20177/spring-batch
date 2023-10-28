package com.example.demo.listener;

import com.example.demo.ProfileRepository;
import com.example.demo.entity.Profile;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class EmpJobExecutionListener implements JobExecutionListener {

    @Autowired
    private ProfileRepository profileRepository;
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Executing jon id : "+jobExecution.getJobId());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        profileRepository.findAll().forEach(System.out::println);

    }
}
