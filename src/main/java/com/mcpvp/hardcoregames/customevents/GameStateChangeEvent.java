package com.mcpvp.hardcoregames.customevents;

import com.mcpvp.hardcoregames.game.GameState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class GameStateChangeEvent extends EventBase
{
    private GameState from, to;
}
