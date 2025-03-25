package get_SD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utility.SupportingUtility;


public class March22_Assignment_SD {

			
			Response response = null;
			Response invalidResponse = null;
			
		
			@Given("Hit books API")
			public void hit_books_api() {
		
						response  = RestAssured.given().get("https://demoqa.com/BookStore/v1/Books");
						System.out.println("output = "+response.statusCode());
			
			}

			@Then("Validate API returns multiple books json")
			public void validate_api_returns_multiple_books_json() {
			
						//System.out.println("All logs : "+response.then().log().all());
						int bookCount = response.body().jsonPath().getList("books").size();
						System.out.println("Total books count is : "+bookCount);
						Assert.assertTrue("Failed - multiple books not present", bookCount>1);
	  
			}

		
			@Then("Validate attribute at json path {string} and its value {string}")
			public void validate_attribute_at_json_path_and_its_value(String jsonPathstr, String expValue) {
						
						String actAttributeValue = response.body().jsonPath().getString(jsonPathstr);
						System.out.println("actual attribute from response"+actAttributeValue);
						Assert.assertTrue("Failed - atriibute value is not matching ", expValue.equals(actAttributeValue));
		
			}
	
	
	
			@Then("Validate isbn attribute at jason path {string} and confirm that it contain only numbers")
			public void validate_isbn_attribute_at_jason_path_and_confirm_that_it_contain_only_numbers(String jsonPathstr) {
				
						String actualISBNValue = response.body().jsonPath().getString(jsonPathstr);
						System.out.println("actual attribute from response"+actualISBNValue);
						Assert.assertTrue("Failed - ISBN value contains characters which are not numbers ", SupportingUtility.isNumber(actualISBNValue));
		
			}
	
			@Given("Hit books API with incorrect end point")
			public void hit_books_api_with_incorrect_end_point() {
		
						//Provided incorrect end point "Book" instead of "Books"
						 invalidResponse = RestAssured.given().get("https://demoqa.com/BookStore/v1/Book");  
						System.out.println("output = "+invalidResponse.statusCode());
	    
			}
	
			@Then("Validate that response data is not returned")
			public void validate_that_response_data_is_not_returned() {
						
						System.out.println("All logs : "+invalidResponse.then().log().all());
						int statCode =  invalidResponse.getStatusCode();
						Assert.assertTrue("Failed - data returned even though end point was incorrect", statCode != 200);
		 
			}

			@Then("Validate json path {string} and confirm that there are total {int} attributes present")
			public void validate_json_path_and_confirm_that_there_are_total_attributes_present(String jsonPathStr, int expectedCountOfAttributes) {
						
						//System.out.println("All logs : "+response.then().log().all());
						Map <String, String>  bookMap =  response.body().jsonPath().getJsonObject(jsonPathStr);
						int actualCountOfAttributes = bookMap.size();
						System.out.println("Total books count is : "+actualCountOfAttributes);
						Assert.assertTrue("Failed - book does not have 9 attributes ", (actualCountOfAttributes == expectedCountOfAttributes));
		  
			}
	
			@Then("each {string} attribute should be a number and should not contain any alphabetic or special characters")
			public void each_attribute_should_be_a_number_and_should_not_contain_any_alphabetic_or_special_characters(String jsonPathstr) {
						
						boolean result = false;
						int bookCount = response.body().jsonPath().getList("books").size();
						System.out.println("Total books count is : "+bookCount);
			
						for (int i = 0; i< bookCount; i++) {
				
									String actualJsonPath = jsonPathstr.replace("XX", ""+i+"");
									System.out.println("Newly built jason path : "+ actualJsonPath);
									String actualValueOfPages =  response.body().jsonPath().getString(actualJsonPath);
									System.out.println("Actual value of pages : "+ actualValueOfPages);
					
									if(!SupportingUtility.isNumber(actualValueOfPages)) {
												result = false;
												break;
									} else {
												result = true;
									}
						}
						Assert.assertTrue("Failed - pages attrbute value is not only numbers but contains characters as well ", result);
			}
		
			@Then("each {string} attribute  value should start with http or https")
			public void each_attribute_value_should_start_with_http_or_https(String jsonPathStr) {
	    
							boolean result = false;
							int bookCount = response.body().jsonPath().getList("books").size();
							System.out.println("Total books count is : " + bookCount);
		
							for (int i = 0; i< bookCount; i++) {
				
										String actualJsonPath = jsonPathStr.replace("XX", ""+i+"");
										System.out.println("Evaluating path : "+ actualJsonPath);
										String actualValueOfWebSite =  response.body().jsonPath().getString(actualJsonPath);
										System.out.println("Actual value of Website : "+ actualValueOfWebSite);
				
										if(!(actualValueOfWebSite.startsWith("http")||actualValueOfWebSite.startsWith("https")) ) {
													result = false;
													break;
										} else {
													result = true;
										}
							}
		
							Assert.assertTrue("Failed - website value does not start with http or https ", result);
			
			}

			@Then("each {string} attribute  value should not be blank or null")
			public void each_attribute_value_should_not_be_blank_or_null(String jsonPathStr) {

							boolean allAttributesValid = true;
							List<Map <String, Object>> books =  response.body().jsonPath().getList("books");
							int booksCount = books.size();
							System.out.println("Total number of books : " + booksCount);
			
							for (int i = 0; i < booksCount; i++ ) {
				
											Map <String, Object> bookMap = books.get(i);
											for (Map.Entry<String, Object> entry : bookMap.entrySet()) {
							
															String key = entry.getKey();
															Object value = entry.getValue();
							
															if (value == null || value.toString().isEmpty()) {
																			System.out.println("Book # " +i+" - Attribute : "  + key + " is null");
																			allAttributesValid = false;
															}
															else {
																			allAttributesValid = true;
															}
											}
				
							Assert.assertTrue("Failed - attribute value is null ", allAttributesValid);				
				}
			
			}			
		
			
			@Then("{string} publisher should have only {int} books namely {string} and {string}")
			public void publisher_should_have_only_books_namely_and(String publisherName, int expBooksCountForPublisher, String expBookTitle1, String expBookTitle2) {
	 
						List<String> expBookTitleList = Arrays.asList(expBookTitle1, expBookTitle2);
						List<String> actBookTitleList = new ArrayList<String>();
						List<Map <String, Object>> booksJsonList =  response.body().jsonPath().getList("books");
												
						for(Map<String, Object> bookMap : booksJsonList) {
									
										if(bookMap.get("publisher").equals(publisherName)) {
													actBookTitleList.add((String)bookMap.get("title"));
										}
										else {
													continue;
										}
							
						}
						
						System.out.println(actBookTitleList);
						
						Assert.assertTrue("Total books count for given publisher does not match", expBooksCountForPublisher == actBookTitleList.size());
						
						Assert.assertTrue("Actual book titles and expected book title for given publisher does not match ", (actBookTitleList.containsAll(expBookTitleList))&&(expBookTitleList.containsAll(actBookTitleList)) );
												
			}
		
			
			@Then("{string}  publisher is not the publishing any book for author {string}")
			public void publisher_is_not_the_publishing_any_book_for_author(String publisherToCheck, String authorToCheckAgainst) {
									
						List<String> authorList = new ArrayList<String>();
						List<Map <String, Object>> booksJsonList =  response.body().jsonPath().getList("books");
						
						for(Map<String, Object> bookMap : booksJsonList) {
							
										if(bookMap.get("publisher").equals(publisherToCheck)) {
													authorList.add((String)bookMap.get("author"));
										}
										else {
													continue;
										}
				
						}
				
						System.out.println(authorList);
						Assert.assertFalse("Failed - O'Reilly Media  publisher is publishing book for author Nicholas C. Zakas", authorList.contains(authorToCheckAgainst));
				
			}

			@Then("{string} publisher should have exactly {int} books")
			public void publisher_should_have_exactly_books(String publisherName, Integer expCountOfBooksForPublisher) {
			    			
							int actCountOfBooksForPublisher = 0;
							List<Map <String, Object>> booksJsonList =  response.body().jsonPath().getList("books");
							
							for(Map<String, Object> bookMap : booksJsonList) {
								
									if(bookMap.get("publisher").equals(publisherName)) {
												actCountOfBooksForPublisher++;
									}else {
												continue;
									}
									
							}		
							
							Assert.assertTrue("Failed - total book count is not matching with expected book count for publisher O'Reilly Media", actCountOfBooksForPublisher == expCountOfBooksForPublisher);
							
			}
					
}
	

