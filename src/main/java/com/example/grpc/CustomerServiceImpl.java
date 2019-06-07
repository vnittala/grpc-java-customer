package com.example.grpc;

import com.example.grpc.customer.CustomerCreateRequest;
import com.example.grpc.customer.CustomerCreateResponse;
import com.example.grpc.customer.CustomerGetRequest;
import com.example.grpc.customer.CustomerGetResponse;
import com.example.grpc.customer.CustomerServiceGrpc.CustomerServiceImplBase;
import com.example.grpc.customer.CustomerType;
import com.google.common.flogger.FluentLogger;
import com.google.rpc.Code;
import com.google.rpc.Status;

import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;

public class CustomerServiceImpl extends CustomerServiceImplBase{
	
	// Variables
	private static final FluentLogger logger = FluentLogger.forEnclosingClass();

	/* (non-Javadoc)
	 * @see com.example.grpc.customer.CustomerServiceGrpc.CustomerServiceImplBase#get(com.example.grpc.customer.CustomerGetRequest, io.grpc.stub.StreamObserver)
	 */
	@Override
	public void get(CustomerGetRequest request, StreamObserver<CustomerGetResponse> responseObserver) {
		// TODO Auto-generated method stub
		logger.atInfo().log("Received CustomerGetRequest");
		
		try {
			
			// Generate static customer record
			CustomerType customer = CustomerType.newBuilder()
													.setCustomerId("1234")
													.setFirstName("John")
													.setLastName("Doe")
													.build();
			
			// Log customer record
			logger.atInfo().log("Customer Record", customer.toString());
			
			// Attached customer record to CustomerGetResponse
			CustomerGetResponse response = CustomerGetResponse.newBuilder().setCustomer(customer).build();
			
			// Send response
			responseObserver.onNext(response);
			
			// Log customer response before closing call
			logger.atInfo().log("Completed CustomerGetRequest");
			
			// Close client call
			responseObserver.onCompleted();
			
		} catch (Exception e) {
			// Capture and send error message
			logger.atInfo().withCause(e).log("Excpetion occured while processing CustomerGetRequest");
			Status status = Status.newBuilder().setCode(Code.INTERNAL_VALUE).setMessage("Excpetion occured while processing CustomerGetRequest").build();
			responseObserver.onError(StatusProto.toStatusException(status));
		}
	}

	/* (non-Javadoc)
	 * @see com.example.grpc.customer.CustomerServiceGrpc.CustomerServiceImplBase#create(com.example.grpc.customer.CustomerCreateRequest, io.grpc.stub.StreamObserver)
	 */
	@Override
	public void create(CustomerCreateRequest request, StreamObserver<CustomerCreateResponse> responseObserver) {
		
		logger.atInfo().log("Received CustomerCreateRequest");
		
		try {
			
			// Generate CustomerCreateResponse
			CustomerCreateResponse response = CustomerCreateResponse
														.newBuilder()
														.setStatusCode("201")
														.setStatusMessage("Created Customer record for : " + request.getCustomer().getFirstName())
														.build();
			
			responseObserver.onNext(response);
			
			logger.atInfo().log("Completed CustomerCreateRequest");
			
			responseObserver.onCompleted();
			
		} catch (Exception e) {
			logger.atInfo().withCause(e).log("Customer already exists");
			Status status = Status.newBuilder().setCode(Code.ALREADY_EXISTS_VALUE).setMessage("Customer already exists").build();
			responseObserver.onError(StatusProto.toStatusException(status));
		}
	}
	
	

}
