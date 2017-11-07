package com.flynava.jupiter.daoImpl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.flynava.jupiter.daoInterface.CrossbarDataDao;
import com.flynava.jupiter.model.RequestModel;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import ws.wamp.jawampa.ApplicationError;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.WampRouter;
import ws.wamp.jawampa.WampRouterBuilder;
import ws.wamp.jawampa.connection.IWampConnectorProvider;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

@Repository
public class CrossbarDataDaoImpl implements CrossbarDataDao {

	private static final Logger logger = Logger.getLogger(CrossbarDataDaoImpl.class);
	
	@Override
	public Object getCrossbarData(RequestModel requestModel) {
		Double directIndirectRev = 5000D;
		Double FrequentFlyerRev = 10000D;
		Double CustomerSegments = 10D;
		Double Distributors = 22D;
		Map<String, Object> crossbarData = new HashMap<String, Object>();
		crossbarData.put("directIndirectRev", directIndirectRev);
		crossbarData.put("FrequentFlyerRev", FrequentFlyerRev);
		crossbarData.put("CustomerSegments", CustomerSegments);
		crossbarData.put("Distributors", Distributors);
		
		WampRouterBuilder routerBuilder = new WampRouterBuilder();
        WampRouter router;
        try {
            routerBuilder.addRealm("realm1");
            router = routerBuilder.build();
        } catch (ApplicationError e1) {
            e1.printStackTrace();
            return null; 
        }
        
        URI serverUri = URI.create("ws://192.168.1.133:7272//ws1");
        

        IWampConnectorProvider connectorProvider = new NettyWampClientConnectorProvider();
        WampClientBuilder builder = new WampClientBuilder();
		
		final WampClient client;
		try {
           
            
            builder.withConnectorProvider(connectorProvider)
                   .withUri("ws://192.168.1.176:7272/ws1")
                   .withRealm("realm1")
                   .withInfiniteReconnects()
                   .withReconnectInterval(3, TimeUnit.SECONDS);
            client = builder.build();
        } catch (Exception e) {
            logger.error("getCrossbarData-Exception", e);
            return null;
        }

		client.statusChanged().subscribe(new Action1<WampClient.State>() {
			@Override
			public void call(WampClient.State t1) {
				System.out.println("Session2 status changed to " + t1);

				if (t1 instanceof WampClient.ConnectedState) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					// Call the procedure
					int[] arr1 = {1,2,3,4};
			        int[] arr2 = {5,3,2,9,16,79};
					Observable<Long> result1 = client.call("com.example.bestcompetitors", Long.class, arr1, arr2);
					result1.subscribe(new Action1<Long>() {
						@Override
						public void call(Long t1) {
							System.out.println("Completed add with result " + t1);
						}
					}, new Action1<Throwable>() {
						@Override
						public void call(Throwable t1) {
							System.out.println("Completed add with error " + t1);
						}
					});

				}
			}
		}, new Action1<Throwable>() {
			@Override
			public void call(Throwable t) {
				System.out.println("Session2 ended with error " + t);
			}
		}, new Action0() {
			@Override
			public void call() {
				System.out.println("Session2 ended normally");
			}
		});

		return crossbarData;
	}

}
