package com.emarques.seniorapi.api.mapper;

import com.emarques.seniorapi.api.model.input.PedidoInput;
import com.emarques.seniorapi.api.model.input.ProdutoInput;
import com.emarques.seniorapi.api.model.ouput.PedidoOutput;
import com.emarques.seniorapi.api.model.ouput.ProdutoOutput;
import com.emarques.seniorapi.api.util.ModelConverter;
import com.emarques.seniorapi.domain.model.Pedido;
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
public class PedidoMapper implements ModelConverter<Pedido, PedidoInput, PedidoOutput> {
    private ModelMapper modelMapper;
    @Override
    public Pedido toDomain(PedidoInput input) {
        return modelMapper.map(input, Pedido.class);
    }

    @Override
    public PedidoOutput toOutput(Pedido domain) {
        return modelMapper.map(domain, PedidoOutput.class);
    }

    @Override
    public List<Pedido> toDomainCollection(List<PedidoInput> inputList) {
        return inputList.stream()
                .map(input -> toDomain(input))
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoOutput> toOutputCollection(List<Pedido> domainList) {
        return domainList.stream()
                .map(domain -> toOutput(domain))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PedidoOutput> toOutputCollection(Page<Pedido> domainList) {
        return new PageImpl<>(toOutputCollection(domainList.toList()));
    }
}
