package com.raxixor.edi.events;

import com.raxixor.edi.database.GuildDatabase;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.sql.SQLException;

/**
 * Created by raxix on 04/04/2017, 00:15.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class GuildLeaveListener extends ListenerAdapter {
	
	@Override
	public void onGuildLeave(GuildLeaveEvent event) {
		JDA jda = event.getJDA();
		long responseNumber = event.getResponseNumber();
		
		Guild guild = event.getGuild();
		
		try {
			if (GuildDatabase.guildExists(guild.getId())) {
				GuildDatabase.removeGuild(guild.getId());
				SimpleLog.getLog("Database").info("Removed Guild " + guild.getName() + " (" + guild.getId() + ") from database.");
			} else {
				SimpleLog.getLog("Database").warn("Could not remove Guild " + guild.getName() + " (" + guild.getId() + ") from the database.");
			}
		} catch (SQLException e) {
			SimpleLog.getLog("Database").warn(e);
		}
	}
	
}
