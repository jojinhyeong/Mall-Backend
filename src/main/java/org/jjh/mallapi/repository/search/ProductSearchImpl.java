package org.jjh.mallapi.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.jjh.mallapi.domain.Product;
import org.jjh.mallapi.domain.QProduct;
import org.jjh.mallapi.domain.QProductImage;
import org.jjh.mallapi.dto.PageRequestDTO;
import org.jjh.mallapi.dto.PageResponseDTO;
import org.jjh.mallapi.dto.ProductDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

@Slf4j
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(Product.class);
    }


    @Override
    public PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO) {

        log.info("--------------------------searchList---------------------------");

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,//페이지 시작 번호가 0부터 시작하므로
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.imageList, productImage);

        query.where(productImage.ord.eq(0));

        Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query);

        List<Tuple> productList = query.select(product, productImage).fetch();

        long count = query.fetchCount();

        log.info("==================");
        log.info(productList.toString());

        return null;
    }
}
