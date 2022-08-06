package com.flutterwave.middleware.notification.repository;



import com.flutterwave.middleware.notification.domain.AppConfig;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppConfigRepository extends R2dbcRepository<AppConfig, Long>, ReactiveCrudRepository<AppConfig, Long>, ReactiveSortingRepository<AppConfig, Long> {

}
