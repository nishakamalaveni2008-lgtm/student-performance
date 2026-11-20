# Testing Plan and Test Cases

| ID | Test / Input | Expected result | Actual result |
|---|---|---|---|
| TC01 | Add student with unique ID and valid fields | Student saved and listed | To be filled after execution |
| TC02 | Add another student with same ID | Duplicate rejected; error shown | To be filled after execution |
| TC03 | Add subject with max=100, pass=40 | Subject saved | To be filled after execution |
| TC04 | Enter internal=25, exam=60 for max=100 | Total 85, 85%, A+, PASS | To be filled after execution |
| TC05 | Enter total below pass mark | FAIL and RA/C grade according to percentage | To be filled after execution |
| TC06 | Enter marks whose sum exceeds maximum | Input rejected | To be filled after execution |
| TC07 | Re-enter marks for same student and subject | Existing result updated, not duplicated | To be filled after execution |
| TC08 | Open student report | Correct student and subject result rows displayed | To be filled after execution |
| TC09 | Run Python analysis with records | Clean CSV and three charts generated | To be filled after execution |

## Testing Techniques
- **Black-box testing:** verify forms and outputs against requirements.
- **Boundary-value analysis:** test marks at 0, pass mark, maximum and above maximum.
- **Integration testing:** verify Java/JPA/MySQL and Python/MySQL connections.
- **Regression testing:** rerun result calculations after changes to grade rules.
- **Validation testing:** confirm that user inputs meet defined constraints.

**Submission note:** Fill the Actual Result column only after executing each test. Do not report unexecuted tests as passed.
