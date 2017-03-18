package com.raxixor.edi.events;

import com.raxixor.edi.Constants;
import com.raxixor.edi.database.GuildDatabase;
import com.raxixor.edi.database.entities.guild.ByeInfo;
import com.raxixor.edi.database.entities.guild.GreetInfo;
import com.raxixor.edi.database.entities.guild.GuildInfo;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.guild.GuildAvailableEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.sql.SQLException;

/**
 * Created by raxix on 18/03/2017, 18:48.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class GuildAvailableListener extends ListenerAdapter {
	
	@Override
	public void onGuildAvailable(GuildAvailableEvent event) {
		JDA jda = event.getJDA();
		long responseNumber = event.getResponseNumber();
		
		Guild guild = event.getGuild();
		
		GuildInfo info = new GuildInfo(
				guild.getId(),
				guild.getOwner().getUser().getId(),
				new GreetInfo(false, null, Constants.DEFAULT_GREET_MSG),
				new ByeInfo(false, null, Constants.DEFAULT_BYE_MSG),
				null,
				null
		);
		
		try {
			if (GuildDatabase.guildExists(guild.getId())) return;
			GuildDatabase.addGuild(info);
		} catch (SQLException e) {
			SimpleLog.getLog("Database").warn(e);
		}
	}
	
}
