package com.example.demo.impls;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.customRepositories.ReservationCustomRepository;


@SuppressWarnings("rawtypes")
@Repository
@Transactional
public class ReservationRepositoryImpl implements ReservationCustomRepository {
}
