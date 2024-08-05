package com.example.dev_pro.service.shelterImpl;

import com.example.dev_pro.impl.shelterImpl.DogShelterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DogShelterServiceImplUnitTest extends ShelterServiceImplTest {
    @InjectMocks
    private DogShelterServiceImpl service;

    @Override
    @BeforeEach
    public void init() {
        super.service = service;
    }
}
