package org.jjh.mallapi.repository.search;

import org.jjh.mallapi.domain.Todo;
import org.jjh.mallapi.dto.PageRequestDTO;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<Todo> search1(PageRequestDTO pageRequestDTO);
}
