package testController;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import base.AbstractContextControllerTest;
@RunWith(SpringJUnit4ClassRunner.class)
public class TestControllerTest extends AbstractContextControllerTest{
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = this.buildMockMvc();
	}
	@Test
	public void test1(){
		String uri = "/testController/test/1";
		String jsonFile = BASE_JSON_DIR + "testController/json/test1.json";
		try {
			String result = this.mock(this.mockMvc, uri, jsonFile);
			MatcherAssert.assertThat(result, Matchers.notNullValue());
			System.out.println(result);
		} catch (Exception e) {
			
		}
	}
	@Test
	public void testThrow(){
		String uri = "/testController/testThrow";
		String jsonFile = BASE_JSON_DIR + "testController/json/testThrow.json";
		try {
			String result = this.mock(this.mockMvc, uri, jsonFile);
			MatcherAssert.assertThat(result, Matchers.notNullValue());
			System.out.println(result);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testBean(){
		String uri = "/testController/testBean";
		try {
			String result = this.mock(this.mockMvc, uri, null);
			MatcherAssert.assertThat(result, Matchers.notNullValue());
			System.out.println(result);
		} catch (Exception e) {
			
		}
	}

	@Test
	public void testLogAspect(){
		String uri = "/testController/testLogAspect";
		try {
			String result = this.mock(this.mockMvc, uri, null);
			MatcherAssert.assertThat(result, Matchers.notNullValue());
			System.out.println(result);
		} catch (Exception e) {
			
		}
	}
	
}
