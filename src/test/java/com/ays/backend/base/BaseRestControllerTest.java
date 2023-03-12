package com.ays.backend.base;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles(value = "dev")
@AutoConfigureMockMvc(addFilters = false)
public abstract class BaseRestControllerTest{

}
