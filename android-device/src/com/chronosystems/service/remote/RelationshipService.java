package com.chronosystems.service.remote;

import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Relationship;

public class RelationshipService {

	private static String followURL = "/relationship/follow";

	/**
	 * add follow
	 * */
	public static Entity follow(final Relationship relationship){
		return WebServiceManager.executeRequest(followURL, relationship);
	}
}