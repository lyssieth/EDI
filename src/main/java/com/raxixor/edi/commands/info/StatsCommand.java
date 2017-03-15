package com.raxixor.edi.commands.info;

import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by raxix on 14/03/2017, 19:06.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class StatsCommand extends Command {
    
    private final OffsetDateTime start = OffsetDateTime.now();
    public StatsCommand() {
        this.name = "stats";
        this.help = "shows some statistics about the bot";
        this.ownerCommand = true;
        this.guildOnly = false;
    }
    
    @Override
    protected void execute(CommandEvent event) {
        long totalMb = Runtime.getRuntime().totalMemory() / (1024 * 1024);
        long usedMb = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
        EmbedBuilder embed = new EmbedBuilder();
        embed.addField("Last Startup", start.format(DateTimeFormatter.RFC_1123_DATE_TIME), true);
        embed.addField("Guilds", String.valueOf(event.getJDA().getGuilds().size()), true);
        embed.addField("Memory", usedMb + "Mb / " + totalMb + "Mb", true);
        embed.addField("Response Total", String.valueOf(event.getJDA().getResponseTotal()), true);
        event.reply(embed.build());
    }
}
