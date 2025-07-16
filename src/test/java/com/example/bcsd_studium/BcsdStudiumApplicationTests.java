package com.example.bcsd_studium;

import com.example.bcsd_studium.domain.repository.*;
import com.example.bcsd_studium.service.ExamService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

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

	@MockitoBean
	private QuestionRepository questionRepository;

	@Test
	void contextLoads() {
	}

}