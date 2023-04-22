package com.example.Spring.Boot.CRUD.test;

import com.example.Spring.Boot.CRUD.test.controllers.StudentController;
import com.example.Spring.Boot.CRUD.test.entities.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
class StudentControllerTest {

	@Autowired
	private StudentController studentController;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;



	@Test
	void StudentControllerLoads() {
		assertThat(studentController).isNotNull();
	}

	private Student getStudentFromId(long id)  throws Exception{
		MvcResult result = this.mockMvc.perform(get("/student/"+ id))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		try{
			String studentJSON = result.getResponse().getContentAsString();
			return objectMapper.readValue(studentJSON, Student.class);
		}catch (Exception e){
			return null;
		}
	}

	private Student createAStudent()throws Exception{
		Student studen = new Student();
		studen.setName("Gino");
		studen.setSurname("Pugli");
		studen.setWorking(true);
		return createAStudent(studen);
	}

	private Student createAStudent(Student student) throws Exception {
		MvcResult result = createAStudentRequest(student);
		Student studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
		return studentFromResponse;
	}

	private MvcResult createAStudentRequest() throws Exception {
		Student student = new Student();
		student.setName("Chrus");
		student.setSurname("Pugli");
		student.setWorking(true);
		return createAStudentRequest(student);

	}

	private MvcResult createAStudentRequest(Student student) throws Exception{
	if(student == null)return null;
	String studentJSON = objectMapper.writeValueAsString(student);
	return  this.mockMvc.perform(post("/student")
			.contentType(MediaType.APPLICATION_JSON)
			.content(studentJSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
	}

	@Test
	void createStudentTest()throws Exception{
		Student studentFromResponse = createAStudent();
		assertThat(studentFromResponse.getId()).isNotNull();
	}

	@Test
	void readSingleStudent() throws Exception{
		Student student = createAStudent();
		assertThat(student).isNotNull();
		assertThat(student.getId()).isNotNull();

		Student studentFromResponse = getStudentFromId(student.getId());
		assertThat(studentFromResponse.getId()).isNotNull();
		assertThat(studentFromResponse.getId()).isEqualTo(student.getId());
	}


	@Test
	void updateStudent()throws Exception{
		Student student = createAStudent();
		assertThat(student.getId()).isNotNull();
		String newName = "Chris";
		student.setName(newName);
		String studentJSON = objectMapper.writeValueAsString(student);

		MvcResult result = this.mockMvc.perform(put("/student/" + student.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(studentJSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		Student studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
		assertThat(studentFromResponse.getId()).isEqualTo(student.getId());
		assertThat(studentFromResponse.getName()).isEqualTo(newName);

		Student studentFromResponseGET = getStudentFromId(student.getId());
		assertThat(studentFromResponseGET.getId()).isNotNull();
		assertThat(studentFromResponseGET.getId()).isEqualTo(student.getId());
		assertThat(studentFromResponseGET.getName()).isEqualTo(newName);

	}

	@Test
	void deleteStudent() throws Exception{
		Student student = createAStudent();
		assertThat(student.getId()).isNotNull();

		this.mockMvc.perform(delete("/student/" + student.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		Student studentFromResponseGet = getStudentFromId(student.getId());
		assertThat(studentFromResponseGet).isNull();
	}

	@Test
	void workingStudent()throws Exception{
		Student student = createAStudent();
		assertThat(student.getId()).isNotNull();

		MvcResult result= this.mockMvc.perform(put("/student/" + student.getId() + "/working?working=true"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		Student studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
		assertThat(studentFromResponse.getId()).isNotNull();
		assertThat(studentFromResponse.getId()).isEqualTo(student.getId());
		assertThat(studentFromResponse.isWorking()).isEqualTo(true);

		Student studentFromResponseGET = getStudentFromId(student.getId());
		assertThat(studentFromResponseGET.getId()).isNotNull();
		assertThat(studentFromResponseGET.getId()).isEqualTo(student.getId());
		assertThat(studentFromResponseGET.isWorking()).isEqualTo(true);





	}




}
