package com.fyp.erpapi.erpapi.data;

import lombok.*;

/**
 * Data Transfer Object (DTO) for a user's profile data.
 * This class is intended to encapsulate basic user profile information for transfer between application layers.
 * It can be expanded to include more user profile details as needed.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserProfileDataDTO {

    /**
     * The email of the user whose profile data is represented.
     */
    private String email;
}
