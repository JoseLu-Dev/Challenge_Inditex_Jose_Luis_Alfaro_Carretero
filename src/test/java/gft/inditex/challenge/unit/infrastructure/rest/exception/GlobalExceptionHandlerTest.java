package gft.inditex.challenge.unit.infrastructure.rest.exception;

import gft.inditex.challenge.domain.exception.NotFoundException;
import gft.inditex.challenge.infrastructure.rest.dto.ErrorResponse;
import gft.inditex.challenge.infrastructure.rest.exception.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private static final String REQUEST_URI = "/api/v1/brands/1/products/35455/prices";
    private static final String NOT_FOUND_MESSAGE = "No price found for brandId=1, productId=35455";
    private static final String PARAMETER_NAME = "onDate";
    private static final String INVALID_VALUE = "invalid-date";

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        when(request.getRequestURI()).thenReturn(REQUEST_URI);
    }

    @Test
    @DisplayName("Should handle NotFoundException and return 404 response")
    void shouldHandleNotFoundException() {
        // Given
        NotFoundException exception = new NotFoundException(NOT_FOUND_MESSAGE);

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleNotFoundException(
                exception, request
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getBody().error()).isEqualTo(HttpStatus.NOT_FOUND.getReasonPhrase());
        assertThat(response.getBody().message()).isEqualTo(NOT_FOUND_MESSAGE);
        assertThat(response.getBody().path()).isEqualTo(REQUEST_URI);
        assertThat(response.getBody().timestamp()).isNotNull();
    }

    @Test
    @DisplayName("Should handle MethodArgumentTypeMismatchException and return 400 response")
    void shouldHandleMethodArgumentTypeMismatchException() {
        // Given
        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                INVALID_VALUE, null, PARAMETER_NAME, null, null
        );

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMethodArgumentTypeMismatch(
                exception, request
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody().error()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(response.getBody().message())
                .isEqualTo("Invalid value 'invalid-date' for parameter 'onDate'");
        assertThat(response.getBody().path()).isEqualTo(REQUEST_URI);
        assertThat(response.getBody().timestamp()).isNotNull();
    }

    @Test
    @DisplayName("Should handle MissingServletRequestParameterException and return 400 response")
    void shouldHandleMissingServletRequestParameterException() {
        // Given
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException(
                PARAMETER_NAME, "Instant"
        );

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMissingServletRequestParameter(
                exception, request
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody().error()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(response.getBody().message())
                .isEqualTo("Required parameter 'onDate' is missing");
        assertThat(response.getBody().path()).isEqualTo(REQUEST_URI);
        assertThat(response.getBody().timestamp()).isNotNull();
    }
}
