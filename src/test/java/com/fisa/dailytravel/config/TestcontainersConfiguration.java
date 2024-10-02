package com.fisa.dailytravel.config;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {
//    @Bean
//    @ServiceConnection
//    OracleContainer oracleFreeContainer() {
//        return new OracleContainer(DockerImageName.parse("gvenzl/oracle-free:23-slim-faststart"))
//                .withReuse(true);
//    }

    @Bean
    @ServiceConnection
    MySQLContainer<?> mysqlContainer() {
        return new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
//                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
//                        new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(3306), new ExposedPort(3306)))
//                ))
                .withReuse(true);
    }

    @Bean
    @ServiceConnection(name = "redis")
    GenericContainer<?> redisContainer() {
        return new GenericContainer<>(DockerImageName.parse("redis:latest"))
//                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
//                        new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(6379), new ExposedPort(6379)))
//                ))
                .withReuse(true);

    }
}
