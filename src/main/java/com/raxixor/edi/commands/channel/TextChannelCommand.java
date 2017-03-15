package com.raxixor.edi.commands.channel;

import com.raxixor.edi.Bot;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;

/**
 * Created by raxix on 15/03/2017, 22:38.
 */
public class TextChannelCommand extends Command {
	
	public TextChannelCommand(Bot bot) {
		this.name = "tchannel";
		this.help = "manages text channels";
		this.guildOnly = true;
		this.ownerCommand = false;
		this.category = bot.MOD;
		this.arguments = "#channel | new | edit";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		
	}
	
}
