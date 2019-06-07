package com.example.grpc;

import java.util.Iterator;

import com.example.grpc.customer.CustomerCreateRequest;
import com.example.grpc.customer.CustomerCreateResponse;
import com.example.grpc.customer.CustomerGetRequest;
import com.example.grpc.customer.CustomerGetResponse;
import com.example.grpc.customer.CustomerServiceGrpc;
import com.example.grpc.customer.CustomerType;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CustomerBlockingClient {

	public static void main(String[] args) {
		final ManagedChannel channel = ManagedChannelBuilder
				.forAddress("localhost", 50552)
				.usePlaintext()
				.build();

		CustomerServiceGrpc.CustomerServiceBlockingStub stub = CustomerServiceGrpc.newBlockingStub(channel);

		CustomerBlockingClient client = new CustomerBlockingClient();
		// call create and get methods
		client.create(stub);
		client.get(stub);
	}

	private void get(CustomerServiceGrpc.CustomerServiceBlockingStub stub) {

		CustomerGetRequest request = CustomerGetRequest.newBuilder().setCustomerId("1234").build();

		// Variables
		Iterator<CustomerGetResponse> response;

		response = stub.get(request);

		// Log the response
		while(response.hasNext())
			System.out.println(response.next());

	}
	
	private void create(CustomerServiceGrpc.CustomerServiceBlockingStub stub) {

		CustomerType customer = CustomerType.newBuilder().setFirstName("John").setLastName("Doe").build();
		CustomerCreateRequest request = CustomerCreateRequest.newBuilder().setCustomer(customer).build();

		// Variables
		CustomerCreateResponse response;

		response = stub.create(request);

		// Log the response
		System.out.println(response);

	}

}
