package ru.diasoft.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@ApiModel(value = "Reference", description = "Справочник")
public class ReferenceDTO {

    @ApiModelProperty(dataType = "Long", value = "Уникальный идентификатор", position = 1)
    private Long id;

    @ApiModelProperty(dataType = "String", value = "Системное имя", required = true, position = 2)
    @NotNull(message="Необходимо указать системное имя справочника")
    @NotBlank(message="Системное имя справочника не может быть пустым")
    @Pattern(regexp = "^[A-Za-z0-9][A-Za-z0-9\\_\\s]*$",
            message = "Системное имя должно начинаться с латинских букв или цифр. Допустимы латинские буквы, цифры, нижнее подчёркивание и пробел")
    private String sysname;

    @ApiModelProperty(dataType = "String", value = "Имя", required = true, position = 3)
    @NotNull(message="Необходимо указать имя справочника")
    @NotBlank(message="Имя справочника не может быть пустым")
    private String name;

    @ApiModelProperty(dataType = "String", value = "Описание", position = 4)
    private String description;

}
