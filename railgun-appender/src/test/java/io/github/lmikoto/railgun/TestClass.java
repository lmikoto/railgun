package io.github.lmikoto.railgun;

import lombok.Data;
import lombok.SneakyThrows;

@Data
public class TestClass extends TestSupperClass {

    TestClass test1;

    @SneakyThrows
    public void test(TestSupperClass test1) {
    }

}
