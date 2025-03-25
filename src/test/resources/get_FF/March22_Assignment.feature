Feature: testing for books get api

Scenario: 1.Response should get multiple books data
Given Hit books API
Then Validate API returns multiple books json

Scenario: 2.Validate single book json should have title value as Git Pocket Guide
Given Hit books API 
Then Validate attribute at json path "books[0].title" and its value "Git Pocket Guide" 

Scenario: 3.Validate isbn attribute value is always a number (and does not contain character) 
Given Hit books API 
Then Validate isbn attribute at jason path "books[0].isbn" and confirm that it contain only numbers

#Scenario: 4.Validate if end point is not correct (typing mistake, like book) it should not return data
#Given Hit books API with incorrect end point
#Then Validate that response data is not returned

Scenario: 5.Verify total 9 attributes are present for each book
Given Hit books API 
Then Validate json path "books[0]" and confirm that there are total 9 attributes present

Scenario: 6. Verify that the 'pages' attribute in each book is a numeric value
Given Hit books API 
Then each "books[XX].pages" attribute should be a number and should not contain any alphabetic or special characters

Scenario: 7. Verify that "website" attribute value should shart with "http://" and end with "index.html"
Given Hit books API 
Then each "books[XX].website" attribute  value should start with http or https

Scenario: 8. Verify that all the response attributes should not be blank or null
Given Hit books API 
Then each "books[XX].YY" attribute  value should not be blank or null

Scenario: 10. Validate that "No starch Press" publisher has exactly two books namely “Eloquent JavaScript, Second Edition” and “Understanding ECMAScript 6”
Given Hit books API 
Then "No Starch Press" publisher should have only 2 books namely "Eloquent JavaScript, Second Edition" and "Understanding ECMAScript 6"

Scenario: 11. Validate that “O Reilly Media” publisher is not the publishing any book for author “Nicholas C. Zakas”
Given Hit books API 
Then "O'Reilly Media"  publisher is not the publishing any book for author "Nicholas C. Zakas"

Scenario: 12. Validate that “O Reilly Media” publisher has 6 books only
Given Hit books API 
Then "O'Reilly Media" publisher should have exactly 6 books