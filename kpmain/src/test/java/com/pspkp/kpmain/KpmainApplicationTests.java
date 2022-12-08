package com.pspkp.kpmain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.description;

import java.lang.reflect.Executable;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pspkp.kpmain.models.Good;
import com.pspkp.kpmain.models.Role;
import com.pspkp.kpmain.models.User;
import com.pspkp.kpmain.repo.UserRepository;

@SpringBootTest
class KpmainApplicationTests {
	@Autowired
	private UserRepository userRepository;

	@Test
	void CheckGoodAndUserCreation() {
		User user = new User("username", "password", true, Collections.singleton(Role.USER), 0, 0);
		Good newgood = new Good("Наушники Logitech", "Отличные наушники", "", user);
		assertSame(newgood.getName(), "Наушники Logitech");
		assertSame(newgood.getDesc(), "Отличные наушники");
		assertSame(newgood.getImage(), "");
		assertSame(newgood.getAuthor(), user);
	}

	@Test
	void BanUser() {
		User user = new User("username", "password", true, Collections.singleton(Role.USER), 0, 0);
		user.setActive(false);
		assertFalse(user.isActive());
	}

	@Test
	void FalseTest_FindUser() {
		assertDoesNotThrow(() -> userRepository.findById(null)); 
	}

	@Test
	void ShortDesc() {

		String description = """
				Lorem Ipsum is simply dummy text of the printing and typesetting industry.
				Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
				when an unknown printer took a galley of type and scrambled it to make a type specimen book.
				It has survived not only five centuries, but also the leap into electronic typesetting,
				remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset
				sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus
				PageMaker including versions of Lorem Ipsum.
					""";

		User user = new User("username", "password", true, Collections.singleton(Role.USER), 0, 0);
		Good newgood = new Good("Наушники Logitech", description, "", user);

		assertTrue(newgood.getShortdesc().length() <= 55);

	}
}
