# Energy Management System

I developed an Energy Management System consisting of a front end and 4 microservices, deployed in Docker, designed for users to manage their associated devices.

There are 2 types of users: Admin & Client - authorized with JWT

Admin use cases:
- CRUD on users
- CRUD on devices
- Chat with clients - send messages, recieve notifications, observe "seen" and "typing" events
 
Client use cases:
- View their own devices
- View energy consumption of their devices as charts for each selected day
- Chat with admin - messages, notifications, typing, seen
- Recieve notifications regarding their devices

Microservices:
- Users microservice - responsible with managing users and with authentication and authorization of users.
- Device microservice - managing devices
- Communication microservice - implements rabbitMQ publish-subscribe communication to exchange messages with device microservice and with the device simulator regarding energy consumption. Also configures WebSocket for sending notificatons when the maximum consumption for a device is exceeded.
- Chat Microservice - implements chatting feature, where admins and clients can chat with each other using channels on websockets.
- Device Simulator - simulate a running device that is consuming energy by sending messages through rabbitMQ to the Communication Microservice.

Screenshots from the application:

- admin chatting with multiple users
  
<img src="Screenshots/Screenshot (50).png">

- view device consumption graph
  
<img src="Screenshots/Screenshot (48).png">

- view users list
  
<img src="Screenshots/Screenshot (47).png">

- view devices
  
<img src="Screenshots/Screenshot (49).png">

