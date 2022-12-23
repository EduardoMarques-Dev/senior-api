package com.emarques.seniorapi.api.util;

import java.util.List;
import java.util.stream.Collectors;

//
//@Component
//@AllArgsConstructor
public interface ModelConverter<DomainModel, InputModel, OutputModel> {

    DomainModel toDomain(InputModel input);
    OutputModel toOutput(DomainModel domain);

    List<DomainModel> toDomainCollection(List<InputModel> inputList);
    List<OutputModel> toOutputCollection(List<DomainModel> domainList);

//    default List<DomainModel> toDomainCollection(List<InputModel> inputList) {
//        return inputList.stream()
//                .map(input -> (DomainModel) toDomain(input))
//                .collect(Collectors.toList());
//    }
//    default List<OutputModel> toOutputCollection(List<DomainModel> domainList) {
//        return domainList.stream()
//                .map(domain -> (OutputModel) toOutput(domain))
//                .collect(Collectors.toList());
//    }



//    public Object outputToDomain(OutputModel output) {
//        return modelMapper.map(output, domainClass);
//    }
//    public Object domainToInput(DomainModel domain) { return modelMapper.map(domain, inputClass);}

}
