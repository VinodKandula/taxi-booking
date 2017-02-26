## Taxi Booking Application

A taxi dispatching station having a pool of taxis serving customers.

The server provide RESTful web services for the pull-based interaction between the client and server for the stateless components and WebSockets for streaming taxi location updates events and AWS Simple Notification Service (Amazon SNS) for pushed-based events such as taxi booking updates.

## Micro-Service Architecture

![taxi-booking-microservice-architecture](/documentation/images/taxi-booking-microservice-architecture.png)


## UseCase Diagram - Taxi Booking Module

![taxi-booking-usecase-diagram](/documentation/images/UseCase Diagram Taxi Booking Module.png)

## Activity Diagram - Taxi Booking

![Activity Diagram - Booking a Taxi](/documentation/images/Activity Diagram - Booking a Taxi.png)

## Activity Diagram - Accept Taxi Booking

![Activity Diagram - Accept Taxi Booking](/documentation/images/Activity Diagram - Accept Taxi Booking.png)


## Class Diagram - Taxi Booking Domain Model

![taxibooking-domain-model-class-diagram](/documentation/images/taxibooking-domain-model-class-diagram.png)

![taxi-booking-domain-model-diagram](/documentation/images/domain-model-class-diagram.png)

## Class Diagram - Taxi Booking State Pattern

![booking-state-pattern-class-diagram](/documentation/images/booking-state-pattern-class-diagram.png)


## Class Diagram - Taxi State Pattern

![taxi-state-pattern-class-diagram](/documentation/images/taxi-state-pattern-class-diagram.gif)


# Taxi Booking Service API

## Setup the Database - TestController

	http://localhost:8000/v1/test/setup

## Taxi Controller

	GET: http://localhost:8000/v1/taxi/{id}

	POST: http://localhost:8000/v1/taxi/{id}/location
		Request: {
					"latitude": 51.763366,
					"longitude": -0.22309
				 }

## GOOGLE MAP API - GeocodeController

* Return a route from start and end location latitude and longitude

	GET: http://localhost:8000/v1/geocode/route
	
	Ex: http://localhost:8000/v1/geocode/route?start_latitude=51.506618&start_longitude=-0.127637&end_latitude=51.506618&end_longitude=-0.127637

* Return address corresponding to provided latitude and longitude.

	GET: http://localhost:8000/v1/geocode/reverse

* Return location (lat/lng) corresponding to provided address.

	GET: http://localhost:8000/v1/geocode/address/lookup

* Return estimate travel time between start and end location in seconds.

	GET: http://localhost:8000/v1/geocode/route/estimate/time

* Find address via textual description.

	GET: http://localhost:8000/v1/geocode/address

## Taxi Booking Controller - BookingController

* A controller class for receiving and handling all booking related transactions

	POST: makeBooking
	
	GET: bookingHistory
	
	GET: findBooking
	
	GET: findBookingsForUser
	
	GET: incompleteBookings
	
	POST: updateBooking
	
	POST: acceptBooking
	

## Config Service 

	http://localhost:8888/booking-service/master

	http://localhost:8888/eureka-service/master

	http://localhost:8888/env

	http://localhost:8888/metrics

## Discovery Service

	http://localhost:8761/

