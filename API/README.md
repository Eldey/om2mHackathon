# SMART Digital Twin API Documentation

# Table of Content
* [General principle](https://github.com/Eldey/om2mHackathon/tree/master/API#general-principle)
* [GET](https://github.com/Eldey/om2mHackathon/tree/master/API#get)
  * [Get a specific device](https://github.com/Eldey/om2mHackathon/tree/master/API#get-the-current-state-of-a-device)
  * [Get a parameter](https://github.com/Eldey/om2mHackathon/tree/master/API#get-the-current-state-of-a-parameter)
* [SET](https://github.com/Eldey/om2mHackathon/tree/master/API#get)
  * [Set a Window or a Door](https://github.com/Eldey/om2mHackathon/tree/master/API#change-the-current-state-of-a-window-or-a-door) 
  * [Set a light](https://github.com/Eldey/om2mHackathon/tree/master/API#change-the-current-state-of-a-light)
* [Parameters](https://github.com/Eldey/om2mHackathon/tree/master/API#parameters)
  * [Controlling the time](https://github.com/Eldey/om2mHackathon/tree/master/API#controlling-the-time-of-day)
    * [Set to a specific time](https://github.com/Eldey/om2mHackathon/tree/master/API#controlling-the-time-of-day)
*********************
## General Principle

In order to control the simulation and its devices, you need to address Post a Content Instance into the Container associated to a device. A dedicated Interworking Proxy Entity (IPE) has been designed to handle all new content instance. The source code of this IPE is available [here](https://github.com/Eldey/om2mHackathon/tree/master/IPE/Src/org.eclipse.om2m.ipe.smart_twin).

The model is composed of two different type of entities:
* A device: It can be a light, a door, a movement sensor or a window
* A parameter: It models the simulation parameters such as the Time, the number of occupants or the refreshing rate.

In order to interact with the simulation, you need a REST client to send HTTP POST requests.

The generic structure of request is as followed:

* HTTP Request

Field | Value
------------ | -------------
URL | http://localhost:8080/~/in-cse/in-name/DigitalTwin/Room/device
Method        | POST
Header        | { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body          | (empty)

* HTTP responses
 Status : 200 Ok | 400 Bad Request
 
The rest of the document describes all the available operations. 
*********************
## GET
Those operations allow to get the current state of a device or a parameter. 

### Get the current state of a device 

| Device Type        | States           | 
| ------------- |:-------------:| 
| Door     | OPENED/CLOSED | 
| Window  | OPENED/CLOSED      |   
| Light | hex: Hex code of the light colour.   intensity: intensity of the light [0..100]      |   
| Movement sensor | ON/OFF     |   

* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin/6.203/window/la
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body | (empty)

* HTTP response

Status : 200 Ok
Body   :
```json
    {    
    "m2m:cin": {
        "rn": "cin_76678820",
        "ty": 4,
        "ri": "/in-cse/cin-76678820",
        "pi": "/in-cse/cnt-98842656",
        "ct": "20190418T132948",
        "lt": "20190418T132948",
        "st": 0,
        "cnf": "text/plain:0",
        "cs": 50,
        "con": "{\"room\":\"6.203\", \"device\":\"window\", \"state\":\"CLOSED\"}"
        }
    }
```
*********************

### Get the current state of a parameter 

| Available parameters     |    Description  | 
| ------------- |:-------------:| 
| TimeOfDay     | The current time in the simulation [0...1440]. |
| RefreshRate   | The refresh rate of the simulation.            |
| Population    | The number of occupants simulated.             |

* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin/TimeOfDay
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body | (empty)

* HTTP response

Status : 200 Ok
Body   :
```json
    {
    "m2m:cin": {
        "rn": "cin_824262239",
        "ty": 4,
        "ri": "/in-cse/cin-824262239",
        "pi": "/in-cse/cnt-609135732",
        "ct": "20190423T183731",
        "lt": "20190423T183731",
        "st": 0,
        "cnf": "text/plain:0",
        "cs": 35,
        "con": "{\"param\":\"TimeOfDay\", \"value\":\"13\"}"
    }
}
```
*********************
## SET
### Change the current state of a Window or a Door 
* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin/6.203/door
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body :
```json
{
   "m2m:cin": {
        "cnf": "application/json",
        "con": "{\"room\":\"6.203\", \"device\":\"door\", \"state\":\"CLOSED\"}"
   }
}
```

* HTTP response

Status : 200 Ok
Body   :
```json
    {
    "m2m:cin": {
        "rn": "cin_605497129",
        "ty": 4,
        "ri": "/in-cse/cin-605497129",
        "pi": "/in-cse/cnt-611739193",
        "ct": "20190507T224825",
        "lt": "20190507T224825",
        "st": 0,
        "cnf": "application/json",
        "cs": 51,
        "con": "{\"room\":\"6.203\", \"device\":\"door\", \"state\":\"CLOSED\"}"
    }
}
```

### Change the current state of a Light
* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin?op=SET_LIGHT&room=6.203&device=light&hex=4286f4&intensity=21.2
op      | SET_CLOSE, SET_OPEN
?hex          | HEX code withouth the #
?intensity    | Intensity [0...100]
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body :
```json
 {
   "m2m:cin": {
        "cnf": "application/json",
        "con": "{\"room\":\"6.203\", \"device\":\"light\", \"hex\":\"#4286f4\", \"intensity\":\"50.5\"}"
   }
}
```

* HTTP response

Status : 200 Ok
Body   :
```json
    {
    "m2m:cin": {
        "rn": "cin_548717777",
        "ty": 4,
        "ri": "/in-cse/cin-548717777",
        "pi": "/in-cse/cnt-463936908",
        "ct": "20190507T225254",
        "lt": "20190507T225254",
        "st": 0,
        "cnf": "application/json",
        "cs": 71,
        "con": "{\"room\":\"6.203\", \"device\":\"light\", \"hex\":\"#4286f4\", \"intensity\":\"50.5\"}"
    }
}
```

*********************

## Parameters
### Controlling the time of Day

#### Set to a specific time and a specific speed
* HTTP Request

value is [0...1440] where 0 is 0:00 and 1440 is 23:59.
ms is the number of milliseconds corresponding to 1 minute.

Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin/TimeOfDay
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body :  
```json
 {
   "m2m:cin": {
        "cnf": "application/json",
        "con": "{\"id\":\"TimeOfDay\", \"value\":\"350\", \"ms\":\"100\"}"
   }
}
```

* HTTP response

Status : 200 Ok
Body   :
```json
    {
    "m2m:cin": {
        "rn": "cin_693503782",
        "ty": 4,
        "ri": "/in-cse/cin-693503782",
        "pi": "/in-cse/cnt-198962612",
        "ct": "20190507T225534",
        "lt": "20190507T225534",
        "st": 0,
        "cnf": "application/json",
        "cs": 45,
        "con": "{\"id\":\"TimeOfDay\", \"value\":\"350\", \"ms\":\"100\"}"
    }
}
```


### Controlling the Population

#### Set to a specific population
* HTTP Request

value is [0...99] corresponding the number of occupants withing the building.

Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin/Population
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body :  
```json
 {
   "m2m:cin": {
        "cnf": "application/json",
        "con": "{\"id\":\"TimeOfDay\", \"value\":\"350\", \"ms\":\"100\"}"
   }
}
```

* HTTP response

Status : 200 Ok
Body   :
```json
    {
    "m2m:cin": {
        "rn": "cin_693503782",
        "ty": 4,
        "ri": "/in-cse/cin-693503782",
        "pi": "/in-cse/cnt-198962612",
        "ct": "20190507T225534",
        "lt": "20190507T225534",
        "st": 0,
        "cnf": "application/json",
        "cs": 45,
        "con": "{\"id\":\"Population\", \"value\":\"50\"}"
    }
}
```
