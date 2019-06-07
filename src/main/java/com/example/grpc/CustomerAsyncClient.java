package com.example.grpc;

import com.example.grpc.customer.CustomerGetRequest;
import com.example.grpc.customer.CustomerGetResponse;
import com.example.grpc.customer.CustomerServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class CustomerAsyncClient {
	public static void main(String[] args) {
		final ManagedChannel channel = ManagedChannelBuilder
				.forAddress("localhost", 50552)
				.usePlaintext()
				.build();

		CustomerServiceGrpc.CustomerServiceStub stub = CustomerServiceGrpc.newStub(channel);
		CustomerGetRequest request = CustomerGetRequest.newBuilder().setCustomerId("1234").build();

		stub.get(request, new StreamObserver<CustomerGetResponse>() {

			@Override
			public void onNext(CustomerGetResponse value) {
				System.out.println("onNext: " + value);
			}

			@Override
			public void onError(Throwable t) {
				System.err.println(t);

			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
				channel.shutdown();
			}
		});
	}
}
