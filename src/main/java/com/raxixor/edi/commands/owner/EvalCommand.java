package com.raxixor.edi.commands.owner;

import com.raxixor.edi.Bot;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by raxix on 14/03/2017, 17:44.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class EvalCommand extends Command {
    
    public EvalCommand(Bot bot) {
        this.name = "eval";
        this.help = "evaluates Nashorn code";
        this.ownerCommand = true;
        this.guildOnly = false;
        this.category = bot.BOT_OWNER;
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
            if (event.isFromType(ChannelType.TEXT)) {
	            MessageEmbed embed = new EmbedBuilder()
			            .setAuthor(author.getName() + "#" + author.getDiscriminator(), null, author.getEffectiveAvatarUrl())
			            .setColor(event.getSelfMember().getColor())
			            .setDescription(event.getClient().getSuccess() + "Output: \n```\n" + se.eval(event.getArgs()) + "\n```")
			            .build();
	            event.reply(embed);
            } else {
	            MessageEmbed embed = new EmbedBuilder()
			            .setAuthor(author.getName() + "#" + author.getDiscriminator(), null, author.getEffectiveAvatarUrl())
			            .setColor(Color.GREEN)
			            .setDescription(event.getClient().getSuccess() + "Output: \n```\n" + se.eval(event.getArgs()) + "\n```")
			            .build();
	            event.reply(embed);
            }
        } catch (Exception e) {
            User author = event.getAuthor();
            if (event.isFromType(ChannelType.TEXT)) {
	            MessageEmbed embed = new EmbedBuilder()
			            .setAuthor(author.getName() + "#" + author.getDiscriminator(), null, author.getEffectiveAvatarUrl())
			            .setColor(event.getSelfMember().getColor())
			            .setDescription(event.getClient().getError() + " | An exception was thrown: \n```\n" + e.getMessage() + " \n```")
			            .build();
	            event.reply(embed);
            } else {
	            MessageEmbed embed = new EmbedBuilder()
			            .setAuthor(author.getName() + "#" + author.getDiscriminator(), null, author.getEffectiveAvatarUrl())
			            .setColor(Color.RED)
			            .setDescription(event.getClient().getError() + " | An exception was thrown: \n```\n" + e.getMessage() + " \n```")
			            .build();
	            event.reply(embed);
            }
            
        }
    }
}
