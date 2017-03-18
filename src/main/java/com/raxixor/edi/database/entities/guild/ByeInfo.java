package com.raxixor.edi.database.entities.guild;

import com.sun.istack.internal.NotNull;

/**
 * Created by raxix on 18/03/2017, 17:31.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class ByeInfo {
	
	private boolean byeEnabled;
	private String byeId;
	private String byeMsg;
	
	/**
	 * 
	 * @param byeEnabled are bye messages enabled?
	 * @param byeId bye channel ID
	 * @param byeMsg bye message
	 */
	public ByeInfo(@NotNull boolean byeEnabled, String byeId, @NotNull String byeMsg) {
		this.byeEnabled = byeEnabled;
		this.byeId = byeId;
		this.byeMsg = byeMsg;
	}
	
	public boolean getByeEnabled() {
		return byeEnabled;
	}
	
	public String getByeId() {
		return byeId;
	}
	
	public String getByeMsg() {
		return byeMsg;
	}
	
	@Override
	public String toString() {
		return byeMsg;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (o == this) return true;
		if (o == null) return false;
		if (o.getClass() != this.getClass()) return false;
		final ByeInfo other = (ByeInfo)o;
		if (this.byeEnabled != other.byeEnabled) return false;
		if (this.byeId != other.byeId) return false;
		if (this.byeMsg != other.byeMsg) return false;
		return true;
	}
}
