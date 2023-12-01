package com.fyp.erpapi.erpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateCompanyDTO {

    private Long adminId;
    private String name;
    private String address;
}
