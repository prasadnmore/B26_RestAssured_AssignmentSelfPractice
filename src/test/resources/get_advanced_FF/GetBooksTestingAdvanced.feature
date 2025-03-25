Feature: testing for books get api advanced

Scenario: 1.Validate one of the book json should have title value as Git Pocket Guide
Given Hit books API 
Then Validate attribute at json path "books[XX].title" and its value "Git Pocket Guide" 