package com.example.demo.listener;

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
    private JdbcTemplate jdbcTemplate;
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Executing jon id : "+jobExecution.getJobId());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        List<Profile> result = null;
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            result = jdbcTemplate.query("SELECT EMP_CODE, EMP_NAME, PROFILE_NAME FROM profile", new RowMapper<Profile>() {
                @Override
                public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new Profile(rs.getLong(1), rs.getString(2), rs.getString(3));
                }
            });
        }

        System.out.println("Number of records = " + result.size());
    }
}
