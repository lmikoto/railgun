package io.github.lmikoto.railgun;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author liuyang
 * 2021/3/6 4:23 下午
 */
public class RailgunTest {

    private final Appender railgun = new Appender();

    private final String userDir = System.getProperty("user.dir");
    private final String testPath = "src/test/java/io/github/lmikoto/railgun";
    private final String configPath = userDir + File.separator + testPath + File.separator + "config.json";

    @Test
    @SneakyThrows
    public void gen(){
        String config = new String(Files.readAllBytes(Paths.get(configPath)));
        railgun.fullProcess(config,testPath + File.separator + "TestClass.java");
    }

    @Test
    public void append(){

    }

}
