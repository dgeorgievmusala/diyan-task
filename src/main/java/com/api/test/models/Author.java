package com.api.test.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private Integer id;
    private Integer idBook;
    private String firstName;
    private String lastName;

}
