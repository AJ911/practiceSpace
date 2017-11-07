package com.flynava.jupiter.caching;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

public class CacheManager {
	
	private static final Logger logger = Logger.getLogger(CacheManager.class);
	
	/* This is the HashMap that contains all objects in the cache. */
	private static HashMap cacheHashMap = new HashMap();
	
	/* This object acts as a semaphore, which protects the HashMap */
	  /* RESERVED FOR FUTURE USE  private static Object lock = new Object(); */
	    static
	    {
	        try
	        {
	            /* Create background thread, which will be responsible for
	purging expired items. */
	            Thread threadCleanerUpper = new Thread(
	            new Runnable()
	            {
	              /*  The default time the thread should sleep between scans.
	                  The sleep method takes in a millisecond value so 5000 = 5
	Seconds.
	              */
	              int milliSecondSleepTime = 5000;
	              public void run()
	              {
	                try
	                {
	                  /* Sets up an infinite loop.  The thread will continue
	looping forever. */
	                  while (true)
	                  {
	                    //System.out.println("ThreadCleanerUpper Scanning For Expired Objects...");
	                    /* Get the set of all keys that are in cache.  These are
	the unique identifiers */
	                    Set keySet = cacheHashMap.keySet();
	                    /* An iterator is used to move through the Keyset */
	                    Iterator keys = keySet.iterator();
	                    /* Sets up a loop that will iterate through each key in
	the KeySet */
	                    while(keys.hasNext())
	                    {
	                      /* Get the individual key.  We need to hold on to this
	key in case it needs to be removed */
	                      Object key = keys.next();
	                      /* Get the cacheable object associated with the key
	inside the cache */
	                      Cacheable value = (Cacheable)cacheHashMap.get(key);
	                      /* Is the cacheable object expired? */
	                      if (value.isExpired())
	                      {
	                        /* Yes it's expired! Remove it from the cache */
	                        cacheHashMap.remove(key);
	                        //System.out.println("ThreadCleanerUpper Running.Found an Expired Object in the Cache.");
	                      }
	                    }
	                    Thread.sleep(this.milliSecondSleepTime);
	                  }
	                }
	                catch (Exception e)
	                {
	                    logger.error("CacheManager-Exception", e);
	                }
	                return;
	              } /* End run method */
	            });
	            
	            threadCleanerUpper.setPriority(Thread.MIN_PRIORITY);
	            // Starts the thread.
	            threadCleanerUpper.start();
	        }
	        catch(Exception e)
	        {
	            //  System.out.println("CacheManager.Static Block: " + e);
	        }
	    } /* End static block */
	    
	    public CacheManager()
	    {
	    }
	    public static void putCache(Cacheable object)
	    {
	    
	      cacheHashMap.put(object.getIdentifier(), object);
	    }
	    public static Cacheable getCache(Object identifier)
	    {
	      
	        Cacheable object = (Cacheable)cacheHashMap.get(identifier);
	     
	      if (object == null)
	        return null;
	      if (object.isExpired())
	      {
	        cacheHashMap.remove(identifier);
	        return null;
	      }
	      else
	      {
	        return object;
	      }
	    }


}
