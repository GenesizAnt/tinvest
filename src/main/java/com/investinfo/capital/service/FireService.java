package com.investinfo.capital.service;

import com.investinfo.capital.repository.FireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FireService {

    private final FireRepository fireRepository;

}
