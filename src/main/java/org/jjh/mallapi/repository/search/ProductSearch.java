package org.jjh.mallapi.repository.search;

import org.jjh.mallapi.dto.PageRequestDTO;
import org.jjh.mallapi.dto.PageResponseDTO;
import org.jjh.mallapi.dto.ProductDTO;

public interface ProductSearch {

    PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO);
}
