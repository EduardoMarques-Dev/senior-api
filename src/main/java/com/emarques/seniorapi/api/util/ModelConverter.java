package com.emarques.seniorapi.api.util;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public interface ModelConverter<DomainModel, InputModel, OutputModel> {

    DomainModel toDomain(InputModel input);

    OutputModel toOutput(DomainModel domain);



    List<DomainModel> toDomainCollection(List<InputModel> inputList);

    List<OutputModel> toOutputCollection(List<DomainModel> domainList);

    Page<OutputModel> toOutputCollection(Page<DomainModel> domainList);

}
