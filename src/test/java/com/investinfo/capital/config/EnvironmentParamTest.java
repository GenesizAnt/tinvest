package com.investinfo.capital.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentParamTest {

    private EnvironmentParam environmentParam;

    @BeforeEach
    public void setUp() {
        Map<String, String> userEnvironment = new HashMap<>();
        userEnvironment.put("C_PORT", "12345");
        userEnvironment.put("C_USER", "67890");
        userEnvironment.put("C_TG_NAME", "TestBot");
        userEnvironment.put("C_TG_TOKEN", "token123");
        userEnvironment.put("C_F_NAME", "John");
        userEnvironment.put("C_L_NAME", "Doe");
        userEnvironment.put("C_USERNAME", "johndoe");
        userEnvironment.put("C_IS_BOT", "true");

        environmentParam = new EnvironmentParam(userEnvironment);
    }

    @Test
    public void testTAccountId() {
        assertEquals("12345", environmentParam.tAccountId());
    }

    @Test
    public void testTgChatId() {
        assertEquals(67890L, environmentParam.tgChatId());
    }

    @Test
    public void testTgNameBot() {
        assertEquals("TestBot", environmentParam.tgNameBot());
    }

    @Test
    public void testTgToken() {
        assertEquals("token123", environmentParam.tgToken());
    }

    @Test
    public void testFirstName() {
        assertEquals("John", environmentParam.firstName());
    }

    @Test
    public void testLastName() {
        assertEquals("Doe", environmentParam.lastName());
    }

    @Test
    public void testTgUserName() {
        assertEquals("johndoe", environmentParam.tgUserName());
    }

    @Test
    public void testIsBot() {
        assertTrue(environmentParam.isBot());
    }

    @Test
    public void testIsBotFalse() {
        Map<String, String> userEnvironment = new HashMap<>();
        userEnvironment.put("C_IS_BOT", "false");
        EnvironmentParam envParam = new EnvironmentParam(userEnvironment);
        assertFalse(envParam.isBot());
    }
}