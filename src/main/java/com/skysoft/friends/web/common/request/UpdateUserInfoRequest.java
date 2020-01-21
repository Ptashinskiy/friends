package com.skysoft.friends.web.common.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserInfoRequest {

    private String address;

    private String phoneNumber;

    private String firstName;

    private String lastName;
}
