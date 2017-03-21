package com.raxixor.edi.events;

import com.raxixor.edi.Constants;
import com.raxixor.edi.database.GuildDatabase;
import com.raxixor.edi.database.UserDatabase;
import com.raxixor.edi.database.entities.guild.ByeInfo;
import com.raxixor.edi.database.entities.guild.GreetInfo;
import com.raxixor.edi.database.entities.guild.GuildInfo;
import com.raxixor.edi.database.entities.user.UserInfo;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by raxix on 16/03/2017, 13:40.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class ReadyListener extends ListenerAdapter {
	
	@Override
	public void onReady(ReadyEvent event) {
		JDA jda = event.getJDA();
		long responseNumber = event.getResponseNumber();
		
		jda.getUserById("145399954574147584").openPrivateChannel().queue(v -> {
			SimpleLog.getLog("PrivChan").info("Opened private channel with owner.");
		});
		
		for (Guild guild : jda.getGuilds()) {
			try {
				GuildInfo info = new GuildInfo(
						guild.getId(),
						guild.getOwner().getUser().getId(),
						new GreetInfo(false, null, Constants.DEFAULT_GREET_MSG),
						new ByeInfo(false, null, Constants.DEFAULT_BYE_MSG),
						null,
						null
				);
				
				if (GuildDatabase.guildExists(guild.getId())) continue;
				GuildDatabase.addGuild(info);
				SimpleLog.getLog("Database").info("Added Guild " + guild.getName() + " (" + guild.getId() + ") to database.");
			} catch (SQLException e) {
				SimpleLog.getLog("Database").warn(e);
			}
		}
		
		SimpleLog.getLog("Ready").info("Ready.");
	}
	
}
