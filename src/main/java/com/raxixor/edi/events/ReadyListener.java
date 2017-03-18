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
		
		jda.getUserById("145399954574147584").openPrivateChannel().queue(v -> {
			SimpleLog.getLog("PrivChan").info("Opened private channel with owner.");
		});
		
		SimpleLog.getLog("Ready").info("Ready.");
	}
	
}
