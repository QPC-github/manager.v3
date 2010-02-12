package com.google.enterprise.connector.sp2c_migration;

import java.util.Set;

/**
 * Specification of the access permission on a {@link DirEntry} for a single
 * user or group. This class can contains both, actual SharePoint permission as
 * well as those permissions which are to be used in the cloud
 */
public class Ace {

	/** Enumerations for scope of an {@link Ace} */
	public static enum Type {
		USER, DOMAINGROUP, SPGROUP
	}

	/** Enumerations for the kind of permission to be used in cloud */
	public static enum GPermission {
		NA, PEEKER, READ, WRITE, FULLCONTROL
	}

	/**
	 * This represents the actual SharePoint permissions
	 *
	 * @author nitendra_thakur
	 */
	public static class SharepointPermissions {
		Set<String> allowedPermissions;
		Set<String> deniedPermissions;

		public SharepointPermissions(Set<String> grantRightsMask,
		    Set<String> denyRightsMask) {
			allowedPermissions = grantRightsMask;
			deniedPermissions = denyRightsMask;
		}

		public Set<String> getAllowedPermissions() {
			return allowedPermissions;
		}

		public Set<String> getDeniedPermissions() {
			return deniedPermissions;
		}
	}

	private final String name; // user or group name;
	private final SharepointPermissions spPermissions;
	private GPermission gPermission = GPermission.NA;
	private final Type type;

	public Ace(String name, SharepointPermissions permission, Type type) {
		this.name = name;
		this.spPermissions = permission;
		this.type = type;
	}

	public void setGPermission(GPermission gPermission) {
		this.gPermission = gPermission;
	}

	public String getName() {
		return name;
	}

	/**
	 * Returns the actual SharePoint permissions
	 *
	 * @return
	 */
	public SharepointPermissions getPermission() {
		return spPermissions;
	}

	/**
	 * Returns the permissions to be used in the cloud
	 *
	 * @return
	 */
	public GPermission getGPermission() {
		return gPermission;
	}

	public Type getType() {
		return type;
	}
}