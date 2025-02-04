package org.jjh.mallapi.repository;

import lombok.extern.slf4j.Slf4j;
import org.jjh.mallapi.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Slf4j
public class ProductRepositoryTests {

    @Autowired
    ProductRespository productRepository;

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {

            Product product = Product.builder()
                .pname("상품"+i)
                .price(100*i)
                .pdesc("상품설명 " + i)
                .build();

            //2개의 이미지 파일 추가
            product.addImageString(UUID.randomUUID()+"_"+"IMAGE1.jpg");
            product.addImageString(UUID.randomUUID()+"_"+"IMAGE2.jpg");

            productRepository.save(product);
            log.info("-------------------");
        }
    }

    @Test
    public void testRead2() {
        Long pno = 1L;

        Optional<Product> result = productRepository.selectOne(pno);
        Product product = result.orElseThrow();

        log.info(String.valueOf(product));
        log.info(product.getImageList().toString());
    }

    @Commit
    @Transactional
    @Test
    public void testDelete(){

        Long pno = 2L;

        productRepository.updateToDelete(true, pno);
    }

    @Test
    public void testUpdate(){

        Product product = productRepository.selectOne(1L).get();
        product.changeName("1번 상품");
        product.changeDesc("1번 상품 설명입니다.");
        product.changePrice(5000);

        //첨부파일 수정
        product.clearList();
        product.addImageString(UUID.randomUUID().toString()+"_"+"NEWIMAGE1.jpg");
        product.addImageString(UUID.randomUUID().toString()+"_"+"NEWIMAGE2.jpg");
        product.addImageString(UUID.randomUUID().toString()+"_"+"NEWIMAGE3.jpg");

        productRepository.save(product);
    }

}