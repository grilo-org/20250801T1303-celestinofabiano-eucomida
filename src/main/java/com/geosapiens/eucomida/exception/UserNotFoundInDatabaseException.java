package com.geosapiens.eucomida.exception;

import static com.geosapiens.eucomida.constant.ErrorMessages.USER_NOT_FOUND_IN_DATABASE;

public class UserNotFoundInDatabaseException extends RuntimeException {
    public UserNotFoundInDatabaseException() {
        super(USER_NOT_FOUND_IN_DATABASE);
    }
}
