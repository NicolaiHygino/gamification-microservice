package microservices.book.gamification.game;

import microservices.book.gamification.challenge.ChallengeSolvedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GameEventHandlerTest {
    @Mock
    private GameService gameService;
    @InjectMocks
    private GameEventHandler gameEventHandler;

    private ChallengeSolvedEvent challengeSolvedEvent;

    @BeforeEach
    void setUp() {
        challengeSolvedEvent = new ChallengeSolvedEvent(
                1L, true, 10, 10, 1L, "nicolai"
        );
    }

    @Test
    void whenHandleMultiplicationSolved_thenGameServiceIsCalled() {
        // When
        gameEventHandler.handleMultiplicationSolved(challengeSolvedEvent);
        // Then
        verify(gameService).newAttemptForUser(challengeSolvedEvent);
    }

    @Test
    void whenGameServiceThrowsException_thenAmqpExceptionIsThrown() {
        // Given
        doThrow(new RuntimeException("Simulated error")).when(gameService).newAttemptForUser(any());

        // When / Then
        Assertions.assertThrows(AmqpRejectAndDontRequeueException.class, () -> {
            gameEventHandler.handleMultiplicationSolved(challengeSolvedEvent);
        });
    }
}
