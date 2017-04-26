package com.raxixor.edi.commands.owner;

import com.raxixor.edi.Bot;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;

/**
 * Created by raxix on 16/03/2017, 18:32.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class EchoCommand extends Command {
	
	public EchoCommand(Bot bot) {
		this.name = "echo";
		this.arguments = "message";
		this.help = "echoes back the message";
		this.ownerCommand = true;
		this.guildOnly = false;
		this.category = bot.BOT_OWNER;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		event.getMessage().delete().queue();
		event.reply(event.getArgs());
	}
}
