package com.deeppatel.rotamanager.helpers.enums;

import com.deeppatel.rotamanager.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public enum TimeChangeRequestStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private static final HashMap<TimeChangeRequestStatus, Integer> colors = new HashMap<TimeChangeRequestStatus,
            Integer>() {{
        put(PENDING, R.color.gray_600);
        put(APPROVED, R.color.green);
        put(REJECTED, R.color.red);
    }};

    private final String status;

    TimeChangeRequestStatus(String status) {
        this.status = status;
    }

    public static TimeChangeRequestStatus get(String status) {
        Optional<TimeChangeRequestStatus> result = Arrays.stream(TimeChangeRequestStatus.values())
                .filter(env -> env.status.equalsIgnoreCase(status))
                .findFirst();

        assert result.isPresent();
        return result.get();
    }

    public String getStatus() {
        return status;
    }

    public Integer getColor() {
        return colors.get(this);
    }
}
