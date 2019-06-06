package com.example.grpc;

import java.io.IOException;

import com.google.common.flogger.FluentLogger;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class CustomerServer {

	// Logger
	private static final FluentLogger logger = FluentLogger.forEnclosingClass();
	
	private Server server;
	private static final int PORT = 50552;
	
	public static void main(String[] args) throws IOException, InterruptedException {		
		/*
		 * Initialize final class and start server and await termination
		 */
		final CustomerServer server = new CustomerServer();
		server.start();
		server.blockUntilShutdown();
	}
	
	private void start() throws IOException {
		
		server = ServerBuilder.forPort(PORT)
				.addService(new CustomerServiceImpl())
				.build();
		server.start();		
		logger.atInfo().log("######### Server started at port %s ##########", PORT);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {

			/* (non-Javadoc)
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				// Use standard error here
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				CustomerServer.this.stop();
				System.err.println("*** server shut down");
			}
			
			
		});
		
	}

	private void stop() {
		if (server != null) {
			server.shutdown();
		}
		
	}
	
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}
}