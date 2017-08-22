package com.raxixor.edi.commands.channel;

import com.raxixor.edi.Bot;
import com.sun.istack.internal.Nullable;
import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.Permission;

/**
 * Created by raxix on 04/04/2017, 01:19.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class VoiceChannelCommand extends Command {
	
	public VoiceChannelCommand(Bot bot) {
		this.name = "vchannel";
		this.help = "does stuff with VoiceChannels";
		this.arguments = "new <name>";
		this.category = bot.MOD;
		this.botPermissions = new Permission[] {Permission.MANAGE_CHANNEL};
		this.ownerCommand = false;
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (!(event.getArgs().toLowerCase().startsWith("new"))) {
			event.reply(event.getClient().getError() + " | No valid arguments provided!");
			return;
		}
		if (event.getArgs().toLowerCase().startsWith("new") && event.getArgs().length() < 4) {
			event.reply(event.getClient().getError() + " | Missing name for new channel.");
			return;
		}
		
		if (event.getArgs().toLowerCase().startsWith("new")) {
			executeNew(event.getArgs().substring(4), event, null);
		}
	}
	
	/**
	 * Executes the creation of a new channel.
	 * 
	 * @param name Name for the new channel.
	 * @param event The CommandEvent from the command execution.
	 * @param extra Extra Information.
	 */
	protected void executeNew(String name, CommandEvent event, @Nullable String extra) {
		event.getGuild().getController().createVoiceChannel(name).queue(v -> {
			event.reply(event.getClient().getSuccess() + " | Successfully created voice channel `" + name + "`!");
		}, t -> {
			event.reply(event.getClient().getError() + " | I failed to create a voice channel named `" + name + "`!\n" +
					t.getMessage());
		});
	}
}
