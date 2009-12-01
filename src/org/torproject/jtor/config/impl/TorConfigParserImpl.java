package org.torproject.jtor.config.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;

import org.torproject.jtor.TorConfig;

/**
 *
 * @author Merlijn Hofstra
 */
public class TorConfigParserImpl {

	private TorConfigParserImpl() {}

	public static boolean parseFile(TorConfig tc, File in) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(in));
			String line = null;
			while ((line=reader.readLine()) != null) {
				
				if (line.startsWith("#") || line.matches("^\\s*$")) { // skip comments and empty lines
					continue;
				}

				// strip comments after a setting
				int index = line.indexOf("#");
				if (index != -1 && !line.substring(index-1, index).equals("\\")) {
					line = line.substring(0, index-1);
				}

				//strip any trailing whitespace
				line = line.replaceAll("\\s*$", "");

				int separator = line.indexOf(" ");
				String key = line.substring(0, separator);
				String value = line.substring(separator+1);

				if (!setConf(tc, key, value)) {
					// syntax error in file
					System.err.println("torrc: Could not parse this line: " + line);
					return false;
				}

			}

		} catch (FileNotFoundException ex) {
			//no config file available
			return true;
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public static boolean setConf(TorConfig tc, String key, String value) {
		try {
			key = key.toLowerCase();

			if (key.equals("configfile")) {
				tc.setConfigFile(value);
			}

			else if (key.equals("datadirectory")) {
				tc.setDataDirectory(new File(value));
			}

			else if (key.equals("bandwidthrate")) {
				tc.setBandwidthRate(Long.parseLong(value));
			}
			
			else if (key.equals("bandwidthburst")) {
				tc.setBandwidthBurst(Long.parseLong(value));
			}
			
			else if (key.equals("maxadvertisedbandwidth")) {
				tc.setMaxAdvertisedBandwidth(Long.parseLong(value));
			}
			
			else if (key.equals("controlport")) {
				tc.setControlPort(Short.parseShort(value));
			}
			
			else if (key.equals("hashedcontrolpassword")) {
				tc.setHashedControlPassword(value);
			}
			
			else if (key.equals("cookieauthentication")) {
				tc.setCookieAuthentication(isTrue(value));
			}
			
			else if (key.equals("dirfetchperiod")) {
				tc.setDirFetchPeriod(Long.parseLong(value));
			}
			
			else if (key.equals("dirserver")) {
				String[] newar = addToStringArray(tc.getDirServer(), value);
				tc.setDirServer(newar);
			}
			
			else if (key.equals("disableallswap")) {
				tc.setDisableAllSwap(isTrue(value));
			}
			
			else if (key.equals("group")) {
				tc.setGroup(value);
			}
			
			else if (key.equals("httpproxy")) {
				tc.setHttpProxy(value);
			}
			
			else if (key.equals("httpproxyauthenticator")) {
				tc.setHttpProxyAuthenticator(value);
			}
			
			else if (key.equals("httpsproxy")) {
				tc.setHttpsProxy(value);
			}
			
			else if (key.equals("httpsproxyauthenticator")) {
				tc.setHttpsProxyAuthenticator(value);
			}
			
			else if (key.equals("keepaliveperiod")) {
				tc.setKeepalivePeriod(Integer.parseInt(value));
			}
			
			else if (key.equals("log")) {
				String[] newar = addToStringArray(tc.getLog(), value);
				tc.setLog(newar);
			}
			
			else if (key.equals("maxconn")) {
				tc.setMaxConn(Integer.parseInt(value));
			}
			
			else if (key.equals("outboundbindaddress")) {
				tc.setOutboundBindAddress(InetAddress.getByName(value));
			}
			
			else if (key.equals("pidfile")) {
				tc.setPidFile(value);
			}
			
			else if (key.equals("runasdaemon")) {
				tc.setRunAsDaemon(isTrue(value));
			}
			
			else if (key.equals("safelogging")) {
				tc.setSafeLogging(isTrue(value));
			}
			
			else if (key.equals("statusfetchperiod")) {
				tc.setStatusFetchPeriod(Long.parseLong(value));
			}
			
			else if (key.equals("user")) {
				tc.setUser(value);
			}
			
			else if (key.equals("hardwareaccel")) {
				tc.setHardwareAccel(isTrue(value));
			}
			
			else if (key.equals("allowunverifiednodes")) {
				tc.setAllowUnverifiedNodes(value);
			}
			
			else if (key.equals("clientonly")) {
				tc.setClientOnly(isTrue(value));
			}
			
			else if (key.equals("entrynodes")) {
				String[] newar = addToStringArray(tc.getEntryNodes(), value);
				tc.setEntryNodes(newar);
			}
			
			else if (key.equals("exitnodes")) {
				String[] newar = addToStringArray(tc.getExitNodes(), value);
				tc.setExitNodes(newar);
			}
			
			else if (key.equals("excludenodes")) {
				String[] newar = addToStringArray(tc.getExcludeNodes(), value);
				tc.setExcludeNodes(newar);
			}
			
			else if (key.equals("strictexitnodes")) {
				tc.setStrictExitNodes(isTrue(value));
			}
			
			else if (key.equals("strictentrynodes")) {
				tc.setStrictEntryNodes(isTrue(value));
			}
			
			else if (key.equals("fascistfirewall")) {
				tc.setFascistFirewall(isTrue(value));
			}
			
			else if (key.equals("firewallports")) { // needs splitting TODO
				short[] newar = addToShortArray(tc.getFirewallPorts(), Short.parseShort(value));
				tc.setFirewallPorts(newar);
			}
			
			else if (key.equals("firewallips")) {
				String[] newar = addToStringArray(tc.getFirewallIPs(), value);
				tc.setExcludeNodes(newar);
			}
			
			else if (key.equals("longlivedports")) { // needs splitting TODO
				short[] newar = addToShortArray(tc.getLongLivedPorts(), Short.parseShort(value));
				tc.setLongLivedPorts(newar);
			}
			
			else if (key.equals("mapaddress")) {
				String[] newar = addToStringArray(tc.getMapAddress(), value);
				tc.setMapAddress(newar);
			}
			
			else if (key.equals("newcircuitperiod")) {
				tc.setNewCircuitPeriod(Long.parseLong(value));
			}
			
			else if (key.equals("maxcircuitdirtiness")) {
				tc.setMaxCircuitDirtiness(Long.parseLong(value));
			}
			
			else if (key.equals("nodefamily")) {
				String[] newar = addToStringArray(tc.getNodeFamily(), value);
				tc.setNodeFamily(newar);
			}
			
			else if (key.equals("rendnodes")) {
				String[] newar = addToStringArray(tc.getRendNodes(), value);
				tc.setRendNodes(newar);
			}
			
			else if (key.equals("rendexcludenodes")) {
				String[] newar = addToStringArray(tc.getRendExcludeNodes(), value);
				tc.setRendExcludeNodes(newar);
			}
			
			else if (key.equals("socksport")) {
				tc.setSocksPort(Short.parseShort(value));
			}
			
			else if (key.equals("socksbindaddress")) {
				tc.setSocksBindAddress(value);
			}
			
			else if (key.equals("sockslistenaddress")) {
				tc.setSocksBindAddress(value);
			}
			
			else if (key.equals("sockspolicy")) {
				tc.setSocksPolicy(value);
			}
			
			else if (key.equals("trackhostexits")) {
				String[] newar = addToStringArray(tc.getTrackHostExits(), value);
				tc.setTrackHostExits(newar);
			}
			
			else if (key.equals("trackhostexitsexpire")) {
				tc.setTrackHostExitsExpire(Long.parseLong(value));
			}
			
			else if (key.equals("usehelpernodes")) {
				tc.setUseHelperNodes(isTrue(value));
			}
			
			else if (key.equals("numhelpernodes")) {
				tc.setNumHelperNodes(Integer.parseInt(value));
			}
			
			else if (key.equals("hiddenservicedir")) {
				String[] newar = addToStringArray(tc.getHiddenServiceDir(), value);
				tc.setHiddenServiceDir(newar);
			}
			
			else if (key.equals("hiddenserviceport")) { // needs splitting?
				String[] newar = addToStringArray(tc.getHiddenServicePort(), value);
				tc.setHiddenServicePort(newar);
			}
			
			else if (key.equals("hiddenservicenodes")) {
				String[] newar = addToStringArray(tc.getHiddenServiceNodes(), value);
				tc.setHiddenServiceNodes(newar);
			}
			
			else if (key.equals("hiddenserviceexcludenodes")) {
				String[] newar = addToStringArray(tc.getHiddenServiceExcludeNodes(), value);
				tc.setHiddenServiceExcludeNodes(newar);
			}
			
			else if (key.equals("hiddenserviceversion")) {
				tc.setHiddenServiceVersion(value);
			}
			
			else if (key.equals("rendpostperiod")) {
				tc.setRendPostPeriod(Long.parseLong(value));
			}

			else { // key was not found
				return false;
			}
			
		} catch(Throwable t) { // if any errors occur, the operation failed.
			return false;
		}

		return true;
	}

	public static String[] addToStringArray(String[] ar, String val) {
		if (ar == null) {
			String[] ret = new String[1];
			ret[0] = val;
			return ret;
		}

		String[] ret = new String[ar.length+1];
		System.arraycopy(ar, 0, ret, 0, ar.length);
		ret[ar.length] = val;
		return ret;
	}

	public static short[] addToShortArray(short[] ar, short val) {
		if (ar == null) {
			short[] ret = new short[1];
			ret[0] = val;
			return ret;
		}

		short[] ret = new short[ar.length+1];
		System.arraycopy(ar, 0, ret, 0, ar.length);
		ret[ar.length] = val;
		return ret;
	}
	
	public static boolean isTrue(String in) {
		return !(in.equals("0") || in.equals("false"));
	}

}
