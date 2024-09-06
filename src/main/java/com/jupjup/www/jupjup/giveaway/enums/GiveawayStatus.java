package com.jupjup.www.jupjup.giveaway.enums;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter


public enum GiveawayStatus {
    PENDING,
    RESERVED,
    COMPLETED
}
