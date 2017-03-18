package com.raxixor.edi.events;

import com.raxixor.edi.database.UserDatabase;
import com.raxixor.edi.database.entities.user.UserInfo;
import net.dv8tion.jda.core.JDA;
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
		
		for (User user : jda.getUsers()) {
			try {
				if (UserDatabase.userExists(user.getId())) continue;
				UserInfo info = new UserInfo(user.getId(), false);
				UserDatabase.addUser(info);
			} catch (SQLException e) {
				SimpleLog.getLog("Database").warn(e);
			}
		}
		
		SimpleLog.getLog("Ready").info("Ready.");
	}
	
}
