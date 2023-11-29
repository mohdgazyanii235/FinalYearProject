package com.fyp.erpapi.erpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserInfoDTO {

    private Long id;
    private String firstName;
    private String lastName;

}
