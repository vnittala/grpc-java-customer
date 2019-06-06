package com.example.grpc;

import java.util.Iterator;

import com.example.grpc.customer.CustomerGetRequest;
import com.example.grpc.customer.CustomerGetResponse;
import com.example.grpc.customer.CustomerServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CustomerBlockingClient {
	
	public static void main(String[] args) {
		final ManagedChannel channel = ManagedChannelBuilder
				.forAddress("localhost", 50552)
				.usePlaintext()
				.build();
		
		CustomerServiceGrpc.CustomerServiceBlockingStub stub = CustomerServiceGrpc.newBlockingStub(channel);
		
		CustomerGetRequest request = CustomerGetRequest.newBuilder().setCustomerId("1234").build();
		
		// Variables
		Iterator<CustomerGetResponse> response;
		
		response = stub.get(request);
		
		// Log the response
		while(response.hasNext())
			System.out.println(response.next());
	}

}
