package com.ronhe.romp.cache.provider.ehcache;

import java.io.InputStream;
import java.net.URL;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;

/**
 * EhCachePlugin.
 * @author  
 */
public class EhCachePlugin{
	
	private static CacheManager cacheManager;
	private String configurationFileName;
	private URL configurationFileURL;
	private InputStream inputStream;
	private Configuration configuration;
	
	public EhCachePlugin() {
		
	}
	
	public EhCachePlugin(CacheManager cacheManager) {
		EhCachePlugin.cacheManager = cacheManager;
		
	}
	
	public EhCachePlugin(String configurationFileName) {
		this.configurationFileName = configurationFileName; 
	}
	
	public EhCachePlugin(URL configurationFileURL) {
		this.configurationFileURL = configurationFileURL;
	}
	
	public EhCachePlugin(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public EhCachePlugin(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public boolean start() {
		createCacheManager();
		CacheKit.init(cacheManager);
		return true;
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}

	private void createCacheManager() {
		 
		if (cacheManager != null)
			return ;
		
		if (configurationFileName != null) {
			cacheManager = CacheManager.create(configurationFileName);
			return ;
		}
		
		if (configurationFileURL != null) {
			cacheManager = CacheManager.create(configurationFileURL);
			return ;
		}
		
		if (inputStream != null) {
			cacheManager = CacheManager.create(inputStream);
			return ;
		}
		
		if (configuration != null) {
			cacheManager = CacheManager.create(configuration);
			return ;
		}
		
		cacheManager = CacheManager.create();
	}
	
	public boolean stop() {
		cacheManager.shutdown();
		return true;
	}
}