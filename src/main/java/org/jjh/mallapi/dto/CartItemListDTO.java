package org.jjh.mallapi.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
public class CartItemListDTO {

    private Long cino;

    private int qty;

    private Long pno;

    private String pname;

    private int price;

    private String imageFile;

    public CartItemListDTO(Long cino, int qty, Long pno, String pname, int price, String imageFile) {
        this.cino = cino;
        this.qty = qty;
        this.pno = pno;
        this.pname = pname;
        this.price = price;
        this.imageFile = imageFile;
    }
}