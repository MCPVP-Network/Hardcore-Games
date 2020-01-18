package com.mcpvp.hardcoregames.game;

import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.HardcoreGamesSettings;
import com.mcpvp.hardcoregames.commons.CC;
import com.mcpvp.hardcoregames.commons.MathUtils;
import com.mcpvp.hardcoregames.customevents.GameStateChangeEvent;
import com.mcpvp.hardcoregames.feast.Feast;
import com.mcpvp.hardcoregames.playerdata.PlayerData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
    @Getter
    private Feast feast;

    private Player winnerPlayer;
    private int winnnerBroadcastCount = 5;

    public Game()
    {
        this.setGameState(GameState.WAITING_FOR_PLAYERS);

        start();
    }

    public boolean isInteractable()
    {
        return this.gameState == GameState.GRACE_PERIOD || this.gameState == GameState.LIVE;
    }


    public boolean checkWin()
    {
        return !HardcoreGamesSettings.DEV_MODE && this.getGameState() == GameState.LIVE && HardcoreGames.getPlayerManager().getAlivePlayerCount() == 1;
    }


    public void startBroadcast(int players)
    {
        Bukkit.broadcastMessage(CC.red + "The Tournament has begun!");
        Bukkit.broadcastMessage(CC.red + "There are " + players + " players participating.");
        Bukkit.broadcastMessage(CC.red + "Everyone is invincible for 2 minutes.");
        Bukkit.broadcastMessage(CC.red + "Good Luck!");
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
                else {
                    this.setGameState(GameState.GRACE_PERIOD);
                    this.start();
                }
                break;
            case GRACE_PERIOD:
                Bukkit.broadcastMessage(CC.red + "Invincibility wears off");
                Bukkit.broadcastMessage(CC.red + "You are no longer invincible.");
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
        if(this.winnerPlayer != null && this.winnnerBroadcastCount >= 0)
        {
            Bukkit.broadcastMessage(CC.red + this.winnerPlayer.getName() + " wins");
            this.winnnerBroadcastCount--;
            return;
        }

        if(this.checkWin())
        {
            this.setGameState(GameState.CHAMPION);
            for (PlayerData player : HardcoreGames.getPlayerManager().getPlayers())
            {
                if(player.isAlive())
                {
                    this.winnerPlayer = player.getPlayer();
                    break;
                }
            }
        }

        //logic
        if(this.getGameState() == GameState.GRACE_PERIOD && (this.getRemainingSeconds() == 120 ||
                                                             this.getRemainingSeconds() == 60 ||
                                                             this.getRemainingSeconds() % 30 == 0 ||
                                                             this.getRemainingSeconds() % 15 == 0 && this.getRemainingSeconds() <= 60 ||
                                                             this.getRemainingSeconds() <= 10 ))
        {
            Bukkit.broadcastMessage(CC.red + "Invincibility wears off in " + this.getRemainingSeconds() + " second" + (this.getRemainingSeconds() == 1 ? "" : "s"));
        }

        if(this.getGameState() == GameState.COUNTINGDOWN && (this.getRemainingSeconds() % 60  == 0 ||
                this.getRemainingSeconds() % 30 == 0 ||
                this.getRemainingSeconds() % 15 == 0 && this.getRemainingSeconds() <= 60 ||
                this.getRemainingSeconds() <= 10 ))
        {
            Bukkit.broadcastMessage(CC.red + "The Tournament will start in " + this.getRemainingSeconds() + " second" + (this.getRemainingSeconds() == 1 ? "" : "s"));
        }

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
