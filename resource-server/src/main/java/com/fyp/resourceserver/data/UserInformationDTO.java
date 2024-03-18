package com.fyp.resourceserver.data;

import jakarta.persistence.GeneratedValue;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserInformationDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String profilePicture;
}
