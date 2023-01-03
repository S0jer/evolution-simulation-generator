package org.simulation.app;

import java.io.IOException;

public interface DayFinishedObserver {
    void dayFinished() throws IOException;
}
