package edu.velalar.performance;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class GradeRuleTest {
 private String grade(double p){return p>=90?"O":p>=80?"A+":p>=70?"A":p>=60?"B+":p>=50?"B":p>=40?"C":"RA";}
 @Test void gradeBoundaries(){assertEquals("O",grade(90));assertEquals("A+",grade(85));assertEquals("C",grade(40));assertEquals("RA",grade(39.99));}
}
