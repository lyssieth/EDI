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
	
	public static GuildInfo getGuildInfo(String id) throws SQLException {
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
	
	public static void addGuild(GuildInfo info) throws SQLException {
		Connection conn = DatabaseUtil.connect();
		String greetId;
		String greetMsg;
		String byeId;
		String byeMsg;
		String botCommanderId;
		String chatMuteId;
		if (info.getGreetInfo().getGreetId() != null) {
			greetId = "'" + info.getGreetInfo().getGreetId() + "'";
		} else greetId = info.getGreetInfo().getGreetId();
		if (info.getGreetInfo().getGreetMsg() != null) {
			greetMsg = "'" + info.getGreetInfo().getGreetMsg() + "'";
		} else greetMsg = info.getGreetInfo().getGreetMsg();
		if (info.getByeInfo().getByeId() != null) {
			byeId = "'" + info.getByeInfo().getByeId() + "'";
		} else byeId = info.getByeInfo().getByeId();
		if (info.getByeInfo().getByeMsg() != null) {
			byeMsg = "'" + info.getByeInfo().getByeMsg() + "'";
		} else byeMsg = info.getByeInfo().getByeMsg();
		if (info.getBotCommanderId() != null) {
			botCommanderId = "'" + info.getBotCommanderId() + "'";
		} else botCommanderId = info.getBotCommanderId();
		if (info.getChatMuteId() != null) {
			chatMuteId = "'" + info.getChatMuteId() + "'";
		} else chatMuteId = info.getChatMuteId();
		
		String sql = "INSERT INTO guilds VALUES ('" +
				info.getId() + "', '" + info.getOwnerId() + "', " + info.getGreetInfo().getGreetEnabled() + ", " +
				greetId + ", " + greetMsg + ", " + info.getByeInfo().getByeEnabled() + ", " + byeId + ", " + byeMsg + 
				", " + botCommanderId + ", " + chatMuteId + ")";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		conn.close();
	}
	
	public static void removeGuild(String id) throws SQLException {
		Connection conn = DatabaseUtil.connect();
		String sql = "DELETE FROM guilds WHERE id = '" + id + "'";
		
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		conn.close();
	}
	
	public static boolean guildExists(String id) throws SQLException {
		Connection conn = DatabaseUtil.connect();
		String sql = "SELECT id FROM guilds WHERe id = '" + id + "'";
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		conn.close();
		
		if (rs.next()) return true;
		return false;
	}
}
