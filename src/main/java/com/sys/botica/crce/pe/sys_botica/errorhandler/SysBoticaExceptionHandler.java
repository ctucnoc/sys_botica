package com.sys.botica.crce.pe.sys_botica.errorhandler;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class SysBoticaExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(HttpStatusCodeException.class)
	protected ResponseEntity<Object> handleHttpRestClient(HttpStatusCodeException ex) {
		SysBoticaError sysceError = null;
		if (ex.getStatusCode().is4xxClientError()) {
			sysceError = new SysBoticaError(ex.getStatusCode(), SysBoticaConstant.PREFIX_CLIENT_ERROR);
		} else if (ex.getStatusCode().is5xxServerError()) {
			sysceError = new SysBoticaError(ex.getStatusCode(), SysBoticaConstant.PREFIX_SERVER_ERROR);
		}
		sysceError.setMessage(ex.getStatusText());
		return buildResponseEntity(sysceError);
	}

	@ExceptionHandler(SysBoticaEntityUnprocessableException.class)
	protected ResponseEntity<Object> handleConflict(SysBoticaEntityUnprocessableException ex) {
		SysBoticaError sysceError = new SysBoticaError(HttpStatus.UNPROCESSABLE_ENTITY,
				SysBoticaConstant.PREFIX_CLIENT_ERROR + SysBoticaConstant.UNPROCESSABLE_ENTITY);
		sysceError.setMessage(ex.getMessage());
		return buildResponseEntity(sysceError);
	}

	@ExceptionHandler(SysBoticaEntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(SysBoticaEntityNotFoundException ex) {
		SysBoticaError sysceError = new SysBoticaError(HttpStatus.NOT_FOUND,
				SysBoticaConstant.PREFIX_CLIENT_ERROR + SysBoticaConstant.NOT_FOUND);
		sysceError.setMessage(ex.getMessage());
		return buildResponseEntity(sysceError);
	}

	@ExceptionHandler(SysBoticaGenericClientException.class)
	protected ResponseEntity<Object> handleGenericClientException(SysBoticaGenericClientException ex) {
		SysBoticaError sysceError = new SysBoticaError(ex.getHttpStatus(), SysBoticaConstant.PREFIX_CLIENT_ERROR);
		sysceError.setMessage(ex.getMessage());
		sysceError.setSubErrors(ex.getSubErrors());
		return buildResponseEntity(sysceError);
	}

	@ExceptionHandler(SysBoticaUnauthorizedException.class)
	protected ResponseEntity<Object> handleEntityUnauthorized(SysBoticaUnauthorizedException ex) {
		SysBoticaError sysceError = new SysBoticaError(HttpStatus.UNAUTHORIZED,
				SysBoticaConstant.PREFIX_CLIENT_ERROR + SysBoticaConstant.UNAUTHORIZED);
		sysceError.setMessage(ex.getMessage());
		return buildResponseEntity(sysceError);
	}

	@ExceptionHandler(SysBoticaGenericServerException.class)
	protected ResponseEntity<Object> handleGenericServerException(SysBoticaGenericServerException ex) {
		SysBoticaError sysceError = null;
		if (ex.getCode() != null) {
			sysceError = new SysBoticaError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getCode());
		} else {
			sysceError = new SysBoticaError(HttpStatus.INTERNAL_SERVER_ERROR,
					SysBoticaConstant.PREFIX_SERVER_ERROR + SysBoticaConstant.INTERNAL_SERVER_ERROR);
		}
		sysceError.setMessage(ex.getMessage());
		return buildResponseEntity(sysceError);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleError(Exception ex) {
		SysBoticaError sysceError = new SysBoticaError(HttpStatus.INTERNAL_SERVER_ERROR,
				SysBoticaConstant.PREFIX_SERVER_ERROR + SysBoticaConstant.INTERNAL_SERVER_ERROR);
		sysceError.setMessage("Error generico de servidor " + ex.getMessage());
		return buildResponseEntity(sysceError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildResponseEntity(new SysBoticaError(HttpStatus.BAD_REQUEST,
				SysBoticaConstant.PREFIX_CLIENT_ERROR + SysBoticaConstant.BAD_REQUEST, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";

		return buildResponseEntity(new SysBoticaError(HttpStatus.BAD_REQUEST,
				SysBoticaConstant.PREFIX_CLIENT_ERROR + SysBoticaConstant.BAD_REQUEST, error, ex));
	}

	protected List<SysBoticaSubError> fillValidationErrorsFrom(MethodArgumentNotValidException argumentNotValid) {
		List<SysBoticaSubError> subErrorCollection = new ArrayList<>();
		argumentNotValid.getBindingResult().getFieldErrors().get(0).getRejectedValue();
		argumentNotValid.getBindingResult().getFieldErrors().stream().forEach((objError) -> {
			SysBoticaSubError sysceSubError = new SysBoticaValidationError(objError.getObjectName(),
					objError.getField(), objError.getRejectedValue(), objError.getDefaultMessage());
			subErrorCollection.add(sysceSubError);
		});
		return subErrorCollection;
	}

	private ResponseEntity<Object> buildResponseEntity(SysBoticaError sysceError) {
		return new ResponseEntity<>(sysceError, sysceError.getHttpStatus());
	}

}
