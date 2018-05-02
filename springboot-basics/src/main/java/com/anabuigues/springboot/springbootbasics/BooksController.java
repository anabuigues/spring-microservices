package com.anabuigues.springboot.springbootbasics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {

	@GetMapping("/books")
	public List<Book> getAllBooks() {
		List<Book> list = new ArrayList<Book>();

		list.add(new Book(1L, "La vida es bella", "Ana Buigues"));

		return list;
	}
}
