package com.example.demo.writter;

import com.example.demo.entity.Profile;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class ProfileWriter {

    @Autowired
    private EntityManagerFactory emf;

    @Bean
    public JpaItemWriter<Profile> writer(){
        return new JpaItemWriterBuilder<Profile>().entityManagerFactory(emf).build();
    }


}
