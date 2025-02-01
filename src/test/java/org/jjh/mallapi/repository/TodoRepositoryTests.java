package org.jjh.mallapi.repository;


import lombok.extern.slf4j.Slf4j;
import org.jjh.mallapi.domain.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@SpringBootTest
@Slf4j
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void test1() {

        Assertions.assertNotNull(todoRepository);
        log.info(todoRepository.getClass().getName());
    }

    /*@Test
    public void testInsert() {

        Todo todo = Todo.builder()
                .title("Title")
                .content("Content...")
                .dueDate(LocalDate.of(2025, 01, 27))
                .build();

        Todo result = todoRepository.save(todo);

        log.info(String.valueOf(result));
    }
*/
    @Test
    public void testRead() {

        Long tno =1L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        log.info(String.valueOf(todo));
    }

    @Test
    public void testUpdate(){

        //먼저 로딩 하고 엔티티 객체를 변경
        Long tno =1L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        todo.setTitle("update title");
        todo.setContent("update content");
        todo.setComplete(true);

        todoRepository.save(todo);
    }

    @Test
    public void testPagin(){

        //페이지번호는 0부터 시작
        Pageable pageable = PageRequest.of(0, 10 , Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(String.valueOf(result.getTotalElements()));

        log.info(result.getContent().toString());
    }

  /*  @Test
    public void testSearch1(){
        todoRepository.search1();
    }*/

}
























