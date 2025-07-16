package com.example.bcsd_studium;

import com.example.bcsd_studium.domain.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.example.bcsd_studium.domain.repository.UserRepository;
import com.example.bcsd_studium.domain.repository.ExamRepository;
import com.example.bcsd_studium.domain.repository.AnswerRepository;

@SpringBootTest(properties = {
		"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration",
		"jwt.secret=01234567890123456789012345678901",
		"jwt.expiration=3600"
})
class BcsdStudiumApplicationTests {

	@MockitoBean
	private UserRepository userRepository;

	@MockitoBean
	private ExamRepository examRepository;

	@MockitoBean
	private AnswerRepository answerRepository;

	@MockitoBean
	private CommentRepository commentRepository;

	@Test
	void contextLoads() {
	}

}