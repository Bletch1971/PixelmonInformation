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

	public static final String MOD_UPDATE_URL="https://raw.githubusercontent.com/Bletch1971/PixelmonInformation/master/9.0/updateforge.json";

	public static final Logger MOD_LOGGER = LogManager.getLogger(MOD_NAME);

	public static final String MOD_ID_MINECRAFT = "minecraft";
	public static final String MOD_ID_FORGE = "forge";
	public static final String MOD_ID_PIXELMON = "pixelmon";
	public static final String MOD_ID_JEI = "jei";
	public static final String MOD_ID_WAILA = "waila";
	public static final String MOD_ID_TOP = "theoneprobe";
}
