package microservices.book.gamification.challenge;

import lombok.Value;

@Value
public class ChallengeSolvedDTO {
    Long attemptId;
    boolean correct;
    int factorA;
    int factorB;
    Long userId;
    String userAlias;
}
