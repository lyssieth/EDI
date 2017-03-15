package com.raxixor.edi.commands.owner;

import com.raxixor.edi.Bot;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;

/**
 * Created by raxix on 15/03/2017, 22:12.
 */
public class SetGameCommand extends Command {
	
	public SetGameCommand(Bot bot) {
		this.name = "setgame";
		this.help = "sets the bot's game";
		this.arguments = "game";
		this.ownerCommand = true;
		this.guildOnly = false;
		this.category = bot.BOT_OWNER;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		JDA jda = event.getJDA();
		
		jda.getPresence().setGame(Game.of(event.getArgs()));
	}
}
