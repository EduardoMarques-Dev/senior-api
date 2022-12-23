package com.emarques.seniorapi.api.util;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;
//
//@Component
//@AllArgsConstructor
public class ModelConverterStandBy<DomainModel, InputModel, OutputModel> {

    private ModelMapper modelMapper;
    Class<DomainModel> domainClass;
    Class<InputModel> inputClass;
    Class<OutputModel> outputClass;

    public DomainModel toDomain(InputModel input) {return modelMapper.map(input, domainClass);}
    public OutputModel toOutput(DomainModel domain) {
        return modelMapper.map(domain, outputClass);
    }

//    public Object outputToDomain(OutputModel output) {
//        return modelMapper.map(output, domainClass);
//    }
//    public Object domainToInput(DomainModel domain) { return modelMapper.map(domain, inputClass);}


    public List<DomainModel> toDomainCollection(List<InputModel> inputList) {
        return inputList.stream()
                .map(input -> (DomainModel) toDomain(input))
                .collect(Collectors.toList());
    }

    public List<OutputModel> toOutputCollection(List<DomainModel> domainList) {
        return domainList.stream()
                .map(domain -> (OutputModel) toOutput(domain))
                .collect(Collectors.toList());
    }

}
