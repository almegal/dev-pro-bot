package com.example.dev_pro.service.shelterImpl;

import com.example.dev_pro.impl.shelterImpl.CatShelterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CatShelterServiceImplUnitTest extends ShelterServiceImplTest {
    @InjectMocks
    private CatShelterServiceImpl service;

    @Override
    @BeforeEach
    public void init() {
        super.service = service;
    }
}
