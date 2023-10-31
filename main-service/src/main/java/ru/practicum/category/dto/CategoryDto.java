package ru.practicum.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
public class CategoryDto {

    private long id;

    @NotBlank(message = "Field: name. Error: must not be blank")
    @Size(min = 1, max = 50)
    private String name;
}