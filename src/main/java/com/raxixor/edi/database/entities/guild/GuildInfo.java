package com.raxixor.edi.database.entities.guild;

import com.sun.istack.internal.NotNull;

/**
 * Created by raxix on 18/03/2017, 17:24.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class GuildInfo {
	
	private String id;
	private String ownerId;
	private GreetInfo greetInfo;
	private ByeInfo byeInfo;
	private String botCommanderId;
	private String chatMuteId;
	
	/**
	 * 
	 * @param id guild ID
	 * @param ownerId owner ID
	 * @param greetInfo greeting information
	 * @param byeInfo bye information
	 * @param botCommanderId bot commander role ID
	 * @param chatMuteId chat mute role ID
	 */
	public GuildInfo(@NotNull String id, @NotNull String ownerId, @NotNull GreetInfo greetInfo, @NotNull ByeInfo byeInfo, String botCommanderId, String chatMuteId) {
		this.id = id;
		this.ownerId = ownerId;
		this.greetInfo = greetInfo;
		this.byeInfo = byeInfo;
		this.botCommanderId = botCommanderId;
		this.chatMuteId = chatMuteId;
	}
	
	public String getId() {
		return id;
	}
	
	public String getOwnerId() {
		return ownerId;
	}
	
	public GreetInfo getGreetInfo() {
		return greetInfo;
	}
	
	public ByeInfo getByeInfo() {
		return byeInfo;
	}
	
	public String getBotCommanderId() {
		return botCommanderId;
	}
	
	public String getChatMuteId() {
		return chatMuteId;
	}
	
	@Override
	public String toString() {
		return id;
	}
}
