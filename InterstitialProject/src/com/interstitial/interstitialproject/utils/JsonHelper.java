package com.interstitial.interstitialproject.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.interstitial.interstitialproject.dao.Offer;
import com.interstitial.interstitialproject.dao.SdkNetwork;

/**
 * The Class JsonHelper.
 */
public class JsonHelper {
	
	/**
	 * Gets the sdk list.
	 *
	 * @param json the json
	 * @return the sdk list
	 */
	
	private static JSONParser parser = new JSONParser();
	
	/**
	 * Gets the sdk list.
	 *
	 * @param json the json
	 * @return the sdk list
	 */
	public static List<SdkNetwork> getSdkList(String json){
		
		List<SdkNetwork> sdkList = new ArrayList<SdkNetwork>();
		Object obj;
		try {
			obj = parser.parse(json);
			JSONObject jsonObj = (JSONObject) obj;
			
			JSONArray ja = (JSONArray) jsonObj.get("sdk");
			for (Object object : ja) {
				SdkNetwork sdk = new SdkNetwork();
				sdk.setName(((JSONObject)object).get("name").toString());
				sdk.setParam1(((JSONObject)object).get("param1").toString());
				sdk.setParam2(((JSONObject)object).get("param2").toString());
				sdk.setParam3(((JSONObject)object).get("param3").toString());
				sdk.setPriority(Integer.parseInt(
						((JSONObject)object).get("priority").toString()));
				
				sdkList.add(sdk);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return sdkList;
	}

	/**
	 * Gets the offers.
	 *
	 * @param json the json
	 * @return the offers
	 */
	public static List<Offer> getOffers(String json) {
		List<Offer> offers = new ArrayList<Offer>();
		Object obj;
		try {
			obj = parser.parse(json);
			JSONObject jsonObj = (JSONObject) obj;
			
			JSONObject extra = (JSONObject) jsonObj.get("internaloptions");
			
			JSONArray ja = (JSONArray) extra.get("offers");
			for (Object object : ja) {
				Offer offer = new Offer();
//				sdk.setName(((JSONObject)object).get("name").toString());
				offer.setType(((JSONObject)object).get("type").toString());
				
				
				if (offer.getType().equals("app")){
					HashSet<String> packages = new HashSet<String>();
					JSONArray options =(JSONArray) ((JSONObject)object).get("options");
					for (Object opt : options) {
						packages.add(((JSONObject)opt).get("packagename").toString());
					}
					offer.setPackages(packages);
				}
				offer.setOfferId(Integer.parseInt(((JSONObject)object).get("offer_id").toString()));
				offers.add(offer);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return offers;
	}
	
	/**
	 * Gets the checks if is show only first sdk.
	 *
	 * @param json the json
	 * @return the checks if is show only first sdk
	 */
	public static boolean getIsShowOnlyFirst(String json) {
		
		Object obj;
		String showLogic = "all";
		try {
			obj = parser.parse(json);
			JSONObject jsonObj = (JSONObject) obj;
			
			JSONObject extra = (JSONObject) jsonObj.get("extra");
			showLogic = ((JSONObject) extra).get("showlogic").toString();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return showLogic.equals("first");
	}
	
	
	public static String getTitle(String json){
		Object obj;
		String title = "all";
		try {
			obj = parser.parse(json);
			JSONObject jsonObj = (JSONObject) obj;
			
			JSONObject extra = (JSONObject) jsonObj.get("internaloptions");
			title = ((JSONObject) extra).get("titletext").toString();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return title;
	}

	public static int getButtonUnLockDelay(String json) {
		Object obj;
		int delay = 1000;
		try {
			obj = parser.parse(json);
			JSONObject jsonObj = (JSONObject) obj;
			
			JSONObject extra = (JSONObject) jsonObj.get("internaloptions");
			delay = Integer.parseInt(
					((JSONObject) extra).get("screendelay").toString());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return delay;
		
	}

	public static boolean getIncent(String json) {
		Object obj;
		String isIncent = "false";
		try {
			obj = parser.parse(json);
			JSONObject jsonObj = (JSONObject) obj;
			
			JSONObject extra = (JSONObject) jsonObj.get("internaloptions");
			isIncent = 	((JSONObject) extra).get("incent").toString();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isIncent.equals("true");
	}

	public static boolean checkLastStep(String json) {
		Object obj;
		String showLogic = "all";
		try {
			obj = parser.parse(json);
			JSONObject jsonObj = (JSONObject) obj;
			
			JSONObject extra = (JSONObject) jsonObj.get("extra");
			showLogic = ((JSONObject) extra).get("laststep").toString();
			
		} catch (ParseException e) {
		}
		catch (Exception e) {
		}
		
		return showLogic.equals("true");
	}
}
