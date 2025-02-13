package org.jjh.mallapi.repository;

import lombok.extern.slf4j.Slf4j;
import org.jjh.mallapi.domain.Cart;
import org.jjh.mallapi.domain.CartItem;
import org.jjh.mallapi.domain.Member;
import org.jjh.mallapi.domain.Product;
import org.jjh.mallapi.dto.CartItemListDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class CartRepositoryTests {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    public void testListOfMember() {

        String email = "user1@aaa.com";
        List<CartItemListDTO> cartItemList = cartItemRepository.getItemsOfCartDTOByEmail(email);

        for (CartItemListDTO dto : cartItemList) {
            log.info(String.valueOf(dto));
        }
    }

    @Transactional
    @Commit
    @Test
    public void testInsertByProduct() {
        log.info("test1-----------------------");

        //사용자가 전송하는 정보
        String email = "user1@aaa.com";
        Long pno = 6L;
        int qty = 3;
        //만일 기존에 사용자의 장바구니 아이템이 있었다면

        CartItem cartItem = cartItemRepository.getItemOfPno(email, pno);

        if (cartItem != null) {
            cartItem.changeQty(qty);
            cartItemRepository.save(cartItem);
            return;
        }

        //장바구니 아이템이 없었다면 장바구니부터 확인 필요
        //사용자가 장바구니를 만든적이 있는지 확인
        Optional<Cart> result = cartRepository.getCartOfMember(email);
        Cart cart = null;

        //사용자의 장바구니가 존재하지 않으면 장바구니 생성
        if (result.isEmpty()) {
            log.info("MemberCart is not exist!!");

            Member member = Member.builder().email(email).build();
            Cart tempCart = Cart.builder().owner(member).build();
            cart = cartRepository.save(tempCart);

        } else {
            cart = result.get();
        }

        log.info(String.valueOf(cart));

        Product product = Product.builder().pno(pno).build();

        cartItem = CartItem.builder()
                .product(product)
                .cart(cart)
                .qty(qty)
                .build();

        //상품 아이템 저장
        cartItemRepository.save(cartItem);

    }

    @Transactional
    @Test
    @Commit
    public void tesstUpdateByCino() {

        Long cino = 1L;

        int qty = 4;

        Optional<CartItem> result = cartItemRepository.findById(cino);

        CartItem cartItem = result.orElseThrow();

        cartItem.changeQty(qty);

        cartItemRepository.save(cartItem);

    }

    @Test
    public void testDeleteThenList() {
        Long cino = 1L;

        //장바구니 번호
        Long cno = cartItemRepository.getCartFromItem(cino);

        //삭제는 임시로 주석처리
        //cartItemRepository.deleteById(cino);
        //목록
        List<CartItemListDTO> cartItemList = cartItemRepository.getItemsOfCartDTOByCart(cno);

        for (CartItemListDTO dto : cartItemList) {
            log.info(String.valueOf(dto));
        }

    }

}
