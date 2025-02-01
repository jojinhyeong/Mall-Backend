package org.jjh.mallapi.service;

import lombok.extern.slf4j.Slf4j;
import org.jjh.mallapi.domain.Todo;
import org.jjh.mallapi.dto.PageRequestDTO;
import org.jjh.mallapi.dto.TodoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
@Slf4j
public class TodoServiceTests {

    @Autowired
    TodoService todoService;

    @Test
    public void testGet(){
        Long tno = 3L;
        TodoDTO result = todoService.get(tno);
        log.info("Retrieved Todo: {}", result);
        assertNotNull(result);
    }

    @Test
    public void testRegister(){

        for(int i =5 ; i<100; i++){
            TodoDTO todoDTO = TodoDTO.builder()
                    .title("aaa"+i)
                    .content("contents"+i)
                    .dueDate(LocalDate.of(2025,01,28))
                    .build();

            todoService.resgister(todoDTO);

        }

    }

    @Test
    public void testModify(){

        TodoDTO todoDTO = TodoDTO.builder()
                .tno(3L)
                .title("modify")
                .content("modify_contents")
                .dueDate(LocalDate.of(2025,01,28))
                .build();

        todoService.modify(todoDTO);
    }

    @Test
    public void testGetList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        log.info("PageRequestDTO: {}", todoService.getList(pageRequestDTO));
    }












}
