package com.banglalink.toffee.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PagedResult<T>  implements Serializable {
    private Long totalCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;

    private List<T> result;
}
