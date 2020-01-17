package com.mcpvp.hardcoregames.commons;

@FunctionalInterface
public interface Callback<T>
{
	public abstract void done(T paramT);
}