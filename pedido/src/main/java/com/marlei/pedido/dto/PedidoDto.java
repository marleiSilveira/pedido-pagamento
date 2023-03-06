package com.marlei.pedido.dto;

import com.marlei.pedido.enumeration.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {
    private Long id;
    private LocalDateTime dataHora;
    private StatusEnum status;
    private List<ItemDoPedidoDto> itens = new ArrayList<>();
}
