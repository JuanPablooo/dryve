package br.com.drive.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class MyDataIntegrityViolationException extends ExceptionDetails {
}
