package com.library.circulation.domain.valueobject;

import com.library.shared.util.TsIdGenerator;
import lombok.Value;

/**
 * Value Object representing a unique identifier for Fine.
 */
@Value
public class ActivityId {
    Long value;

    private ActivityId(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("ActivityId cannot be null");
        }
        this.value = value;
    }

    public static ActivityId of(Long value) {
        return new ActivityId(value);
    }

    public static ActivityId generate() {
        return new ActivityId(TsIdGenerator.next());
    }
}
