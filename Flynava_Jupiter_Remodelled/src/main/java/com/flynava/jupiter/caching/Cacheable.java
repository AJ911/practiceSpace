package com.flynava.jupiter.caching;

public interface Cacheable {
	
	public boolean isExpired();
	public Object getIdentifier();
	

}
