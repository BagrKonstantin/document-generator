package com.db.document_generator.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.db.document_generator.domain")
@EnableJpaRepositories("com.db.document_generator.repos")
@EnableTransactionManagement
public class DomainConfig {
}
