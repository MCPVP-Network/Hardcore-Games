package com.mcpvp.hardcoregames.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum GameState
{
    INIT(false, false, -1),
    WAITING_FOR_PLAYERS(true, true, -1),
    COUNTINGDOWN(true, true, 12),
    GRACE_PERIOD(true, true, 60 * 2),
    LIVE(false, true, 60 * 60 * 4),
    CHAMPION(false, true, 30),
    SHUT_DOWN(false, false, -1);

    private boolean joinnable;
    private boolean staffJoinable;
    private int seconds;

    public boolean isTimable()
    {
        return this.seconds != -1;
    }
}
