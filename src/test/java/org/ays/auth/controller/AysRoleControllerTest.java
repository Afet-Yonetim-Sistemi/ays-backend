package org.ays.auth.controller;

import org.ays.AysRestControllerTest;
import org.ays.auth.model.request.AysRoleCreateRequest;
import org.ays.auth.model.request.AysRoleCreateRequestBuilder;
import org.ays.auth.service.AysRoleCreateService;
import org.ays.common.model.response.AysErrorResponse;
import org.ays.common.model.response.AysResponse;
import org.ays.common.model.response.AysResponseBuilder;
import org.ays.common.util.exception.model.AysErrorBuilder;
import org.ays.util.AysMockMvcRequestBuilders;
import org.ays.util.AysMockResultMatchersBuilders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Set;

class AysRoleControllerTest extends AysRestControllerTest {

    @MockBean
    private AysRoleCreateService roleCreateService;


    private static final String BASE_PATH = "/api/v1";

    @Test
    void givenValidRoleCreateRequest_whenRoleCreated_thenReturnSuccess() throws Exception {
        // Given
        AysRoleCreateRequest mockCreateRequest = new AysRoleCreateRequestBuilder()
                .withValidValues()
                .build();

        // When
        Mockito.doNothing()
                .when(roleCreateService)
                .create(Mockito.any(AysRoleCreateRequest.class));

        // Then
        String endpoint = BASE_PATH.concat("/role");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AysMockMvcRequestBuilders
                .post(endpoint, mockAdminToken.getAccessToken(), mockCreateRequest);

        AysResponse<Void> mockResponse = AysResponseBuilder.SUCCESS;

        aysMockMvc.perform(mockHttpServletRequestBuilder, mockResponse)
                .andExpect(AysMockResultMatchersBuilders.status()
                        .isOk())
                .andExpect(AysMockResultMatchersBuilders.response()
                        .doesNotExist());

        // Verify
        Mockito.verify(roleCreateService, Mockito.times(1))
                .create(Mockito.any(AysRoleCreateRequest.class));
    }

    @Test
    void givenValidRoleCreateRequest_whenUserUnauthorized_thenReturnAccessDeniedException() throws Exception {
        // Given
        AysRoleCreateRequest mockCreateRequest = new AysRoleCreateRequestBuilder()
                .withValidValues()
                .build();

        // Then
        String endpoint = BASE_PATH.concat("/role");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AysMockMvcRequestBuilders
                .post(endpoint, mockUserToken.getAccessToken(), mockCreateRequest);

        AysErrorResponse mockErrorResponse = AysErrorBuilder.FORBIDDEN;
        aysMockMvc.perform(mockHttpServletRequestBuilder, mockErrorResponse)
                .andExpect(AysMockResultMatchersBuilders.status()
                        .isForbidden())
                .andExpect(AysMockResultMatchersBuilders.subErrors()
                        .doesNotExist());

        // Verify
        Mockito.verify(roleCreateService, Mockito.never())
                .create(Mockito.any(AysRoleCreateRequest.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "A",
            "% fsdh     ",
            "493268349068342",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla nec odio nec urna tincidunt fermentum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla nec odio nec urna tincidunt fermentum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla nec odio nec urna tincidunt fermentum."
    })
    void givenInvalidRoleCreateRequest_whenNameIsNotValid_thenReturnValidationError(String name) throws Exception {
        // Given
        AysRoleCreateRequest mockCreateRequest = new AysRoleCreateRequestBuilder()
                .withValidValues()
                .withName(name)
                .build();

        // Then
        String endpoint = BASE_PATH.concat("/role");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AysMockMvcRequestBuilders
                .post(endpoint, mockAdminToken.getAccessToken(), mockCreateRequest);

        AysErrorResponse mockErrorResponse = AysErrorBuilder.VALIDATION_ERROR;

        aysMockMvc.perform(mockHttpServletRequestBuilder, mockErrorResponse)
                .andExpect(AysMockResultMatchersBuilders.status()
                        .isBadRequest())
                .andExpect(AysMockResultMatchersBuilders.subErrors()
                        .isNotEmpty());

        // Verify
        Mockito.verify(roleCreateService, Mockito.never())
                .create(Mockito.any(AysRoleCreateRequest.class));
    }

    @Test
    void givenInvalidRoleCreateRequest_whenPermissionIdsAreNull_thenReturnValidationError() throws Exception {
        // Given
        AysRoleCreateRequest mockCreateRequest = new AysRoleCreateRequestBuilder()
                .withValidValues()
                .withPermissionIds(null)
                .build();

        // Then
        String endpoint = BASE_PATH.concat("/role");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AysMockMvcRequestBuilders
                .post(endpoint, mockAdminToken.getAccessToken(), mockCreateRequest);

        AysErrorResponse mockErrorResponse = AysErrorBuilder.VALIDATION_ERROR;

        aysMockMvc.perform(mockHttpServletRequestBuilder, mockErrorResponse)
                .andExpect(AysMockResultMatchersBuilders.status()
                        .isBadRequest())
                .andExpect(AysMockResultMatchersBuilders.subErrors()
                        .isNotEmpty());

        // Verify
        Mockito.verify(roleCreateService, Mockito.never())
                .create(Mockito.any(AysRoleCreateRequest.class));
    }

    @Test
    void givenInvalidRoleCreateRequest_whenPermissionIdsAreEmpty_thenReturnValidationError() throws Exception {
        // Given
        AysRoleCreateRequest mockCreateRequest = new AysRoleCreateRequestBuilder()
                .withValidValues()
                .withPermissionIds(Set.of())
                .build();

        // Then
        String endpoint = BASE_PATH.concat("/role");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AysMockMvcRequestBuilders
                .post(endpoint, mockAdminToken.getAccessToken(), mockCreateRequest);

        AysErrorResponse mockErrorResponse = AysErrorBuilder.VALIDATION_ERROR;

        aysMockMvc.perform(mockHttpServletRequestBuilder, mockErrorResponse)
                .andExpect(AysMockResultMatchersBuilders.status()
                        .isBadRequest())
                .andExpect(AysMockResultMatchersBuilders.subErrors()
                        .isNotEmpty());

        // Verify
        Mockito.verify(roleCreateService, Mockito.never())
                .create(Mockito.any(AysRoleCreateRequest.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "55aed4c4facb4b66bdb5-309eaaef4453"
    })
    void givenInvalidRoleCreateRequest_whenPermissionIdIsNotValid_thenReturnValidationError(String permissionId) throws Exception {
        // Given
        AysRoleCreateRequest mockCreateRequest = new AysRoleCreateRequestBuilder()
                .withValidValues()
                .withPermissionIds(Set.of(permissionId))
                .build();

        // Then
        String endpoint = BASE_PATH.concat("/role");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AysMockMvcRequestBuilders
                .post(endpoint, mockAdminToken.getAccessToken(), mockCreateRequest);

        AysErrorResponse mockErrorResponse = AysErrorBuilder.VALIDATION_ERROR;

        aysMockMvc.perform(mockHttpServletRequestBuilder, mockErrorResponse)
                .andExpect(AysMockResultMatchersBuilders.status()
                        .isBadRequest())
                .andExpect(AysMockResultMatchersBuilders.subErrors()
                        .isNotEmpty());

        // Verify
        Mockito.verify(roleCreateService, Mockito.never())
                .create(Mockito.any(AysRoleCreateRequest.class));
    }

}
