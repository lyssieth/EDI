package com.raxixor.edi;

import com.raxixor.edi.commands.EvalCommand;
import com.raxixor.edi.commands.admin.BanCommand;
import com.raxixor.edi.commands.admin.CleanCommand;
import com.raxixor.edi.commands.admin.KickCommand;
import com.raxixor.edi.commands.admin.MagnetCommand;
import com.raxixor.edi.commands.info.RoleInfoCommand;
import com.raxixor.edi.commands.info.StatsCommand;
import com.raxixor.edi.commands.info.UserInfoCommand;
import com.raxixor.edi.commands.owner.GivePermCommand;
import me.jagrosh.jdautilities.commandclient.CommandClient;
import me.jagrosh.jdautilities.commandclient.CommandClientBuilder;
import me.jagrosh.jdautilities.commandclient.examples.PingCommand;
import me.jagrosh.jdautilities.commandclient.examples.ShutdownCommand;
import me.jagrosh.jdautilities.waiter.EventWaiter;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.*;
import net.dv8tion.jda.core.utils.SimpleLog;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by raxix on 14/03/2017, 11:02.
 * @author raxix <raxixor@gmail.com>
 */
public class EDI {

    /**
     * The main function.
     * @param args Commandline arguments.
     */
    public static void main(String[] args) {
        try {
            List<String> list = Files.readAllLines(Paths.get("config.txt"));
            
            String token = list.get(0);
            
            EventWaiter waiter = new EventWaiter();

            CommandClient client = new CommandClientBuilder()
                    .useDefaultGame()
                    .setOwnerId(Constants.OWNER_ID)
                    .setPrefix(Constants.PREFIX)
                    .setEmojis(Constants.SUCCESS, Constants.WARNING, Constants.ERROR)
                    .addCommands(
                            new PingCommand(),
                            new ShutdownCommand(),
                            new EvalCommand(),
                            new KickCommand(),
                            new BanCommand(),
                            new CleanCommand(waiter),
                            new StatsCommand(),
                            new UserInfoCommand(),
                            new RoleInfoCommand(),
                            new MagnetCommand(waiter),
                            new GivePermCommand()
                    ).build();
            new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .setStatus(OnlineStatus.DO_NOT_DISTURB)
                    .setGame(Game.of("Loading..."))
                    .addListener(waiter)
                    .addListener(client)
                    .buildAsync();
        } catch (IOException | LoginException | IllegalArgumentException | RateLimitedException e) {
            SimpleLog.getLog("Startup").fatal(e);
        }
    }
}
