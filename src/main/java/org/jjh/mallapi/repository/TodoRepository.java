package org.jjh.mallapi.repository;

import org.jjh.mallapi.domain.Todo;
import org.jjh.mallapi.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {


}
