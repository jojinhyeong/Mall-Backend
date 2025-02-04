package org.jjh.mallapi.service;

import lombok.extern.slf4j.Slf4j;
import org.jjh.mallapi.dto.PageRequestDTO;
import org.jjh.mallapi.dto.PageResponseDTO;
import org.jjh.mallapi.dto.ProductDTO;
import org.jjh.mallapi.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Slf4j
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        PageResponseDTO<ProductDTO> result = productService.getList(pageRequestDTO);
        log.info(result.getDtoList().toString());

    }

    @Test
    public void testRegister() {

        ProductDTO productDTO = ProductDTO.builder()
                .pname("새로운 상품")
                .pdesc("신규 추가 상품입니다.")
                .price(1000)
                .build();

        //uuid가 있어야 함
        productDTO.setUploadFileNames(
                List.of(
                        UUID.randomUUID()+"_" +"Test1.jpg",
                        UUID.randomUUID()+"_" +"Test2.jpg"));

        productService.register(productDTO); }
}
