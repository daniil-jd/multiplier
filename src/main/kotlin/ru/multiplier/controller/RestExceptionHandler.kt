package ru.multiplier.controller

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.beans.TypeMismatchException
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class RestExceptionHandler(private val messageSource: MessageSource) : ResponseEntityExceptionHandler() {

//    val log = KotlinLogging.logger { }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return if (ex is MethodArgumentTypeMismatchException) {
            buildResponseEntity(PARAMETER_VALIDATION_ERROR, HttpStatus.BAD_REQUEST, ex.name)
        } else {
            super.handleTypeMismatch(ex, headers, status, request)
        }

    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors
            .map { buildErrorBlock(FIELD_VALIDATION_ERROR, it.field) }
            .toList()
        return ResponseEntity.status(status).body(ErrorResponse(errors))
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val cause = ex.cause
        return when (cause) {
            is MissingKotlinParameterException -> {
                val fieldName = cause.parameter.name as String
                buildResponseEntity(MISSING_MANDATORY_FIELD, HttpStatus.BAD_REQUEST, fieldName)
            }
            is MismatchedInputException -> {
                val fieldName = cause.path.first().fieldName
                buildResponseEntity(FIELD_VALIDATION_ERROR, HttpStatus.BAD_REQUEST, fieldName)
            }
            else -> {
                super.handleHttpMessageNotReadable(ex, headers, status, request)
            }
        }
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<Any> {
        log.warn("Validation failed", ex)
        return buildResponseEntity(FIELD_VALIDATION_ERROR, HttpStatus.BAD_REQUEST, ex.fieldName)
    }

    @ExceptionHandler(AddressAlreadyExistException::class)
    fun handleAddressAlreadyExistException(ex: AddressAlreadyExistException): ResponseEntity<Any> {
        log.warn("Address already exists", ex)
        return buildResponseEntity(ADDRESS_ALREADY_EXIST, HttpStatus.BAD_REQUEST, ex.addressName)
    }

    @ExceptionHandler(AddressPositionAlreadyExistException::class)
    fun handleAddressPositionAlreadyExistException(ex: AddressPositionAlreadyExistException): ResponseEntity<Any> {
        log.warn("Position already exists", ex)
        return buildResponseEntity(ADDRESS_POSITION_EXIST, HttpStatus.BAD_REQUEST, ex.item, ex.addresses.toString())
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException): ResponseEntity<Any> {
        log.warn("Requested entity was not found", ex)
        return buildResponseEntity(NOT_FOUND, HttpStatus.NOT_FOUND, ex.entityName, ex.id)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<Any> {
        log.warn("Constraint violations", ex)
        val param = ex.constraintViolations.first().propertyPath.last().toString()
        return buildResponseEntity(PARAMETER_VALIDATION_ERROR, HttpStatus.BAD_REQUEST, param)
    }

    @ExceptionHandler(Throwable::class)
    fun handleGlobalException(t: Throwable) : ResponseEntity<Any> {
        log.error("Exception raised in RestExceptionHandler.handleGlobalException", t)
        return buildResponseEntity(INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(NavisionTimeToSendNotConfiguredStoreException::class)
    fun handleNavisionNotConfiguredException(ex: NavisionTimeToSendNotConfiguredStoreException) : ResponseEntity<Any> {
        log.error("Navision timetable not configured", ex)
        return buildResponseEntity(NAVISION_NOT_CONFIGURED, HttpStatus.INTERNAL_SERVER_ERROR, ex.store.toString())
    }

    @ExceptionHandler(NavisionTimeToSendNotFoundByStoreAndDateException::class)
    fun handleNavisionWrongDateException(ex: NavisionTimeToSendNotFoundByStoreAndDateException) : ResponseEntity<Any> {
        log.error("Navision timetable don't have records with store = ${ex.store} and date ${ex.date}", ex)
        return buildResponseEntity(NAVISION_WRONG_DATE, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AllParametersMissingException::class)
    fun handleEntityNotFoundException(ex: AllParametersMissingException): ResponseEntity<Any> {
        log.warn("There is no one parameter to search", ex)
        return buildResponseEntity(ALL_PARAMETERS_MISSING, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(PositionWithCountedStatusNotFoundException::class)
    fun positionWithCountedStatusNotFoundException(ex: PositionWithCountedStatusNotFoundException): ResponseEntity<Any> {
        log.warn("Error while virtual count", ex)
        return buildResponseEntity(VC_POSITION_WITH_COUNTED_STATUS_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(AddressSearchException::class)
    fun addressSearchException(ex: AddressSearchException): ResponseEntity<Any> {
        log.warn("Can't find unique address", ex)
        return buildResponseEntity(ADDRESS_SEARCH_EXCEPTION, HttpStatus.BAD_REQUEST)
    }

    private fun buildErrorBlock(error: ErrorCode, vararg params: String): ErrorBlock {
        return ErrorBlock(
            error.code,
            messageSource.getMessage(error.message, params, Locale.getDefault())
        )
    }

    private fun buildResponseEntity(
        error: ErrorCode,
        status: HttpStatus,
        vararg params: String
    ): ResponseEntity<Any> {
        return ResponseEntity.status(status).body(ErrorResponse(listOf(buildErrorBlock(error, *params))))
    }
}