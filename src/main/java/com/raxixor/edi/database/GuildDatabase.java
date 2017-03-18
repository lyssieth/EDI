package com.raxixor.edi.database;

import com.raxixor.edi.database.entities.guild.ByeInfo;
import com.raxixor.edi.database.entities.guild.GreetInfo;
import com.raxixor.edi.database.entities.guild.GuildInfo;
import net.dv8tion.jda.core.JDA;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by raxix on 18/03/2017, 17:19.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class GuildDatabase {
	
	public static int getGuildCount() throws SQLException {
		Connection conn = DatabaseUtil.connect();
		String sql = "SELECT count(*) FROM guilds";
		int count = 0;
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		conn.close();
		return rs.getInt(1);
	}
	
	public static GuildInfo getGuildInfo(JDA jda, String id) throws SQLException {
		Connection conn = DatabaseUtil.connect();
		String sql = "SELECT * FROM guilds WHERE id = '" + id + "'";
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		conn.close();
		return new GuildInfo(
				rs.getString("id"),
				rs.getString("owner_id"),
				new GreetInfo(
						rs.getBoolean("greet_enabled"),
						rs.getString("greet_id"),
						rs.getString("greet_msg")
				),
				new ByeInfo(
						rs.getBoolean("bye_enabled"),
						rs.getString("bye_id"),
						rs.getString("bye_msg")
				),
				rs.getString("botcommander_id"),
				rs.getString("chatmute_id")
			);
	}
}
