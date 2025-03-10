package microservices.book.gamification.game.domain;

import lombok.Getter;

@Getter
public enum BadgeType {
    // Badges depending on score
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),
    // Other badges won for different conditions
    FIRST_WON("First time"),
    LUCKY_NUMBER("Lucky number");
    private final String description;

    BadgeType(String description) {
        this.description= description;
    }
}