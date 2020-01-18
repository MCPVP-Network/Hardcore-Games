package com.mcpvp.hardcoregames.game;

import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.HardcoreGamesSettings;
import com.mcpvp.hardcoregames.commons.MathUtils;
import com.mcpvp.hardcoregames.customevents.GameStateChangeEvent;
import com.mcpvp.hardcoregames.feast.Feast;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Wait for enough players to join
 * - countdown
 * - respawn all
 * -
 */
public class Game implements Runnable
{
    @Getter
    private int RemainingSeconds;
    @Getter
    private GameState gameState = GameState.INIT;

    private Feast feast;

    public Game()
    {
        this.setGameState(GameState.WAITING_FOR_PLAYERS);

        start();
    }

    public boolean isInteractable()
    {
        return this.gameState == GameState.GRACE_PERIOD || this.gameState == GameState.LIVE;
    }






    public void start()
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(HardcoreGames.getPlugin(), this, 1, 20);
    }

    public void setGameState(GameState gameState)
    {
        GameStateChangeEvent event = new GameStateChangeEvent(this.gameState, gameState);

        event.call();

        this.gameState = event.getTo();
        this.RemainingSeconds = event.getTo().getSeconds();
    }


    /**
     * Trigged when time hits 0 or constantly if it's not a timnable stage like INIT or SHUTDOWN
     */
    public void updateGameState()
    {
        int playerCount = HardcoreGames.getPlayerManager().getPlayerCount();
        switch (this.getGameState())
        {

            case INIT:
                break;
            case WAITING_FOR_PLAYERS:
                if(playerCount >= HardcoreGamesSettings.MIN_PLAYERS_TO_START)
                    this.setGameState(GameState.COUNTINGDOWN);
                break;
            case COUNTINGDOWN:
                if(playerCount < HardcoreGamesSettings.MIN_PLAYERS_TO_START)
                    this.setGameState(GameState.WAITING_FOR_PLAYERS);
                else
                    this.setGameState(GameState.GRACE_PERIOD);
                break;
            case GRACE_PERIOD:
                this.setGameState(GameState.LIVE);
                break;
            case LIVE:

                break;
            case CHAMPION:
                this.setGameState(GameState.SHUT_DOWN);
                break;
            case SHUT_DOWN:
                break;
        }

    }

    /**
     *
     * @return seconds since game state started
     */
    public int getSeconds()
    {
        return  (this.getGameState().getSeconds() - this.getRemainingSeconds());
    }

    @Override
    public void run()
    {

        //logic
        System.out.println(this.getGameState().name() + " =?> " + getSeconds());
        if(this.getGameState() == GameState.LIVE)
        {
            if( this.getSeconds() == 60 * 12)
            {
                int x = MathUtils.r(200) - 100;
                int z = MathUtils.r(200) - 100;

                feast = new Feast(new Location(HardcoreGamesSettings.getHGWorld(), x, 50, z));
            }

            if( this.getSeconds() == 60 * 18)
            {
                feast.generatePlatform();
                feast.generateChests();
            }
        }

        //update times
        if (this.getGameState().isTimable())
        {
            this.RemainingSeconds--;
            if (this.RemainingSeconds == 0)
            {
                updateGameState();
            }
        }
        else
        {
            updateGameState();
        }

    }
}
