package com.ays.common.model;

import com.ays.common.util.validation.PhoneNumber;
import com.google.gson.Gson;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A class representing a phone number, including its country code and line number.
 * The phone number is validated using the custom PhoneNumber validation annotation.
 * This class is immutable and can be constructed using the Builder pattern.
 */
@Getter
@Setter
@Builder
@PhoneNumber
public class AysPhoneNumber implements AysPhoneNumberAccessor {

    /**
     * The country code of the phone number
     */
    @NotBlank
    private String countryCode;

    /**
     * The line number of the phone number
     */
    @NotBlank
    private String lineNumber;

    /**
     * This method returns a JSON representation of the object for validation exception messages.
     */
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
