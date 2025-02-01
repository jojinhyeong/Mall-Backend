package org.jjh.mallapi.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.jjh.mallapi.domain.QTodo;
import org.jjh.mallapi.domain.Todo;
import org.jjh.mallapi.dto.PageRequestDTO;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Slf4j
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl() {
        super(Todo.class);
    }

    @Override
    public Page<Todo> search1(PageRequestDTO pageRequestDTO) {

        log.info("Search1........");

        QTodo todo = QTodo.todo;

        JPQLQuery<Todo> query = from(todo);


        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1
                , pageRequestDTO.getSize(), Sort.by("tno").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<Todo> list = query.fetch(); //목록데이터

        long total = query.fetchCount(); //목록 카운트 long 타입

        return new PageImpl<>(list, pageable, total);
    }
}
