package com.fyp.erpapi.erpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for marking the completion of a user's onboarding process.
 * Contains essential information required at the end of the onboarding flow.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OnBoardingCompleteDTO {

    private String email;
}
