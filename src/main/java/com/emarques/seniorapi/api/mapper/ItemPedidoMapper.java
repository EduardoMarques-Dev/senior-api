package com.emarques.seniorapi.api.mapper;

import com.emarques.seniorapi.api.model.input.ItemPedidoInput;
import com.emarques.seniorapi.api.model.ouput.ItemPedidoOutput;
import com.emarques.seniorapi.api.util.ModelConverter;
import com.emarques.seniorapi.domain.model.ItemPedido;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ItemPedidoMapper  implements ModelConverter<ItemPedido, ItemPedidoInput, ItemPedidoOutput> {

    private ModelMapper modelMapper;



    @Override
    public ItemPedido toDomain(ItemPedidoInput input) {
        return modelMapper.map(input, ItemPedido.class);
    }

    @Override
    public ItemPedidoOutput toOutput(ItemPedido domain) {
        return modelMapper.map(domain, ItemPedidoOutput.class);
    }

    @Override
    public List<ItemPedido> toDomainCollection(List<ItemPedidoInput> inputList) {
        return inputList.stream()
                .map(input -> toDomain(input))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemPedidoOutput> toOutputCollection(List<ItemPedido> domainList) {
        return domainList.stream()
                .map(domain -> toOutput(domain))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ItemPedidoOutput> toOutputCollection(Page<ItemPedido> domainList) {
        return new PageImpl<>(toOutputCollection(domainList.toList()));
    }
}
