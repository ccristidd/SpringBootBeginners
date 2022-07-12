package com.springbootbeginners;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


//@SpringBootTest
class SpringBootBeginnersApplicationTests {
    Calculator calculator = new Calculator();

    @Test
    void contextLoads() {
    }

    @Test
    void adding() {
        //given
        int primulNr = 50;
        int alDoileaNr = 10;

        //when
        int rezultat = calculator.add(primulNr, alDoileaNr);

        //then
        int expected = 60;
        assertThat(rezultat).isEqualTo(expected);
    }

    class Calculator {
        public int add(int a, int b) {
            return a + b;
        }
    }
}
