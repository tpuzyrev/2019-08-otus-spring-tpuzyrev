package ru.otus.spring.usertesting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.usertesting.services.ConsoleUserInteractionImpl;
import ru.otus.spring.usertesting.services.TestRunner;
import ru.otus.spring.usertesting.services.UserInteraction;

import javax.jws.soap.SOAPBinding;
import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class applicationTest {

    @Autowired
    TestRunner testRunner;

    @MockBean
    UserInteraction userInteraction;

    @Test
    public void runTesting() {
        Mockito.when(userInteraction.getInputText()).thenReturn("Пузырев Тимофей Иванович");
        Mockito.when(userInteraction.getInputNumber(Mockito.anyCollection(), Mockito.anyBoolean())).thenReturn(Collections.singletonList(1));
        testRunner.runTesting();
    }
}
