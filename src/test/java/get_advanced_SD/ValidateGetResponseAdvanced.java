package get_advanced_SD;

import java.util.ArrayList;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ValidateGetResponseAdvanced {

	String urlString = null;
	Response response = null;
	
	@Given("Hit books API")
	public void hit_books_api() {
		response = RestAssured.given().get("https://demoqa.com/BookStore/v1/Books");
		   System.out.println("output = "+response.statusCode());
	}
	
	@Then("Validate attribute at json path {string} and its value {string}")
	public void validate_attribute_at_json_path_and_its_value(String jsonPathstr, String expValue) {

		/** my solution
		 
		boolean result;
		int bookCount = response.body().jsonPath().getList("books").size();
		ArrayList<String> titleList = new ArrayList<String>();
		
		for(int i = 0; i<bookCount; i++) {
			titleList.add("books[i].title"); 
		}
		
		for(int j = 0; j<bookCount; j++) {
			if(titleList.get(j).equals(expValue)) {
				result = true;
				break;
			}
			else {
				result = false;
			}
		}
		
		String actAttributeValue = response.body().jsonPath().getString(jsonPathstr);
		System.out.println("actual attribute from response"+actAttributeValue);
		Assert.assertTrue("Failed - attribute value is not matching ", expValue.equals(actAttributeValue));
	**/	
		
		boolean result = false;
		
		int bookCount = response.body().jsonPath().getList("books").size();
		
		for (int i = 0; i< bookCount; i++) {
			
			String jsonPathReplace = jsonPathstr.replace("XX", ""+i+"");
			System.out.println("Newly built jason path"+ jsonPathReplace);
			String actualTitle =  response.body().jsonPath().getString(jsonPathReplace);
			
			if (actualTitle.equals(expValue)) {
				System.out.println("Book value present");
				result = true;
				break;
			}else {
				result = false;
			}
			
		}
		Assert.assertTrue("Failed - atriibute value is not matching ", result);
	}
	
}
