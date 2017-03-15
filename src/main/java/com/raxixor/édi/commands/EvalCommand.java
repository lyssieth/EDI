package com.raxixor.edinet.commands;

import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by raxix on 14/03/2017, 17:44.
 */
public class EvalCommand extends Command {
    
    public EvalCommand() {
        this.name = "eval";
        this.help = "evaluates nashorn code";
        this.ownerCommand = true;
        this.guildOnly = false;
    }
    
    @Override
    protected void execute(CommandEvent event) {
        ScriptEngine se = new ScriptEngineManager().getEngineByName("Nashorn");
        se.put("event", event);
        se.put("jda", event.getJDA());
        se.put("guild", event.getGuild());
        se.put("channel", event.getChannel());
        se.put("message", event.getMessage());
        se.put("random", new Random());
        se.put("thread", ThreadLocalRandom.current());
        try {
            User author = event.getAuthor();
            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor(author.getName() + "#" + author.getDiscriminator(), null, author.getEffectiveAvatarUrl())
                    .setColor(event.getSelfMember().getColor())
                    .setDescription(event.getClient().getSuccess() + "Output: \n```\n" + se.eval(event.getArgs()) + "\n```")
                    .build();
            event.reply(embed);
        } catch (Exception e) {
            User author = event.getAuthor();
            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor(author.getName() + "#" + author.getDiscriminator(), null, author.getEffectiveAvatarUrl())
                    .setColor(event.getSelfMember().getColor())
                    .setDescription(event.getClient().getError() + " | An exception was thrown: \n```\n" + e.getMessage() + " \n```")
                    .build();
            event.reply(embed);
        }
    }
}
