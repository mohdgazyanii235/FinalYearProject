package com.fyp.erpapi.erpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for user's personal information.
 * This class is used to convey user information, such as first and last name, between the application layers,
 * typically during user profile updates or onboarding processes.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserInfoDTO {

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

}
