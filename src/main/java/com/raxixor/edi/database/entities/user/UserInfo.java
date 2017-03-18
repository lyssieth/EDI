package com.raxixor.edi.database.entities.user;

import com.sun.istack.internal.NotNull;

/**
 * Created by raxix on 18/03/2017, 18:43.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class UserInfo {
	
	private String id;
	private boolean eval;
	
	public UserInfo(@NotNull String id, @NotNull boolean eval) {
		this.id = id;
		this.eval = eval;
	}
	
	public String getId() {
		return id;
	}
	
	public boolean getEval() {
		return eval;
	}
	
	@Override
	public String toString() {
		return id;
	}
}
