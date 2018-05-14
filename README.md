#Capabilities
Given a set of days of the week, time windows, and corresponding prices, this API will later return prices for specified time ranges on specified dates.  
    
###POSTs (Schedule Overwrites)
POSTs are used to populate the price schedule.  There is no endpoint for updating.  Any post will completely overwrite the existing schedule.  
Format Example: 
``` 
   {  
      "rates": [  
        {  
          "days": "mon,tues,wed,thurs,fri",  
          "times": "0600-1800",  
          "price": 1500  
        },  
        {  
          "days": "sat,sun",  
          "times": "0600-2000",  
          "price": 2000  
        }  
      ]  
    }
```
  
###GETs (Pricing Query)
GETs are used to retrieve the price for a specified time window.  
Example:  
`localhost/cost?start=2000-11-01T07:30:00.000-05:00&end=2000-11-01T10:30:00.000-05:00`  
`start` is the start time; `end` is the end time, and they must follow the format above, of `YYYY-MM-DDTHH:MM:SS.MMMZZZZZZ`
Here, the group of Ys represents year, the first pair of Ms is month, Ds are date, Hs are hour, the second pair of Ms is minute, Ss are second, the trio of Ms is miliseconds, and Zs are the time zone shift.  
  
  
#Running the API
1) Install Docker Compose on your system, following the instructions at https://docs.docker.com/compose/install/ (Including prerequisite instructions, if you do not have Docker Engine installed).  
2) Make sure that you have nothing listening on port 80 on your machine.
3) From the root directory of this project, run `docker-compose up`.  
3) If you see an error message about `permission denied` from the docker daemon, you need to add your user to the `docker` group, and restart your machine. Add yourself to the group with `sudo usermod -a -G docker $USER`.  
4) The API should now be listening at port 80 on your machine, so reach it at `localhost`, or the publicly visible IP or DNS name if running on the cloud.
  
  
#Work I Would Like To Do
###Tests
I have not written any unit tests or validation tests.  This is not something that I skip in production grade code, but time was a serious constraint.  
  
###Validation
I would like to validate request body and query param content for formatting as well as values.  Currently, a lot of faith is placed on the client.  
  
###Exception Handling
I do not catch any exceptions right now, and I do not have any specific status codes being returned.  
  
###Test and Clarify Timezone Concerns
POST information is saved without any timezone adjustments being made, whereas GET requests must include a timezone in order to be valid ISO formatting.
  
###UI
This is currently a simple REST API.  There is no UI.  
  
###More Endpoints
I would like to add a PUT endpoint for updating the existing schedule, rather than only allowing POSTs that completely overwrite the existing data.  
I would also like to add a GET endpoint from which a client could retrieve a list of time windows and their corresponding prices, by providing a set of days.
  
###Allow XML POSTs
Currently, only JSON is accepted.
  
###Create Swagger Docs
Simple README is all I have right now.
 
###Remove Spring Boot
Despite the assignment requirement, I used Spring Boot.  I was spinning my wheels trying to get JPA dependencies and configuration to work, and I had very little time to work on this.  I thought that something with Boot would be better than nothing without it.  
  
###Remove My 1 Second Hack
In order to leverage JPA simplicity without dealing with times that begin or end exactly at the boundary of a price window, I add a second onto the start time, and remove a second from the end time.  This is a hack, and I would rather write JPQL to do a proper query.
