package com.github.gnx.automate;

import com.github.gnx.automate.exec.ExecEnvConfig;
import com.github.gnx.automate.exec.IExecTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class AutomateApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomateApiApplication.class, args);
    }


}
