package com.raxixor.edi.database.entities.guild;

import com.sun.istack.internal.NotNull;

/**
 * Created by raxix on 18/03/2017, 17:25.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class GreetInfo {
	
	private boolean greetEnabled;
	private String greetId;
	private String greetMsg;
	
	/**
	 * 
	 * @param greetEnabled greet messages enabled?
	 * @param greetId greet channel ID
	 * @param greetMsg greet message
	 */
	public GreetInfo(@NotNull boolean greetEnabled, String greetId, @NotNull String greetMsg) {
		this.greetEnabled = greetEnabled;
		this.greetId = greetId;
		this.greetMsg = greetMsg;
	}
	
	public boolean getGreetEnabled() {
		return greetEnabled;
	}
	
	public String getGreetId() {
		return greetId;
	}
	
	public String getGreetMsg() {
		return greetMsg;
	}
	
	@Override
	public String toString() {
		return greetMsg;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (o == this) return true;
		if (o == null) return false;
		if (o.getClass() != this.getClass()) return false;
		final GreetInfo other = (GreetInfo)o;
		if (this.greetEnabled != other.greetEnabled) return false;
		if (this.greetId != other.greetId) return false;
		if (this.greetMsg != other.greetMsg) return false;
		return true;
	}
}
