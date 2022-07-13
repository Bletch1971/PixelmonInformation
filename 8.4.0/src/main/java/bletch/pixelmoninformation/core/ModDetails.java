package bletch.pixelmoninformation.core;

import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ParametersAreNonnullByDefault
public class ModDetails {
	private static final int VersionMajor = 1;
	private static final int VersionMinor = 0;
	private static final int VersionRevision = 0;

	public static final String MOD_ID = "pixelmoninformation";
	public static final String MOD_NAME = "PixelmonInformation";

	public static final String MOD_VERSION = VersionMajor + "." + VersionMinor + "." + VersionRevision;
	public static final String MOD_DEPENDENCIES = "required-after:minecraft@[1.12.2];required-after:forge@[14.23.5.2847,);required-after:pixelmon@[8.3.0,);after:jei;after:waila;after:theoneprobe;after:jeresources";

	public static final String MOD_UPDATE_URL="https://raw.githubusercontent.com/Bletch1971/PixelmonInformation/master/8.3/updateforge.json";
			
	public static final String MOD_SERVER_PROXY_CLASS = "bletch.pixelmoninformation.core.ModCommonProxy";
	public static final String MOD_CLIENT_PROXY_CLASS = "bletch.pixelmoninformation.core.ModClientProxy";
	
	public static final Logger MOD_LOGGER = LogManager.getLogger(MOD_NAME);

	public static final String MOD_ID_MINECRAFT = "minecraft";
	public static final String MOD_ID_FORGE = "forge";
	public static final String MOD_ID_PIXELMON = "pixelmon";
	public static final String MOD_ID_JEI = "jei";
	public static final String MOD_ID_WAILA = "waila";
	public static final String MOD_ID_TOP = "theoneprobe";
	public static final String MOD_ID_JER = "jeresources";
	
	public static final String PATH_DEBUG = "/debug";
	public static final String FILE_DEBUGLOG = PATH_DEBUG + "/" + MOD_ID + ".txt"; 
}
