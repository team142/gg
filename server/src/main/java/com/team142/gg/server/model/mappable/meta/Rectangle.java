package com.team142.gg.server.model.mappable.meta;

import lombok.Getter;
import lombok.Setter;

public interface Rectangle {
    double width = 0;
    double height = 0;

    double getWidth();
    double getHeight();
}
