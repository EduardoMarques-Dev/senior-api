package com.emarques.seniorapi.api.mapper;

import com.emarques.seniorapi.api.model.input.ProdutoInput;
import com.emarques.seniorapi.api.model.ouput.ProdutoOutput;
import com.emarques.seniorapi.api.util.ModelConverter;
import com.emarques.seniorapi.domain.model.Produto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProdutoMapper implements ModelConverter<Produto, ProdutoInput, ProdutoOutput> {

    private ModelMapper modelMapper;


    @Override
    public Produto toDomain(ProdutoInput input) {
        return modelMapper.map(input, Produto.class);
    }

    @Override
    public ProdutoOutput toOutput(Produto domain) {
        return modelMapper.map(domain, ProdutoOutput.class);
    }

    @Override
    public List<Produto> toDomainCollection(List<ProdutoInput> inputList) {
        return inputList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdutoOutput> toOutputCollection(List<Produto> domainList) {
        return domainList.stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProdutoOutput> toOutputCollection(Page<Produto> domainList) {
        return new PageImpl<>(toOutputCollection(domainList.toList()));
    }
}
